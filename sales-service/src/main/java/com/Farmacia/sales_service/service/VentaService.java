package com.Farmacia.sales_service.service;

import com.Farmacia.sales_service.DTO.*;
import com.Farmacia.sales_service.model.DetalleVenta;
import com.Farmacia.sales_service.model.Venta;
import com.Farmacia.sales_service.repository.VentaRepository;
import com.Farmacia.sales_service.util.EstadoVenta;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository repo;

    @Autowired
    private VentaMapper mapper;

    @Autowired
    private ClienteFeignClient clienteService;

    @Autowired
    private MedicamentoFeignClient medicamentoService;

    @Autowired
    private UserFeignClient userService;

    @Override
    public VentaGetDTO create(VentaPostDTO post) {
        ClientesGetDTO cliente = obtenerCliente(post.clienteId());
        UserGetDTO usuario = obtenerUsuario(post.userId());

        List<DetalleVenta> detalles = new ArrayList<>();

        for (VentaDetalleDTO detalleDTO : post.detalles()) {
            MedicamentosGetDTO medicamento = obtenerMedicamento(detalleDTO.medicamentoId());

            validarMedicamento(medicamento, detalleDTO.cantidad());

            actualizarStock(medicamento.id(), medicamento.stock() - detalleDTO.cantidad());

            DetalleVenta detalle = mapper.toDetalleVenta(detalleDTO);
            detalles.add(detalle);
        }

        Venta venta = mapper.toEntity(post, detalles);
        for (DetalleVenta detalle : detalles) {
            detalle.setVenta(venta);
        }

        Venta savedVenta = repo.save(venta);
        return mapper.toDTOS(savedVenta, cliente.nombre(), usuario.nombre());
    }

    @CircuitBreaker(name = "customer-service", fallbackMethod = "fallbackObtenerCliente")
    @Retry(name = "customer-service")
    private ClientesGetDTO obtenerCliente(Integer clienteId) {
        ClientesGetDTO cliente = clienteService.obtenerId(clienteId);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }
        return cliente;
    }

    private ClientesGetDTO fallbackObtenerCliente(Throwable throwable) {
        throw new RuntimeException("Servicio de clientes no disponible" + throwable.getMessage());
    }

    @CircuitBreaker(name = "user-service", fallbackMethod = "fallbackObtenerUsuario")
    @Retry(name = "user-service")
    private UserGetDTO obtenerUsuario(Integer userId) {
        UserGetDTO usuario = userService.obtenerId(userId);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuario;
    }

    private UserGetDTO fallbackObtenerUsuario(Throwable throwable) {
        throw new RuntimeException("Servicio de usuarios no disponible" + throwable.getMessage());
    }

    @CircuitBreaker(name = "catalog-service", fallbackMethod = "fallbackObtenerMedicamento")
    @Retry(name = "catalog-service")
    private MedicamentosGetDTO obtenerMedicamento(Integer medicamentoId) {
        MedicamentosGetDTO medicamento = medicamentoService.obtenerMedicamentosPorId(medicamentoId);
        if (medicamento == null) {
            throw new RuntimeException("Medicamento no encontrado");
        }
        return medicamento;
    }

    private MedicamentosGetDTO fallbackObtenerMedicamento(Throwable throwable) {
        throw new RuntimeException("Servicio de medicamentos no disponible" + throwable.getMessage());
    }

    @CircuitBreaker(name = "catalog-service", fallbackMethod = "fallbackActualizarStock")
    @Retry(name = "catalog-service")
    private void actualizarStock(Integer medicamentoId, Integer nuevoStock) {
        medicamentoService.actualizarStock(medicamentoId, nuevoStock);
    }

    private MedicamentosGetDTO fallbackActualizarStock(Throwable throwable) {
        throw new RuntimeException("Servicio de medicamentos no disponible" + throwable.getMessage());
    }

    private void validarMedicamento(MedicamentosGetDTO medicamento, Integer cantidadSolicitada) {
        if (!medicamento.activo()) {
            throw new RuntimeException("Medicamento inactivo: " + medicamento.nombre());
        }
        if (medicamento.stock() < cantidadSolicitada) {
            throw new RuntimeException(
                    String.format("Stock insuficiente para %s. Disponible: %d, Solicitado: %d",
                            medicamento.nombre(), medicamento.stock(), cantidadSolicitada)
            );
        }
    }

    @Override
    @CircuitBreaker(name = "catalog-service", fallbackMethod = "fallbackCancelar")
    @Retry(name = "catalog-service")
    public VentaGetDTO cancel(Integer id) {
        Venta venta = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        if (venta.getEstado().equals(EstadoVenta.ANULADA)) {
            throw new RuntimeException("La venta ya está anulada");
        }

        for (DetalleVenta d : venta.getDetalleventas()) {
            try {
                MedicamentosGetDTO m = obtenerMedicamento(d.getMedicamentoId());
                actualizarStock(m.id(), m.stock() + d.getCantidad());
            } catch (Exception e) {
                throw new RuntimeException("Error devolviendo stock para medicamento: " + d.getMedicamentoId(), e);
            }
        }

        venta.setEstado(EstadoVenta.ANULADA);
        venta.setActivo(false);
        Venta saved = repo.save(venta);
        return mapper.toDTO(saved);
    }

    public VentaGetDTO fallbackCancelar(Throwable throwable) {
        throw new RuntimeException("No se puede cancelar la venta. Servicio de medicamentos no disponible" + throwable.getMessage());
    }

    @Override
    public Optional<VentaGetDTO> findById(Integer id) {
        Optional<Venta> venta = repo.findById(id).filter(Venta::getActivo);
        if (venta.isPresent()) {
            ClientesGetDTO cliente = obtenerCliente(venta.get().getClienteId());
            UserGetDTO user = obtenerUsuario(venta.get().getUserId());
            VentaGetDTO dto = mapper.toDTOS(venta.get(),cliente.nombre(), user.nombre());
            return Optional.of(dto);
        }

        return Optional.empty();
    }

    @Override
    public List<VentaGetDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public List<VentaGetDTO> obtenerVentasPorUsuario(Integer userId) {
        List<Venta> ventas = repo.findByUserIdAndActivoTrue(userId);
        return ventas.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaGetDTO> obtenerVentasPorCliente(Integer clienteId) {
        List<Venta> ventas = repo.findByClienteIdAndActivoTrue(clienteId);
        return ventas.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}

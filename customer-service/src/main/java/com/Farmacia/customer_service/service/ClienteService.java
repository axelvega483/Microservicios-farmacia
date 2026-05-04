package com.Farmacia.customer_service.service;

import com.Farmacia.customer_service.DTO.*;
import com.Farmacia.customer_service.model.Cliente;
import com.Farmacia.customer_service.repository.ClienteRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClienteService implements IClienteService {
    @Autowired
    private ClienteRepository repo;
    @Autowired
    private ClienteMapper mapper;
    @Autowired
    private RecetaFeignClient receta;
    @Autowired
    private VentaFeignClient venta;

    @Override
    public ClientesGetDTO create(ClientePostDTO post) {
        if (repo.existsByDniAndActivoTrue(post.dni())) {
            throw new EntityExistsException("Cliente ya existente");
        }
        Cliente cliente = mapper.create(post);
        Cliente saved = repo.save(cliente);
        return mapper.toDTO(saved);
    }


    @Override
    public ClientesGetDTO update(Integer id, ClienteUpdateDTO put) {
        Cliente cliente = repo.findById(id).orElse(null);
        if (cliente == null) {
            throw new EntityExistsException("El Cliente no existe");
        }
        mapper.update(cliente, put);
        Cliente saved = repo.save(cliente);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        Optional<Cliente> optionalCliente = repo.findById(id);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setActivo(Boolean.FALSE);
            repo.save(cliente);
        }
    }

    @Override
    public Optional<ClientesGetDTO> findById(Integer id) {
        Optional<Cliente> cliente = repo.findById(id).filter(Cliente::getActivo);
        if (cliente.isPresent()) {
            Cliente cli = cliente.get();
            List<ClienteVentaDTO> ventas = obtenerVentasConResiliencia(cli.getId());
            List<ClienteRecetasDTO> recetas = obtenerRecetasConResiliencia(cli.getId());
            ClientesGetDTO dto = mapper.toDTOS(cliente.get(), recetas, ventas);
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @CircuitBreaker(name = "sales-service", fallbackMethod = "fallbackObtenerVentas")
    @Retry(name = "sales-service")
    private List<ClienteVentaDTO> obtenerVentasConResiliencia(Integer clienteId) {
        return venta.obtenerVentasPorCliente(clienteId);
    }

    @CircuitBreaker(name = "prescriptions-service", fallbackMethod = "fallbackObtenerRecetas")
    @Retry(name = "prescriptions-service")
    private List<ClienteRecetasDTO> obtenerRecetasConResiliencia(Integer id) {
        return receta.findByCliente(id);
    }

    private List<ClienteVentaDTO> fallbackObtenerVentas(Integer clienteId, Throwable throwable) {
        log.warn("Fallback para ventas del cliente: {}, Error: {}", clienteId, throwable.getMessage());
        return Collections.emptyList();
    }

    private List<ClienteRecetasDTO> fallbackObtenerRecetas(Integer clienteId, Throwable throwable) {
        log.warn("Fallback para recetas del cliente: {}, Error: {}", clienteId, throwable.getMessage());
        return Collections.emptyList();
    }


    @Override
    public List<ClientesGetDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public ClientesGetDTO findName(Integer id) {
        Optional<Cliente> cliente = repo.findById(id).filter(Cliente::getActivo);
        if (!cliente.isPresent()) {
            throw new EntityNotFoundException("Cliente no encontrado");
        }
        return mapper.toDTO(cliente.get());
    }

}

package com.Farmacia.sales_service.DTO;

import com.Farmacia.sales_service.model.DetalleVenta;
import com.Farmacia.sales_service.model.Venta;
import com.Farmacia.sales_service.util.EstadoVenta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VentaMapper {

    public VentaGetDTO toDTOS(Venta venta, String cliente, String empleado) {
        List<VentaDetalleDTO> detalles = venta.getDetalleventas().stream()
                .map(detalle -> new VentaDetalleDTO(
                        detalle.getMedicamentoId(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario(),
                        detalle.getCantidad() * detalle.getPrecioUnitario())).toList();
        return new VentaGetDTO(
                venta.getId(),
                venta.getFecha(),
                venta.getTotal(),
                detalles,
                cliente,
                empleado,
                venta.getActivo(),
                venta.getEstado()
        );
    }

    public VentaGetDTO toDTO(Venta venta) {
        List<VentaDetalleDTO> detalles = venta.getDetalleventas().stream()
                .map(detalle -> new VentaDetalleDTO(
                        detalle.getMedicamentoId(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario(),
                        detalle.getCantidad() * detalle.getPrecioUnitario())).toList();
        return new VentaGetDTO(
                venta.getId(),
                venta.getFecha(),
                venta.getTotal(),
                detalles,
                null,
                null,
                venta.getActivo(),
                venta.getEstado()
        );
    }

    public Venta toEntity(VentaPostDTO post, List<DetalleVenta> detalles) {
        return Venta.builder()
                .clienteId(post.clienteId())
                .userId(post.userId())
                .fecha(post.fecha())
                .detalleventas(detalles)
                .total(calcularTotal(detalles))
                .activo(Boolean.TRUE)
                .estado(EstadoVenta.FACTURADA)
                .build();
    }

    public DetalleVenta toDetalleVenta(VentaDetalleDTO detalleDTO) {
        return DetalleVenta.builder()
                .cantidad(detalleDTO.cantidad())
                .precioUnitario(detalleDTO.precioUnitario())
                .medicamentoId(detalleDTO.medicamentoId())
                .build();
    }

    private double calcularTotal(List<DetalleVenta> detalles) {
        return detalles.stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                .sum();
    }

    public List<VentaGetDTO> toDTOList(List<Venta> ventas) {
        return ventas.stream().filter(Venta::getActivo).map(this::toDTO).toList();
    }
}

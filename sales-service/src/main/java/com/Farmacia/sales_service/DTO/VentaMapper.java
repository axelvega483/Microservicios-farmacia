package com.Farmacia.sales_service.DTO;

import com.Farmacia.sales_service.model.DetalleVenta;
import com.Farmacia.sales_service.model.Venta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VentaMapper {

    public VentaGetDTO toDTO(Venta venta) {
        VentaGetDTO dto = new VentaGetDTO();
        dto.setActivo(venta.getActivo());
        dto.setFecha(venta.getFecha());
        dto.setId(venta.getId());
        dto.setTotal(venta.getTotal());
        dto.setEstado(venta.getEstado());

        List<VentaDetalleDTO> detalles = venta.getDetalleventas().stream()
                .map(detalle -> new VentaDetalleDTO(
                        detalle.getMedicamentoId(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario(),
                        detalle.getCantidad() * detalle.getPrecioUnitario())).toList();
        dto.setDetalleventas(detalles);
        return dto;
    }

    public Venta toEntity(VentaPostDTO post, List<DetalleVenta> detalles) {
        Venta venta = new Venta();
        venta.setClienteId(post.getClienteId());
        venta.setUserId(post.getUserId());
        venta.setFecha(post.getFecha());
        venta.setDetalleventas(detalles);
        venta.setTotal(calcularTotal(detalles));
        return venta;
    }

    public DetalleVenta toDetalleVenta(VentaDetalleDTO detalleDTO) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(detalleDTO.getCantidad());
        detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
        detalle.setMedicamentoId(detalleDTO.getMedicamentoId());
        return detalle;
    }

    private double calcularTotal(List<DetalleVenta> detalles) {
        return detalles.stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                .sum();
    }
}

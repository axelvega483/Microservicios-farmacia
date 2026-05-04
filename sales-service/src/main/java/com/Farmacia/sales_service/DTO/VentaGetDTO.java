package com.Farmacia.sales_service.DTO;

import com.Farmacia.sales_service.util.EstadoVenta;

import java.time.LocalDate;
import java.util.List;

public record VentaGetDTO(
        Integer id,
        LocalDate fecha,
        Double total,
        List<VentaDetalleDTO> detalleventas,
        String cliente,
        String empleado,
        Boolean activo,
        EstadoVenta estado) {

}

package com.Farmacia.sales_service.DTO;

import jakarta.validation.constraints.NotNull;


public record VentaDetalleDTO(
        @NotNull
        Integer medicamentoId,
        @NotNull
        Integer cantidad,
        @NotNull
        Double precioUnitario,
        Double subtotal) {

}

package com.Farmacia.sales_service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaDetalleDTO {

    @NotNull
    private Integer medicamentoId;
    @NotNull
    private Integer cantidad;
    @NotNull
    private Double precioUnitario;
    private Double subtotal;
}

package com.Farmacia.sales_service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VentaPostDTO {

    @NotNull
    private LocalDate fecha;
    @NotNull
    private Double total;
    @NotNull
    private List<VentaDetalleDTO> detalles;
    @NotNull
    private Integer clienteId;
    @NotNull
    private Integer userId;
}

package com.Farmacia.catalog_service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicamentoPostDTO {

    @NotNull
    private String nombre;
    @NotNull
    private String descripcion;
    @NotNull
    private Double precio;
    @NotNull
    private Integer stock;
    @NotNull
    private LocalDate fechaVencimiento;
    @NotNull
    private Boolean recetaRequerida;
    @NotNull
    private Integer proveedor;
}

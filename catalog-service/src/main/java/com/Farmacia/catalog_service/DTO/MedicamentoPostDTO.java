package com.Farmacia.catalog_service.DTO;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record MedicamentoPostDTO(
        @NotNull
        String nombre,
        @NotNull
        String descripcion,
        @NotNull
        Double precio,
        @NotNull
        Integer stock,
        @NotNull
        LocalDate fechaVencimiento,
        @NotNull
        Boolean recetaRequerida,
        @NotNull
        Integer proveedor) {


}

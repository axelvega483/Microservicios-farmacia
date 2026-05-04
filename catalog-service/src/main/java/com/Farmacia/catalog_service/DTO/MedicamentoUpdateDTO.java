package com.Farmacia.catalog_service.DTO;

import java.time.LocalDate;


public record MedicamentoUpdateDTO(
        String nombre,
        String descripcion,
        Double precio,
        Integer stock,
        LocalDate fechaVencimiento,
        Boolean recetaRequerida,
        Boolean activo,
        Integer proveedor) {


}

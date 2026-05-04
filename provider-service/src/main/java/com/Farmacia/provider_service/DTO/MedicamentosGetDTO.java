package com.Farmacia.provider_service.DTO;

import java.time.LocalDate;

public record MedicamentosGetDTO(
        Integer id,
        String nombre,
        String descripcion,
        Double precio,
        Integer stock,
        LocalDate fechaVencimiento,
        Boolean recetaRequerida,
        Boolean activo) {


}
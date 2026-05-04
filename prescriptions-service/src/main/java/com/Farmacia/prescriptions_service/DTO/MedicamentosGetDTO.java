package com.Farmacia.prescriptions_service.DTO;


public record MedicamentosGetDTO(
        Integer id,
        String nombre,
        String descripcion,
        Double precio,
        Boolean recetaRequerida) {

}

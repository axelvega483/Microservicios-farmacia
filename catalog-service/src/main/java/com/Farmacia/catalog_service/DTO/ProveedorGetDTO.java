package com.Farmacia.catalog_service.DTO;


public record ProveedorGetDTO(
        Integer id,
        String nombre,
        String telefono,
        String email,
        Boolean activo) {

}

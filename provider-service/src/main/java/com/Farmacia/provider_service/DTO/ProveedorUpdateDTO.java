package com.Farmacia.provider_service.DTO;


public record ProveedorUpdateDTO(
        String nombre,
        String telefono,
        String email,
        Boolean activo) {

}

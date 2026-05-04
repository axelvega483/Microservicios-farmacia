package com.Farmacia.provider_service.DTO;

import jakarta.validation.constraints.NotNull;


public record ProveedorPostDTO(
        @NotNull
        String nombre,
        @NotNull
        String telefono,
        @NotNull
        String email) {


}

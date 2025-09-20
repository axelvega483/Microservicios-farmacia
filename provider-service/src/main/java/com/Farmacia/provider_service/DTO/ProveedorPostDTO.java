package com.Farmacia.provider_service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorPostDTO {

    @NotNull
    private String nombre;
    @NotNull
    private String telefono;
    @NotNull
    private String email;
}

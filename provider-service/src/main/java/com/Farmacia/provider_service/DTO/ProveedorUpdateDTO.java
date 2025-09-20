package com.Farmacia.provider_service.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorUpdateDTO {

    private String nombre;

    private String telefono;

    private String email;

    private Boolean activo;
}

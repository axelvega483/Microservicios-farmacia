package com.Farmacia.provider_service.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProveedorGetDTO {
    private Integer id;
    private String nombre;
    private String telefono;
    private String email;
    private Boolean activo;
    private List<MedicamentosGetDTO> medicamentos;
}

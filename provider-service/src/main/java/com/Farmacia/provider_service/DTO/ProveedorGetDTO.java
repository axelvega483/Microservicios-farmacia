package com.Farmacia.provider_service.DTO;


import java.util.List;

public record ProveedorGetDTO(
        Integer id,
        String nombre,
        String telefono,
        String email,
        Boolean activo,
        List<MedicamentosGetDTO> medicamentos) {

}

package com.Farmacia.prescriptions_service.DTO;

import java.time.LocalDate;
import java.util.List;

public record RecetaMedicaGetDTO(
        Integer id,
        String medico,
        LocalDate fecha,
        LocalDate vigenteHasta,
        Boolean activo,
        String clienteNombre,
        List<MedicamentosGetDTO> medicamentosNombres) {

}

package com.Farmacia.prescriptions_service.DTO;

import java.time.LocalDate;
import java.util.List;


public record RecetaMedicaUptadeDTO(
        LocalDate fecha,
        String medico,
        Integer clienteId,
        List<Integer> medicamentoIds,
        LocalDate vigenteHasta,
        Boolean activo) {

}

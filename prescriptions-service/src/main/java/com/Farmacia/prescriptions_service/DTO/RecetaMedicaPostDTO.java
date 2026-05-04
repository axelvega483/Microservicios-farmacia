package com.Farmacia.prescriptions_service.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record RecetaMedicaPostDTO(
        @NotNull
        String medico,

        @NotNull
        Integer cliente,

        @NotNull
        List<Integer> medicamentoIds,

        @NotNull
        LocalDate vigenteHasta) {

}

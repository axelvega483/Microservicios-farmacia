package com.Farmacia.prescriptions_service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RecetaMedicaPostDTO {

    @NotNull
    private String medico;

    @NotNull
    private Integer cliente;

    @NotNull
    private List<Integer> medicamentoIds;

    @NotNull
    private LocalDate vigenteHasta;
}

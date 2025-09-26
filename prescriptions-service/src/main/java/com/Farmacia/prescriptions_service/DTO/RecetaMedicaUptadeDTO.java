package com.Farmacia.prescriptions_service.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RecetaMedicaUptadeDTO {

    private LocalDate fecha;

    private String medico;

    private Integer clienteId;

    private List<Integer> medicamentoIds;

    private LocalDate vigenteHasta;

    private Boolean activo;
}

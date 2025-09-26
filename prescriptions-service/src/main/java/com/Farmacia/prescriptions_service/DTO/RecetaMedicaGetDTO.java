package com.Farmacia.prescriptions_service.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RecetaMedicaGetDTO {

    private Integer id;
    private String medico;
    private LocalDate fecha;
    private LocalDate vigenteHasta;
    private Boolean activo;
    private String clienteNombre;
    private List<MedicamentosGetDTO> medicamentosNombres;
}

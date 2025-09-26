package com.Farmacia.prescriptions_service.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MedicamentosGetDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Boolean recetaRequerida;
}

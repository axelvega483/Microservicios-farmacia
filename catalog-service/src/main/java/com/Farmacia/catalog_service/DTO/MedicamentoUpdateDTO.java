package com.Farmacia.catalog_service.DTO;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicamentoUpdateDTO {

    private String nombre;

    private String descripcion;

    private Double precio;

    private Integer stock;

    private LocalDate fechaVencimiento;

    private Boolean recetaRequerida;

    private Boolean activo;

    private Integer proveedor;

}

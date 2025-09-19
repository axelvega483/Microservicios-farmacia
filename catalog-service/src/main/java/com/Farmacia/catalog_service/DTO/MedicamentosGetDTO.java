package com.Farmacia.catalog_service.DTO;

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
    private Integer stock;
    private LocalDate fechaVencimiento;
    private Boolean recetaRequerida;
    private Boolean activo;
    private ProveedorGetDTO proveedor;
    private List<MedicamentoDetalleDTO> detalleVentasID;
}

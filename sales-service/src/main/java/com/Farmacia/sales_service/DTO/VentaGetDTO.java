package com.Farmacia.sales_service.DTO;

import com.Farmacia.sales_service.util.EstadoVenta;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VentaGetDTO {

    private Integer id;
    private LocalDate fecha;
    private Double total;
    private List<VentaDetalleDTO> detalleventas;
    private String cliente;
    private String empleado;
    private Boolean activo;
    private EstadoVenta estado;
}

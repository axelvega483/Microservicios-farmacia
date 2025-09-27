package com.Farmacia.sales_service.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientesGetDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String dni;
    private Boolean activo;
}

package com.Farmacia.sales_service.DTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserGetDTO {
    private Integer id;

    private String nombre;

    private String email;

    private String dni;

    private String rol;

    private Boolean activo;


}

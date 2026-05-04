package com.Farmacia.sales_service.DTO;


public record UserGetDTO(
        Integer id,
        String nombre,
        String email,
        String dni,
        String rol,
        Boolean activo) {

}

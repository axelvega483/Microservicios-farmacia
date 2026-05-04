package com.Farmacia.sales_service.DTO;


public record ClientesGetDTO(
        Integer id,
        String nombre,
        String email,
        String dni,
        Boolean activo) {


}

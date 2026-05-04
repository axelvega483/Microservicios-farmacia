package com.Farmacia.customer_service.DTO;

public record ClienteUpdateDTO(

        String nombre,

        String email,

        String dni,

        Boolean activo) {

}

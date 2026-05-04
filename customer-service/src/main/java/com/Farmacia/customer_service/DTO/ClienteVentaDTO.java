package com.Farmacia.customer_service.DTO;


import java.time.LocalDate;

public record ClienteVentaDTO(
         Integer id,
         LocalDate fecha,
         Double total) {

}

package com.Farmacia.customer_service.DTO;

import java.time.LocalDate;

public record ClienteRecetasDTO(
        Integer id,
        LocalDate fecha,
        String medico,
        LocalDate vigenteHasta) {


}

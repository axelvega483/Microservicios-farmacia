package com.Farmacia.customer_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRecetasDTO {

    private Integer id;
    private LocalDate fecha;
    private String medico;
    private LocalDate vigenteHasta;
}

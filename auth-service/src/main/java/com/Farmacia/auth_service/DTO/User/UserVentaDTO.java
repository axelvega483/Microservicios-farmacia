package com.Farmacia.auth_service.DTO.User;


import com.Farmacia.auth_service.util.EstadoVenta;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserVentaDTO(Integer id,
                           LocalDate fecha,
                           BigDecimal total,
                           EstadoVenta estado) {
}

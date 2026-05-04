package com.Farmacia.customer_service.DTO;

import java.util.List;

public record ClientesGetDTO(
         Integer id,
         String nombre,
         String email,
         String dni,
         Boolean activo,
         List<ClienteRecetasDTO> recetas,
         List<ClienteVentaDTO> ventas) {

}

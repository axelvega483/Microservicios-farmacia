package com.Farmacia.auth_service.DTO.User;

import com.Farmacia.auth_service.util.RolUser;

import java.util.List;

public record UserGetDTO(
         Integer id,
         String nombre,
         String email,
         String dni,
         RolUser rol,
         boolean activo,
         List<UserVentaDTO> ventas) {


}

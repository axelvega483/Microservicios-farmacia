package com.Farmacia.auth_service.DTO.User;


public record UserPutDTO(
         String nombre,

         String email,

         String dni,

         String password,

         boolean activo) {




}

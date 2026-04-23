package com.Farmacia.auth_service.DTO.User;

import com.Farmacia.auth_service.util.RolUser;
import jakarta.validation.constraints.NotNull;

public record UserPostDTO(
        @NotNull
        String nombre,

        @NotNull
        String email,

        @NotNull
        String password,

        @NotNull
        String dni,

        @NotNull
        RolUser rol) {

}

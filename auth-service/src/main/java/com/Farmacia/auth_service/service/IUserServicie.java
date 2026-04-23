package com.Farmacia.auth_service.service;

import com.Farmacia.auth_service.DTO.User.UserGetDTO;
import com.Farmacia.auth_service.DTO.User.UserPostDTO;
import com.Farmacia.auth_service.DTO.User.UserPutDTO;
import com.Farmacia.auth_service.DTO.User.UserRolDTO;
import com.Farmacia.auth_service.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserServicie {
    User save(User user);

    Optional<UserGetDTO> findById(Integer id);

    List<UserGetDTO> findAll();

    void delete(Integer id);

    UserGetDTO crear(UserPostDTO post);

    UserGetDTO actualizar(Integer id, UserPutDTO put);

    UserGetDTO actualizarRol(Integer id, UserRolDTO rol);

    Boolean existe(String dni);


    UserGetDTO obtenerId(Integer userId);
}

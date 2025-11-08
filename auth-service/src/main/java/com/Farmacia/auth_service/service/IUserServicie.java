package com.Farmacia.auth_service.service;

import com.Farmacia.auth_service.DTO.UserGetDTO;
import com.Farmacia.auth_service.DTO.UserPostDTO;
import com.Farmacia.auth_service.DTO.UserPutDTO;
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

    Boolean existe(String dni);

    Optional<User> findByCorreoAndPassword(String email, String password);

    UserGetDTO obtenerId(Integer userId);
}

package com.Farmacia.customer_service.service;

import com.Farmacia.customer_service.DTO.ClientePostDTO;
import com.Farmacia.customer_service.DTO.ClienteUpdateDTO;
import com.Farmacia.customer_service.DTO.ClientesGetDTO;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    ClientesGetDTO create(ClientePostDTO post);

    boolean findDniActivo(String dni);

    ClientesGetDTO update(Integer id, ClienteUpdateDTO put);

    void delete(Integer id);

    Optional<ClientesGetDTO> findById(Integer id);

    List<ClientesGetDTO> findAll();

    Optional<ClientesGetDTO> findName(Integer id);
}

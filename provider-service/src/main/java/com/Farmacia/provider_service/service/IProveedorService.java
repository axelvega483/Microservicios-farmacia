package com.Farmacia.provider_service.service;

import com.Farmacia.provider_service.DTO.ProveedorGetDTO;
import com.Farmacia.provider_service.DTO.ProveedorPostDTO;
import com.Farmacia.provider_service.DTO.ProveedorUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface IProveedorService {
    ProveedorGetDTO create(ProveedorPostDTO post);

    ProveedorGetDTO update(Integer id, ProveedorUpdateDTO put);

    void delete(Integer id);

    Optional<ProveedorGetDTO> findById(Integer id);

    List<ProveedorGetDTO> findAll();

    Optional<ProveedorGetDTO> proveedorId(Integer id);
}

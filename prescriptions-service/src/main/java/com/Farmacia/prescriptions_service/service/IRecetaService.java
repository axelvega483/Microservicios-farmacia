package com.Farmacia.prescriptions_service.service;

import com.Farmacia.prescriptions_service.DTO.RecetaMedicaGetDTO;
import com.Farmacia.prescriptions_service.DTO.RecetaMedicaPostDTO;
import com.Farmacia.prescriptions_service.DTO.RecetaMedicaUptadeDTO;

import java.util.List;
import java.util.Optional;

public interface IRecetaService {
    RecetaMedicaGetDTO create(RecetaMedicaPostDTO post);

    RecetaMedicaGetDTO update(Integer id, RecetaMedicaUptadeDTO put);

    Optional<RecetaMedicaGetDTO> findById(Integer id);

    void delete(Integer id);

    List<RecetaMedicaGetDTO> findAll();

    List<RecetaMedicaGetDTO> findByCliente(Integer id);
}

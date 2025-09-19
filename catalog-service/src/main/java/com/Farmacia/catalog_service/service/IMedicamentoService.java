package com.Farmacia.catalog_service.service;

import com.Farmacia.catalog_service.DTO.MedicamentoPostDTO;
import com.Farmacia.catalog_service.DTO.MedicamentoUpdateDTO;
import com.Farmacia.catalog_service.DTO.MedicamentosGetDTO;

import java.util.List;
import java.util.Optional;

public interface IMedicamentoService {
    MedicamentosGetDTO create(MedicamentoPostDTO post);

    boolean medicamentoExiste(String nombre, Integer proveedorId);

    void delete(Integer id);

    Optional<MedicamentosGetDTO> findById(Integer id);

    List<MedicamentosGetDTO> findAll();

    List<MedicamentosGetDTO> findByName(String nombre);

    MedicamentosGetDTO update(Integer id, MedicamentoUpdateDTO put);
}

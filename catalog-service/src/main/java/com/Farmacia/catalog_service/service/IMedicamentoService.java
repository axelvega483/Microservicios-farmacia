package com.Farmacia.catalog_service.service;

import com.Farmacia.catalog_service.DTO.MedicamentoPostDTO;
import com.Farmacia.catalog_service.DTO.MedicamentoUpdateDTO;
import com.Farmacia.catalog_service.DTO.MedicamentosGetDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    List<MedicamentosGetDTO> proveedorId(Integer proveedorId);

    List<MedicamentosGetDTO> recetaId(Integer recetaId);


    Optional<MedicamentosGetDTO> obtenerMedicamentosPorId(Integer medicamentoId);


    void actualizarStock(Integer id,Integer cantidad);
}

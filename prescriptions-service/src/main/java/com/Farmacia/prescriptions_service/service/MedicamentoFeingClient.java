package com.Farmacia.prescriptions_service.service;

import com.Farmacia.prescriptions_service.DTO.MedicamentosGetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "catalog-service")
public interface MedicamentoFeingClient {
    @GetMapping("/medicamento/receta/{recetaId}")
    List<MedicamentosGetDTO> obtenerMedicamentosPorReceta(@PathVariable Integer recetaId);
}




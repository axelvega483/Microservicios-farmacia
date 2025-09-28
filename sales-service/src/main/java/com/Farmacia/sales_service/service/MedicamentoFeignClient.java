package com.Farmacia.sales_service.service;

import com.Farmacia.sales_service.DTO.MedicamentosGetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "catalog-service")
public interface MedicamentoFeignClient {
    @GetMapping("/medicamento/obtener/{medicamentoId}")
    MedicamentosGetDTO obtenerMedicamentosPorId(@PathVariable Integer medicamentoId);

    @PutMapping("/medicamento/stock/{id}")
    void actualizarStock(@PathVariable Integer id, @RequestParam Integer cantidad);
}


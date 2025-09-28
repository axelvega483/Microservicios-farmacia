package com.Farmacia.provider_service.service;


import com.Farmacia.provider_service.DTO.MedicamentosGetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "catalog-service")
public interface MedicamentoFeignClient {
    @GetMapping("/medicamento/proveedor/{proveedorId}")
    List<MedicamentosGetDTO> obtenerMedicamentosPorProveedor(@PathVariable Integer proveedorId);
}
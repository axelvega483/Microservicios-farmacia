package com.Farmacia.provider_service.service;


import com.Farmacia.provider_service.DTO.MedicamentosGetDTO;
import com.Farmacia.provider_service.config.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "catalog-service", configuration = FeignClientConfig.class)
public interface MedicamentoFeignClient {
    @GetMapping("/catalog/proveedor/{proveedorId}")
    List<MedicamentosGetDTO> obtenerMedicamentosPorProveedor(@PathVariable Integer proveedorId);
}
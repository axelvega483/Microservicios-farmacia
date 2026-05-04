package com.Farmacia.prescriptions_service.service;

import com.Farmacia.prescriptions_service.DTO.MedicamentosGetDTO;
import com.Farmacia.prescriptions_service.config.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "catalog-service", configuration = FeignClientConfig.class)
public interface MedicamentoFeingClient {
    @PostMapping("/catalog/ids")
    List<MedicamentosGetDTO> obtenerMedicamentosPorIds(@RequestBody List<Integer> ids);
}




package com.Farmacia.customer_service.service;

import com.Farmacia.customer_service.DTO.ClienteRecetasDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "prescriptions-service")
public interface RecetaFeignClient {
    @GetMapping("/receta/cliente/{id}")
    List<ClienteRecetasDTO> findByCliente(@PathVariable Integer id);
}

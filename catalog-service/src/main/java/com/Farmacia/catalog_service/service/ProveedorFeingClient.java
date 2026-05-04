package com.Farmacia.catalog_service.service;

import com.Farmacia.catalog_service.DTO.ProveedorGetDTO;
import com.Farmacia.catalog_service.config.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "provider-service", configuration = FeignClientConfig.class)
public interface ProveedorFeingClient {
    @GetMapping("/provider/basico/{id}")
    ProveedorGetDTO obtenerProveedorBasico(@PathVariable Integer id);
}


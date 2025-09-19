package com.Farmacia.catalog_service.service;

import com.Farmacia.catalog_service.DTO.ProveedorGetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "provider-service")
public interface ProveedorFeingClient {
    @GetMapping("/provider/{proveedorId}")
    ProveedorGetDTO obtenerProveedorPorId(@PathVariable Integer proveedorId);
}


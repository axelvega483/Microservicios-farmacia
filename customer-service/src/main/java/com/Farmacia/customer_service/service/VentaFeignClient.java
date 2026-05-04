package com.Farmacia.customer_service.service;

import com.Farmacia.customer_service.DTO.ClienteVentaDTO;
import com.Farmacia.customer_service.config.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "sales-service", configuration = FeignClientConfig.class)
public interface VentaFeignClient {
    @GetMapping("/venta/cliente/{clienteId}")
    List<ClienteVentaDTO> obtenerVentasPorCliente(@PathVariable Integer clienteId);
}

package com.Farmacia.sales_service.service;

import com.Farmacia.sales_service.DTO.ClientesGetDTO;
import com.Farmacia.sales_service.config.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service",configuration = FeignClientConfig.class)
public interface ClienteFeignClient {
    @GetMapping("/customer/nombre/{clienteId}")
    ClientesGetDTO obtenerId(@PathVariable Integer clienteId);
}

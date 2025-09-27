package com.Farmacia.sales_service.service;

import com.Farmacia.sales_service.DTO.ClientesGetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface ClienteFeignClient {
    @GetMapping("/customer/obtener/{clienteId}")
    ClientesGetDTO obtenerId(@PathVariable Integer clienteId);
}

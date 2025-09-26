package com.Farmacia.prescriptions_service.service;

import com.Farmacia.prescriptions_service.DTO.ClientesGetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface ClienteFeignClient {
    @GetMapping("/customer/nombre/{id}")
    ClientesGetDTO findName(@PathVariable("id") Integer id);
}
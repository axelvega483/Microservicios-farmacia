package com.Farmacia.sales_service.service;

import com.Farmacia.sales_service.DTO.UserGetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface UserFeignClient {
    @GetMapping("/auth/obtener/{userId}")
    UserGetDTO obtenerId(@PathVariable Integer userId);
}

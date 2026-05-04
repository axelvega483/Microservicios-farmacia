package com.Farmacia.sales_service.service;

import com.Farmacia.sales_service.DTO.UserGetDTO;
import com.Farmacia.sales_service.config.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service",configuration = FeignClientConfig.class)
public interface UserFeignClient {
    @GetMapping("/user/obtener/{userId}")
    UserGetDTO obtenerId(@PathVariable Integer userId);
}

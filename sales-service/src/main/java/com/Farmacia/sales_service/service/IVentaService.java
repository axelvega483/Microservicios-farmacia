package com.Farmacia.sales_service.service;

import com.Farmacia.sales_service.DTO.VentaGetDTO;
import com.Farmacia.sales_service.DTO.VentaPostDTO;

import java.util.List;
import java.util.Optional;

public interface IVentaService {
    VentaGetDTO create(VentaPostDTO post);

    VentaGetDTO cancel(Integer id);

    Optional<VentaGetDTO> findById(Integer id);

    List<VentaGetDTO> findAll();

    List<VentaGetDTO> obtenerVentasPorUsuario(Integer userId);

    List<VentaGetDTO> obtenerVentasPorCliente(Integer clienteId);
}

package com.Farmacia.customer_service.DTO;

import com.Farmacia.customer_service.model.Cliente;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ClienteMapper {

    public ClientesGetDTO toDTOS(Cliente cliente, List<ClienteRecetasDTO> recetas,
                                 List<ClienteVentaDTO> ventas) {
        return new ClientesGetDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDni(),
                cliente.getActivo(),
                recetas,
                ventas
        );
    }

    public ClientesGetDTO toDTO(Cliente cliente) {
        return new ClientesGetDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDni(),
                cliente.getActivo(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }


    public Cliente create(ClientePostDTO post) {
        return Cliente.builder()
                .dni(post.dni())
                .email(post.email())
                .nombre(post.nombre())
                .activo(Boolean.TRUE)
                .build();
    }

    public void update(Cliente cliente, ClienteUpdateDTO put) {
        if (put.dni() != null) cliente.setDni(put.dni());
        if (put.email() != null) cliente.setEmail(put.email());
        if (put.nombre() != null) cliente.setNombre(put.nombre());
        if (put.activo() != null) cliente.setActivo(put.activo());

    }
    public List<ClientesGetDTO> toDTOList(List<Cliente> clientes) {
        return clientes.stream().filter(Cliente::getActivo).map(this::toDTO).toList();
    }
}

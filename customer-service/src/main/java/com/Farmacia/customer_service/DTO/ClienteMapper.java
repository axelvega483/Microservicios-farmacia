package com.Farmacia.customer_service.DTO;

import com.Farmacia.customer_service.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClientesGetDTO toDTO(Cliente cliente) {
        ClientesGetDTO dto = new ClientesGetDTO();
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        dto.setId(cliente.getId());
        dto.setActivo(cliente.getActivo());
        dto.setDni(cliente.getDni());
        return dto;

    }

    public Cliente create(ClientePostDTO post) {
        Cliente cliente = new Cliente();
        cliente.setActivo(Boolean.TRUE);
        cliente.setDni(post.getDni());
        cliente.setEmail(post.getEmail());
        cliente.setNombre(post.getNombre());
        return cliente;
    }

    public Cliente update(Cliente cliente, ClienteUpdateDTO put) {
        if (put.getDni()!= null) cliente.setDni(put.getDni());
        if (put.getEmail() != null) cliente.setEmail(put.getEmail());
        if (put.getNombre() != null) cliente.setNombre(put.getNombre());
        if (put.getActivo() != null) cliente.setActivo(put.getActivo());
        return cliente;
    }
}

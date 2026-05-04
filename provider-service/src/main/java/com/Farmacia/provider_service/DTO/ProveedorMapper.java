package com.Farmacia.provider_service.DTO;

import com.Farmacia.provider_service.model.Proveedor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class ProveedorMapper {

    public ProveedorGetDTO toDTOS(Proveedor proveedor, List<MedicamentosGetDTO> medicamentos) {
        return new ProveedorGetDTO(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getEmail(),
                proveedor.getTelefono(),
                proveedor.getActivo(),
                medicamentos
        );
    }

    public ProveedorGetDTO toDTO(Proveedor proveedor) {
        return new ProveedorGetDTO(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getEmail(),
                proveedor.getTelefono(),
                proveedor.getActivo(),
                Collections.emptyList()
        );
    }

    public Proveedor create(ProveedorPostDTO post) {
        return Proveedor.builder()
                .nombre(post.nombre())
                .email(post.email())
                .telefono(post.telefono())
                .activo(Boolean.TRUE)
                .build();
    }

    public void update(Proveedor proveedor, ProveedorUpdateDTO put) {
        if (put.nombre() != null) proveedor.setNombre(put.nombre());
        if (put.email() != null) proveedor.setEmail(put.email());
        if (put.telefono() != null) proveedor.setTelefono(put.telefono());
        if (put.activo() != null) proveedor.setActivo(put.activo());
    }

    public List<ProveedorGetDTO> toDTOList(List<Proveedor> proveedors) {
        return proveedors.stream().filter(Proveedor::getActivo).map(this::toDTO).toList();
    }
}

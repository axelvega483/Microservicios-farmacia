package com.Farmacia.catalog_service.DTO;

import com.Farmacia.catalog_service.model.Medicamento;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicamentoMapper {
    public MedicamentosGetDTO toDTOS(Medicamento medicamento, ProveedorGetDTO proveedor) {
        return new MedicamentosGetDTO(
                medicamento.getId(),
                medicamento.getNombre(),
                medicamento.getDescripcion(),
                medicamento.getPrecio(),
                medicamento.getStock(),
                medicamento.getFechaVencimiento(),
                medicamento.getRecetaRequerida(),
                medicamento.getActivo(),
                proveedor
        );
    }

    public MedicamentosGetDTO toDTO(Medicamento medicamento) {
        return new MedicamentosGetDTO(
                medicamento.getId(),
                medicamento.getNombre(),
                medicamento.getDescripcion(),
                medicamento.getPrecio(),
                medicamento.getStock(),
                medicamento.getFechaVencimiento(),
                medicamento.getRecetaRequerida(),
                medicamento.getActivo(),
                null
        );
    }

    public Medicamento create(MedicamentoPostDTO post) {
        return Medicamento.builder()
                .nombre(post.nombre())
                .descripcion(post.descripcion())
                .precio(post.precio())
                .stock(post.stock())
                .fechaVencimiento(post.fechaVencimiento())
                .recetaRequerida(post.recetaRequerida())
                .proveedorId(post.proveedor())
                .activo(Boolean.TRUE)
                .build();
    }

    public void update(Medicamento medicamento, MedicamentoUpdateDTO put) {
        if (put.activo() != null) medicamento.setActivo(put.activo());
        if (put.descripcion() != null) medicamento.setDescripcion(put.descripcion());
        if (put.fechaVencimiento() != null) medicamento.setFechaVencimiento(put.fechaVencimiento());
        if (put.nombre() != null) medicamento.setNombre(put.nombre());
        if (put.precio() != null) medicamento.setPrecio(put.precio());
        if (put.proveedor() != null) medicamento.setProveedorId(put.proveedor());
        if (put.recetaRequerida() != null) medicamento.setRecetaRequerida(put.recetaRequerida());
        if (put.stock() != null) medicamento.setStock(put.stock());
    }

    public List<MedicamentosGetDTO> toDTOList(List<Medicamento> medicamentos) {
        return medicamentos.stream().filter(Medicamento::getActivo).map(this::toDTO).toList();
    }
}

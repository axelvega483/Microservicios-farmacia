package com.Farmacia.catalog_service.DTO;

import com.Farmacia.catalog_service.model.Medicamento;
import org.springframework.stereotype.Component;

@Component
public class MedicamentoMapper {


    public MedicamentosGetDTO toDTO(Medicamento medicamento) {
        MedicamentosGetDTO dto = new MedicamentosGetDTO();
        dto.setActivo(medicamento.getActivo());
        dto.setDescripcion(medicamento.getDescripcion());
        dto.setFechaVencimiento(medicamento.getFechaVencimiento());
        dto.setId(medicamento.getId());
        dto.setNombre(medicamento.getNombre());
        dto.setPrecio(medicamento.getPrecio());
        dto.setRecetaRequerida(medicamento.getRecetaRequerida());
        dto.setStock(medicamento.getStock());
        return dto;
    }

    public Medicamento create(MedicamentoPostDTO post) {
        Medicamento medicamento = new Medicamento();
        medicamento.setActivo(Boolean.TRUE);
        medicamento.setDescripcion(post.getDescripcion());
        medicamento.setFechaVencimiento(post.getFechaVencimiento());
        medicamento.setNombre(post.getNombre());
        medicamento.setPrecio(post.getPrecio());
        medicamento.setProveedorId(post.getProveedor());
        medicamento.setRecetaRequerida(post.getRecetaRequerida());
        medicamento.setStock(post.getStock());
        return medicamento;
    }

    public Medicamento update(Medicamento medicamento, MedicamentoUpdateDTO put) {
        if (put.getActivo() != null) medicamento.setActivo(put.getActivo());
        if (put.getDescripcion() != null) medicamento.setDescripcion(put.getDescripcion());
        if (put.getFechaVencimiento() != null) medicamento.setFechaVencimiento(put.getFechaVencimiento());
        if (put.getNombre() != null) medicamento.setNombre(put.getNombre());
        if (put.getPrecio() != null) medicamento.setPrecio(put.getPrecio());
        if (put.getProveedor() != null) medicamento.setProveedorId(put.getProveedor());
        if (put.getRecetaRequerida() != null) medicamento.setRecetaRequerida(put.getRecetaRequerida());
        if (put.getStock() != null) medicamento.setStock(put.getStock());
        return medicamento;
    }
}

package com.Farmacia.prescriptions_service.DTO;

import com.Farmacia.prescriptions_service.model.RecetaMedica;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RecetaMedicaMapper {

    public RecetaMedicaGetDTO toDTO(RecetaMedica recetaMedica) {
        RecetaMedicaGetDTO dto = new RecetaMedicaGetDTO();

        dto.setId(recetaMedica.getId());
        dto.setFecha(recetaMedica.getFecha());
        dto.setMedico(recetaMedica.getMedico());
        dto.setVigenteHasta(recetaMedica.getVigenteHasta());
        dto.setActivo(recetaMedica.getActivo());

        return dto;
    }

    public RecetaMedica create(RecetaMedicaPostDTO post) {
        RecetaMedica receta = new RecetaMedica();
        receta.setFecha(LocalDate.now());
        receta.setMedico(post.getMedico());
        receta.setVigenteHasta(post.getVigenteHasta());
        receta.setActivo(Boolean.TRUE);
        receta.setClienteId(post.getCliente());
        receta.setMedicamentoIds(post.getMedicamentoIds());
        return receta;
    }

    public RecetaMedica update(RecetaMedica receta, RecetaMedicaUptadeDTO put) {
        if (put.getFecha() != null) receta.setFecha(put.getFecha());
        if (put.getMedico() != null) receta.setMedico(put.getMedico());
        if (put.getVigenteHasta() != null) receta.setVigenteHasta(put.getVigenteHasta());
        if (put.getActivo() != null) receta.setActivo(put.getActivo());
        if (put.getClienteId() != null) receta.setClienteId(put.getClienteId());
        if (put.getMedicamentoIds() != null) receta.setMedicamentoIds(put.getMedicamentoIds());
        return receta;
    }

}

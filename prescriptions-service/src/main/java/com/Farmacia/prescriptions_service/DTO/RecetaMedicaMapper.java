package com.Farmacia.prescriptions_service.DTO;

import com.Farmacia.prescriptions_service.model.RecetaMedica;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RecetaMedicaMapper {

    public RecetaMedicaGetDTO toDTOS(RecetaMedica recetaMedica, String clienteNombre, List<MedicamentosGetDTO> medicamentos) {
        return new RecetaMedicaGetDTO(
                recetaMedica.getId(),
                recetaMedica.getMedico(),
                recetaMedica.getFecha(),
                recetaMedica.getVigenteHasta(),
                recetaMedica.getActivo(),
                clienteNombre,
                medicamentos
        );
    }

    public RecetaMedicaGetDTO toDTO(RecetaMedica recetaMedica) {
        return new RecetaMedicaGetDTO(
                recetaMedica.getId(),
                recetaMedica.getMedico(),
                recetaMedica.getFecha(),
                recetaMedica.getVigenteHasta(),
                recetaMedica.getActivo(),
                null,
                null
        );
    }


    public RecetaMedica create(RecetaMedicaPostDTO post) {
        return RecetaMedica.builder()
                .fecha(LocalDate.now())
                .medico(post.medico())
                .vigenteHasta(post.vigenteHasta())
                .activo(Boolean.TRUE)
                .clienteId(post.cliente())
                .medicamentoIds(post.medicamentoIds())
                .build();
    }

    public void update(RecetaMedica receta, RecetaMedicaUptadeDTO put) {
        if (put.fecha() != null) receta.setFecha(put.fecha());
        if (put.medico() != null) receta.setMedico(put.medico());
        if (put.vigenteHasta() != null) receta.setVigenteHasta(put.vigenteHasta());
        if (put.activo() != null) receta.setActivo(put.activo());
        if (put.clienteId() != null) receta.setClienteId(put.clienteId());
        if (put.medicamentoIds() != null) receta.setMedicamentoIds(put.medicamentoIds());
    }

    public List<RecetaMedicaGetDTO> toDTOList(List<RecetaMedica> recetaMedicas) {
        return recetaMedicas.stream().filter(RecetaMedica::getActivo).map(this::toDTO).toList();
    }
}

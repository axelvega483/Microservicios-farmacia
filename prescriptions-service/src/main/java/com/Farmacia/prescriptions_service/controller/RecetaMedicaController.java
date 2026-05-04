package com.Farmacia.prescriptions_service.controller;

import com.Farmacia.prescriptions_service.DTO.RecetaMedicaGetDTO;
import com.Farmacia.prescriptions_service.DTO.RecetaMedicaPostDTO;
import com.Farmacia.prescriptions_service.DTO.RecetaMedicaUptadeDTO;
import com.Farmacia.prescriptions_service.service.IRecetaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("prescriptions")
public class RecetaMedicaController {

    @Autowired
    private IRecetaService recetaService;


    @GetMapping
    public ResponseEntity<List<RecetaMedicaGetDTO>> findAll() {
        List<RecetaMedicaGetDTO> dto = recetaService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<RecetaMedicaGetDTO> findById(@PathVariable Integer id) {
        RecetaMedicaGetDTO receta = recetaService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receta Medica no encontrada"));
        return new ResponseEntity<>(receta, HttpStatus.OK);
    }


    @PostMapping("crear")
    public ResponseEntity<RecetaMedicaGetDTO> create(@Valid @RequestBody RecetaMedicaPostDTO recetaDto) {
        RecetaMedicaGetDTO dto = recetaService.create(recetaDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PutMapping("{id}")
    public ResponseEntity<RecetaMedicaGetDTO> update(@Valid @RequestBody RecetaMedicaUptadeDTO recetaMedicaDto, @PathVariable Integer id) {
        RecetaMedicaGetDTO dto = recetaService.update(id, recetaMedicaDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        RecetaMedicaGetDTO recetaMedica = recetaService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se pudo dar de baja - receta no encontrada"));
        recetaService.delete(recetaMedica.id());
        return new ResponseEntity<>("receta medica eliminada", HttpStatus.OK);
    }


    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<RecetaMedicaGetDTO>> findByCliente(@PathVariable Integer id) {
        List<RecetaMedicaGetDTO> recetas = recetaService.findByCliente(id);
        if (recetas == null || recetas.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron recetas para el cliente");
        }
        return new ResponseEntity<>(recetas, HttpStatus.OK);
    }
}
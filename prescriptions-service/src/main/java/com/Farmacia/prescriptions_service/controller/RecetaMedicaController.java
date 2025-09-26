package com.Farmacia.prescriptions_service.controller;

import com.Farmacia.prescriptions_service.DTO.RecetaMedicaGetDTO;
import com.Farmacia.prescriptions_service.DTO.RecetaMedicaPostDTO;
import com.Farmacia.prescriptions_service.DTO.RecetaMedicaUptadeDTO;
import com.Farmacia.prescriptions_service.service.IRecetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("receta")
public class RecetaMedicaController {

    @Autowired
    private IRecetaService recetaService;


    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<RecetaMedicaGetDTO> dto = recetaService.findAll();
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            RecetaMedicaGetDTO receta = recetaService.findById(id).orElse(null);
            if (receta != null) {
                return new ResponseEntity<>( receta, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Receta Medica no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody RecetaMedicaPostDTO recetaDto) {
        try {
            RecetaMedicaGetDTO dto = recetaService.create(recetaDto);
            return new ResponseEntity<>("Receta medica cargada", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error: " +e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody RecetaMedicaUptadeDTO recetaMedicaDto, @PathVariable Integer id) {
        try {
            RecetaMedicaGetDTO dto = recetaService.update(id,recetaMedicaDto);
            return new ResponseEntity<>("Receta medica actualizada", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id
    ) {
        try {
            RecetaMedicaGetDTO recetaMedica = recetaService.findById(id).orElse(null);
            if (recetaMedica != null) {
                recetaService.delete(recetaMedica.getId());
                return new ResponseEntity<>("receta medica eliminada", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("no se pudo dar de baja", HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> findByCliente(@PathVariable Integer id) {
        try {
            List<RecetaMedicaGetDTO> recetas = recetaService.findByCliente(id);
            if (recetas != null) {
                return new ResponseEntity<>( recetas, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Receta Medica no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

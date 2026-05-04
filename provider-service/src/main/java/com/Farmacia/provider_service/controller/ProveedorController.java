package com.Farmacia.provider_service.controller;

import com.Farmacia.provider_service.DTO.ProveedorGetDTO;
import com.Farmacia.provider_service.DTO.ProveedorPostDTO;
import com.Farmacia.provider_service.DTO.ProveedorUpdateDTO;
import com.Farmacia.provider_service.service.IProveedorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("provider")
public class ProveedorController {

    @Autowired
    private IProveedorService proveedorService;


    @GetMapping
    public ResponseEntity<List<ProveedorGetDTO>> findAll() {
        List<ProveedorGetDTO> dto = proveedorService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<ProveedorGetDTO> findById(@PathVariable Integer id) {
        ProveedorGetDTO proveedor = proveedorService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe proveedor"));
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ProveedorGetDTO> create(@Valid @RequestBody ProveedorPostDTO proveedorDTO) {
        ProveedorGetDTO dto = proveedorService.create(proveedorDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PutMapping("{id}")
    public ResponseEntity<ProveedorGetDTO> update(@Valid @RequestBody ProveedorUpdateDTO proveedorDTO, @PathVariable Integer id) {
        ProveedorGetDTO dto = proveedorService.update(id, proveedorDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        ProveedorGetDTO proveedor = proveedorService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo eliminar - proveedor no encontrado"));
        proveedorService.delete(proveedor.id());
        return new ResponseEntity<>("Proveedor eliminado", HttpStatus.OK);
    }


    @GetMapping("/basico/{id}")
    public ResponseEntity<ProveedorGetDTO> obtenerProveedorBasico(@PathVariable Integer id) {
        ProveedorGetDTO dto = proveedorService.proveedorId(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe proveedor"));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
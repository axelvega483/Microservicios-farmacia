package com.Farmacia.provider_service.controller;

import com.Farmacia.provider_service.DTO.ProveedorGetDTO;
import com.Farmacia.provider_service.DTO.ProveedorPostDTO;
import com.Farmacia.provider_service.DTO.ProveedorUpdateDTO;
import com.Farmacia.provider_service.service.IProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("proveedor")
public class ProveedorController {

    @Autowired
    private IProveedorService proveedorService;


    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<ProveedorGetDTO> dto = proveedorService.findAll();
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            ProveedorGetDTO proveedor = proveedorService.findById(id).orElse(null);
            if (proveedor != null) {
                return new ResponseEntity<>(proveedor, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No existe proveedor", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProveedorPostDTO proveedorDTO) {
        try {
            ProveedorGetDTO dto = proveedorService.create(proveedorDTO);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ProveedorUpdateDTO proveedorDTO, @PathVariable Integer id) {
        try {
            ProveedorGetDTO dto = proveedorService.update(id, proveedorDTO);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            ProveedorGetDTO proveedor = proveedorService.findById(id).orElse(null);
            if (proveedor != null) {
                proveedorService.delete(proveedor.getId());
                return new ResponseEntity<>("Proveedor eliminado", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se pudo eliminar", HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/basico/{id}")
    public ResponseEntity<?> obtenerProveedorBasico(@PathVariable Integer id) {
        try {
            ProveedorGetDTO dto = proveedorService.proveedorId(id).orElse(null);
            if (dto != null) {
                return new ResponseEntity<>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No existe proveedor", HttpStatus.NOT_FOUND);
            }
        } catch (
                Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}

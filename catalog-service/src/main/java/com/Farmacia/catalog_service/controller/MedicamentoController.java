package com.Farmacia.catalog_service.controller;

import com.Farmacia.catalog_service.DTO.MedicamentoPostDTO;
import com.Farmacia.catalog_service.DTO.MedicamentoUpdateDTO;
import com.Farmacia.catalog_service.DTO.MedicamentosGetDTO;
import com.Farmacia.catalog_service.service.IMedicamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("medicamento")
public class MedicamentoController {

    @Autowired
    private IMedicamentoService medicamentoService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<MedicamentosGetDTO> dto = medicamentoService.findAll();
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("nombre/{nombre}")
    public ResponseEntity<?> findByName(@PathVariable String nombre) {
        try {
            List<MedicamentosGetDTO> medicamentos = medicamentoService.findByName(nombre);
            if (medicamentos != null && !medicamentos.isEmpty()) {
                return new ResponseEntity<>(medicamentos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontraron coincidencia", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            MedicamentosGetDTO medicamento = medicamentoService.findById(id).orElse(null);
            if (medicamento != null) {
                return new ResponseEntity<>( medicamento, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Medicamento inexistente", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MedicamentoPostDTO medicamentoDTO) {
        try {
            MedicamentosGetDTO dto = medicamentoService.create(medicamentoDTO);
            return new ResponseEntity<>( dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody MedicamentoUpdateDTO medicamentoDTO, @PathVariable Integer id) {
        try {
            MedicamentosGetDTO dto = medicamentoService.update(id, medicamentoDTO);
            return new ResponseEntity<>( dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            MedicamentosGetDTO medicamento = medicamentoService.findById(id).orElse(null);
            if (medicamento != null) {
                medicamentoService.delete(medicamento.getId());
                return new ResponseEntity<>("Medicamento eliminado", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se pudo eliminar", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<?> obtenerMedicamentosPorProveedor(@PathVariable Integer proveedorId){
        try{
            List<MedicamentosGetDTO> medicamentos = medicamentoService.proveedorId(proveedorId);
            if (medicamentos != null && !medicamentos.isEmpty()) {
                return new ResponseEntity<>(medicamentos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontraron coincidencia", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

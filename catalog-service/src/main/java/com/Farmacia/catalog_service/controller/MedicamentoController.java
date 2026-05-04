package com.Farmacia.catalog_service.controller;

import com.Farmacia.catalog_service.DTO.MedicamentoPostDTO;
import com.Farmacia.catalog_service.DTO.MedicamentoUpdateDTO;
import com.Farmacia.catalog_service.DTO.MedicamentosGetDTO;
import com.Farmacia.catalog_service.service.IMedicamentoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("catalog")
public class MedicamentoController {

    @Autowired
    private IMedicamentoService medicamentoService;


    @GetMapping
    public ResponseEntity<List<MedicamentosGetDTO>> findAll() {
        List<MedicamentosGetDTO> dto = medicamentoService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("nombre/{nombre}")
    public ResponseEntity<List<MedicamentosGetDTO>> findByName(@PathVariable String nombre) {
        List<MedicamentosGetDTO> medicamentos = medicamentoService.findByName(nombre);
        if (medicamentos == null || medicamentos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron coincidencias");
        }
        return new ResponseEntity<>(medicamentos, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<MedicamentosGetDTO> findById(@PathVariable Integer id) {
        MedicamentosGetDTO medicamento = medicamentoService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento inexistente"));
        return new ResponseEntity<>(medicamento, HttpStatus.OK);
    }


    @PostMapping("crear")
    public ResponseEntity<MedicamentosGetDTO> create(@Valid @RequestBody MedicamentoPostDTO medicamentoDTO) {
        MedicamentosGetDTO dto = medicamentoService.create(medicamentoDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PutMapping("{id}")
    public ResponseEntity<MedicamentosGetDTO> update(@Valid @RequestBody MedicamentoUpdateDTO medicamentoDTO, @PathVariable Integer id) {
        MedicamentosGetDTO dto = medicamentoService.update(id, medicamentoDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        MedicamentosGetDTO medicamento = medicamentoService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo eliminar - medicamento no encontrado"));
        medicamentoService.delete(medicamento.id());
        return new ResponseEntity<>("Medicamento eliminado", HttpStatus.OK);
    }


    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<MedicamentosGetDTO>> obtenerMedicamentosPorProveedor(@PathVariable Integer proveedorId) {
        List<MedicamentosGetDTO> medicamentos = medicamentoService.proveedorId(proveedorId);
        if (medicamentos == null || medicamentos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron coincidencias");
        }
        return new ResponseEntity<>(medicamentos, HttpStatus.OK);
    }


    @PostMapping("/ids")
    public ResponseEntity<List<MedicamentosGetDTO>>obtenerMedicamentosPorIds(@RequestBody List<Integer> ids) {
        List<MedicamentosGetDTO> medicamentos = medicamentoService.findByIds(ids);
        if (medicamentos == null || medicamentos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron coincidencias");
        }
        return new ResponseEntity<>(medicamentos, HttpStatus.OK);
    }


    @GetMapping("/obtener/{medicamentoId}")
    public ResponseEntity<MedicamentosGetDTO> obtenerMedicamentosPorId(@PathVariable Integer medicamentoId) {
        MedicamentosGetDTO medicamento = medicamentoService.obtenerMedicamentosPorId(medicamentoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron coincidencias"));
        return new ResponseEntity<>(medicamento, HttpStatus.OK);
    }


    @PutMapping("/stock/{id}")
    public ResponseEntity<String> actualizarStock(@PathVariable Integer id, @RequestParam Integer cantidad) {
        medicamentoService.actualizarStock(id, cantidad);
        return new ResponseEntity<>("Stock actualizado", HttpStatus.OK);
    }
}
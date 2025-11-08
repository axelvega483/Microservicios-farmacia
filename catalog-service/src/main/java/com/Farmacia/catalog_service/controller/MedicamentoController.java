package com.Farmacia.catalog_service.controller;

import com.Farmacia.catalog_service.DTO.MedicamentoPostDTO;
import com.Farmacia.catalog_service.DTO.MedicamentoUpdateDTO;
import com.Farmacia.catalog_service.DTO.MedicamentosGetDTO;
import com.Farmacia.catalog_service.service.IMedicamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("medicamento")
@Tag(name = "Medicamentos", description = "Controlador para operaciones de medicamentos")
public class MedicamentoController {

    @Autowired
    private IMedicamentoService medicamentoService;

    @Operation(summary = "Listar todos los medicamentos", description = "Devuelve una lista con todos los medicamentos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamentos listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<MedicamentosGetDTO>> findAll() {
        List<MedicamentosGetDTO> dto = medicamentoService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Buscar medicamentos por nombre", description = "Devuelve medicamentos que coincidan con el nombre proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamentos encontrados exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron coincidencias"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("nombre/{nombre}")
    public ResponseEntity<List<MedicamentosGetDTO>> findByName(
            @Parameter(description = "Nombre del medicamento a buscar", example = "paracetamol", required = true)
            @PathVariable String nombre) {
        List<MedicamentosGetDTO> medicamentos = medicamentoService.findByName(nombre);
        if (medicamentos == null || medicamentos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron coincidencias");
        }
        return new ResponseEntity<>(medicamentos, HttpStatus.OK);
    }

    @Operation(summary = "Obtener medicamento por ID", description = "Devuelve un medicamento específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<MedicamentosGetDTO> findById(
            @Parameter(description = "ID del medicamento a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        MedicamentosGetDTO medicamento = medicamentoService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento inexistente"));
        return new ResponseEntity<>(medicamento, HttpStatus.OK);
    }

    @Operation(summary = "Crear nuevo medicamento", description = "Registra un nuevo medicamento en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicamento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<MedicamentosGetDTO> create(
            @Parameter(description = "Datos del medicamento a crear", required = true)
            @Valid @RequestBody MedicamentoPostDTO medicamentoDTO) {
        MedicamentosGetDTO dto = medicamentoService.create(medicamentoDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar medicamento existente", description = "Actualiza la información de un medicamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<MedicamentosGetDTO> update(
            @Parameter(description = "Datos actualizados del medicamento", required = true)
            @Valid @RequestBody MedicamentoUpdateDTO medicamentoDTO,
            @Parameter(description = "ID del medicamento a actualizar", example = "1", required = true)
            @PathVariable Integer id) {
        MedicamentosGetDTO dto = medicamentoService.update(id, medicamentoDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar medicamento", description = "Elimina un medicamento del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID del medicamento a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        MedicamentosGetDTO medicamento = medicamentoService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo eliminar - medicamento no encontrado"));
        medicamentoService.delete(medicamento.getId());
        return new ResponseEntity<>("Medicamento eliminado", HttpStatus.OK);
    }

    @Operation(summary = "Obtener medicamentos por proveedor", description = "Devuelve los medicamentos asociados a un proveedor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamentos encontrados exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron medicamentos para el proveedor"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<MedicamentosGetDTO>> obtenerMedicamentosPorProveedor(
            @Parameter(description = "ID del proveedor", example = "1", required = true)
            @PathVariable Integer proveedorId) {
        List<MedicamentosGetDTO> medicamentos = medicamentoService.proveedorId(proveedorId);
        if (medicamentos == null || medicamentos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron coincidencias");
        }
        return new ResponseEntity<>(medicamentos, HttpStatus.OK);
    }

    @Operation(summary = "Obtener medicamentos por receta", description = "Devuelve los medicamentos asociados a una receta específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamentos encontrados exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron medicamentos para la receta"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/receta/{recetaId}")
    public ResponseEntity<List<MedicamentosGetDTO>> obtenerMedicamentosPorReceta(
            @Parameter(description = "ID de la receta", example = "1", required = true)
            @PathVariable Integer recetaId) {
        List<MedicamentosGetDTO> medicamentos = medicamentoService.recetaId(recetaId);
        if (medicamentos == null || medicamentos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron coincidencias");
        }
        return new ResponseEntity<>(medicamentos, HttpStatus.OK);
    }

    @Operation(summary = "Obtener medicamento por ID específico", description = "Devuelve un medicamento específico usando método alternativo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/obtener/{medicamentoId}")
    public ResponseEntity<MedicamentosGetDTO> obtenerMedicamentosPorId(
            @Parameter(description = "ID del medicamento", example = "1", required = true)
            @PathVariable Integer medicamentoId) {
        MedicamentosGetDTO medicamento = medicamentoService.obtenerMedicamentosPorId(medicamentoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron coincidencias"));
        return new ResponseEntity<>(medicamento, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar stock de medicamento", description = "Actualiza la cantidad en stock de un medicamento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/stock/{id}")
    public ResponseEntity<String> actualizarStock(
            @Parameter(description = "ID del medicamento", example = "1", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Cantidad a actualizar", example = "50", required = true)
            @RequestParam Integer cantidad) {
        medicamentoService.actualizarStock(id, cantidad);
        return new ResponseEntity<>("Stock actualizado", HttpStatus.OK);
    }
}
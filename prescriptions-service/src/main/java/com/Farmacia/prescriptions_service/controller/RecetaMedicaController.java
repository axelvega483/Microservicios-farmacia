package com.Farmacia.prescriptions_service.controller;

import com.Farmacia.prescriptions_service.DTO.RecetaMedicaGetDTO;
import com.Farmacia.prescriptions_service.DTO.RecetaMedicaPostDTO;
import com.Farmacia.prescriptions_service.DTO.RecetaMedicaUptadeDTO;
import com.Farmacia.prescriptions_service.service.IRecetaService;
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

@RestController
@CrossOrigin("*")
@RequestMapping("receta")
@Tag(name = "Recetas Médicas", description = "Controlador para operaciones de recetas médicas")
public class RecetaMedicaController {

    @Autowired
    private IRecetaService recetaService;

    @Operation(summary = "Listar todas las recetas médicas", description = "Devuelve una lista con todas las recetas médicas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas médicas listadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<RecetaMedicaGetDTO>> findAll() {
        List<RecetaMedicaGetDTO> dto = recetaService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Obtener receta médica por ID", description = "Devuelve una receta médica específica basada en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta médica encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Receta médica no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<RecetaMedicaGetDTO> findById(
            @Parameter(description = "ID de la receta médica a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        RecetaMedicaGetDTO receta = recetaService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receta Medica no encontrada"));
        return new ResponseEntity<>(receta, HttpStatus.OK);
    }

    @Operation(summary = "Crear nueva receta médica", description = "Registra una nueva receta médica en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receta médica creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping()
    public ResponseEntity<String> create(
            @Parameter(description = "Datos de la receta médica a crear", required = true)
            @Valid @RequestBody RecetaMedicaPostDTO recetaDto) {
        RecetaMedicaGetDTO dto = recetaService.create(recetaDto);
        return new ResponseEntity<>("Receta medica cargada", HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar receta médica existente", description = "Actualiza la información de una receta médica existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta médica actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Receta médica no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<String> update(
            @Parameter(description = "Datos actualizados de la receta médica", required = true)
            @Valid @RequestBody RecetaMedicaUptadeDTO recetaMedicaDto,
            @Parameter(description = "ID de la receta médica a actualizar", example = "1", required = true)
            @PathVariable Integer id) {
        RecetaMedicaGetDTO dto = recetaService.update(id, recetaMedicaDto);
        return new ResponseEntity<>("Receta medica actualizada", HttpStatus.OK);
    }

    @Operation(summary = "Eliminar receta médica", description = "Elimina una receta médica del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta médica eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Receta médica no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID de la receta médica a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        RecetaMedicaGetDTO recetaMedica = recetaService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no se pudo dar de baja - receta no encontrada"));
        recetaService.delete(recetaMedica.getId());
        return new ResponseEntity<>("receta medica eliminada", HttpStatus.OK);
    }

    @Operation(summary = "Obtener recetas por cliente", description = "Devuelve las recetas médicas asociadas a un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron recetas para el cliente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<RecetaMedicaGetDTO>> findByCliente(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Integer id) {
        List<RecetaMedicaGetDTO> recetas = recetaService.findByCliente(id);
        if (recetas == null || recetas.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron recetas para el cliente");
        }
        return new ResponseEntity<>(recetas, HttpStatus.OK);
    }
}
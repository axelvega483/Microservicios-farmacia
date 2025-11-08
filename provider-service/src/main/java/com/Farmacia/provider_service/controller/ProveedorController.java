package com.Farmacia.provider_service.controller;

import com.Farmacia.provider_service.DTO.ProveedorGetDTO;
import com.Farmacia.provider_service.DTO.ProveedorPostDTO;
import com.Farmacia.provider_service.DTO.ProveedorUpdateDTO;
import com.Farmacia.provider_service.service.IProveedorService;
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
@RequestMapping("proveedor")
@Tag(name = "Proveedores", description = "Controlador para operaciones de proveedores")
public class ProveedorController {

    @Autowired
    private IProveedorService proveedorService;

    @Operation(summary = "Listar todos los proveedores", description = "Devuelve una lista con todos los proveedores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedores listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<ProveedorGetDTO>> findAll() {
        List<ProveedorGetDTO> dto = proveedorService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Obtener proveedor por ID", description = "Devuelve un proveedor específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<ProveedorGetDTO> findById(
            @Parameter(description = "ID del proveedor a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        ProveedorGetDTO proveedor = proveedorService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe proveedor"));
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    @Operation(summary = "Crear nuevo proveedor", description = "Registra un nuevo proveedor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ProveedorGetDTO> create(
            @Parameter(description = "Datos del proveedor a crear", required = true)
            @Valid @RequestBody ProveedorPostDTO proveedorDTO) {
        ProveedorGetDTO dto = proveedorService.create(proveedorDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar proveedor existente", description = "Actualiza la información de un proveedor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<ProveedorGetDTO> update(
            @Parameter(description = "Datos actualizados del proveedor", required = true)
            @Valid @RequestBody ProveedorUpdateDTO proveedorDTO,
            @Parameter(description = "ID del proveedor a actualizar", example = "1", required = true)
            @PathVariable Integer id) {
        ProveedorGetDTO dto = proveedorService.update(id, proveedorDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID del proveedor a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        ProveedorGetDTO proveedor = proveedorService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo eliminar - proveedor no encontrado"));
        proveedorService.delete(proveedor.getId());
        return new ResponseEntity<>("Proveedor eliminado", HttpStatus.OK);
    }

    @Operation(summary = "Obtener información básica de proveedor", description = "Devuelve información básica de un proveedor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/basico/{id}")
    public ResponseEntity<ProveedorGetDTO> obtenerProveedorBasico(
            @Parameter(description = "ID del proveedor para información básica", example = "1", required = true)
            @PathVariable Integer id) {
        ProveedorGetDTO dto = proveedorService.proveedorId(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe proveedor"));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
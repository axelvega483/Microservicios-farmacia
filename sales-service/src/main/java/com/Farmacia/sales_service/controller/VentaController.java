package com.Farmacia.sales_service.controller;

import com.Farmacia.sales_service.DTO.VentaGetDTO;
import com.Farmacia.sales_service.DTO.VentaPostDTO;
import com.Farmacia.sales_service.service.IVentaService;
import com.Farmacia.sales_service.service.PdfGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("venta")
@Tag(name = "Ventas", description = "Controlador para operaciones de ventas")
public class VentaController {

    @Autowired
    private IVentaService ventasService;

    @Autowired
    private PdfGeneratorService pdfGenerator;

    @Operation(summary = "Listar todas las ventas", description = "Devuelve una lista con todas las ventas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ventas listadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<VentaGetDTO>> findAll() {
        List<VentaGetDTO> dto = ventasService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Obtener venta por ID", description = "Devuelve una venta específica basada en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<VentaGetDTO> findById(
            @Parameter(description = "ID de la venta a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        VentaGetDTO venta = ventasService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro venta"));
        return new ResponseEntity<>(venta, HttpStatus.OK);
    }

    @Operation(summary = "Crear nueva venta", description = "Registra una nueva venta en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<String> create(
            @Parameter(description = "Datos de la venta a crear", required = true)
            @Valid @RequestBody VentaPostDTO ventaDTO) {
        VentaGetDTO dto = ventasService.create(ventaDTO);
        return new ResponseEntity<>("Venta creada", HttpStatus.CREATED);
    }

    @Operation(summary = "Anular venta", description = "Anula una venta existente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta anulada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/anular/{id}")
    public ResponseEntity<String> cancel(
            @Parameter(description = "ID de la venta a anular", example = "1", required = true)
            @PathVariable Integer id) {
        VentaGetDTO venta = ventasService.cancel(id);
        return new ResponseEntity<>("Venta anulada", HttpStatus.OK);
    }

    @Operation(summary = "Descargar factura PDF", description = "Genera y descarga la factura en formato PDF de una venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura generada y descargada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada o factura no generada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al generar la factura")
    })
    @GetMapping("/factura/{id}")
    public ResponseEntity<InputStreamResource> descargarFactura(
            @Parameter(description = "ID de la venta para generar factura", example = "1", required = true)
            @PathVariable Integer id) {
        VentaGetDTO venta = ventasService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada"));

        String rutaPDF = pdfGenerator.generarFacturaPDF(venta);

        File pdfFile = new File(rutaPDF);
        if (!pdfFile.exists()) {
            throw new EntityNotFoundException("Factura no generada correctamente");
        }

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=factura-" + venta.getId() + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfFile.length())
                    .body(resource);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error al acceder al archivo PDF: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtener ventas por usuario", description = "Devuelve las ventas asociadas a un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ventas encontradas exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VentaGetDTO>> obtenerVentasPorUsuario(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @PathVariable Integer userId) {
        List<VentaGetDTO> dto = ventasService.obtenerVentasPorUsuario(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Obtener ventas por cliente", description = "Devuelve las ventas asociadas a un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ventas encontradas exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VentaGetDTO>> obtenerVentasPorCliente(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Integer clienteId) {
        List<VentaGetDTO> dto = ventasService.obtenerVentasPorCliente(clienteId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
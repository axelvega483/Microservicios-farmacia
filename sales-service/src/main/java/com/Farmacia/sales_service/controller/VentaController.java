package com.Farmacia.sales_service.controller;

import com.Farmacia.sales_service.DTO.VentaGetDTO;
import com.Farmacia.sales_service.DTO.VentaPostDTO;
import com.Farmacia.sales_service.service.IVentaService;
import com.Farmacia.sales_service.service.PdfGeneratorService;
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
@RequestMapping("sales")
public class VentaController {

    @Autowired
    private IVentaService ventasService;

    @Autowired
    private PdfGeneratorService pdfGenerator;

    @GetMapping
    public ResponseEntity<List<VentaGetDTO>> findAll() {
        List<VentaGetDTO> dto = ventasService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<VentaGetDTO> findById(@PathVariable Integer id) {
        VentaGetDTO venta = ventasService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro venta"));
        return new ResponseEntity<>(venta, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody VentaPostDTO ventaDTO) {
        VentaGetDTO dto = ventasService.create(ventaDTO);
        return new ResponseEntity<>("Venta creada", HttpStatus.CREATED);
    }


    @PutMapping("/anular/{id}")
    public ResponseEntity<String> cancel(@PathVariable Integer id) {
        VentaGetDTO venta = ventasService.cancel(id);
        return new ResponseEntity<>("Venta anulada", HttpStatus.OK);
    }


    @GetMapping("/factura/{id}")
    public ResponseEntity<InputStreamResource> descargarFactura(@PathVariable Integer id) {
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
                            "attachment; filename=factura-" + venta.id() + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfFile.length())
                    .body(resource);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error al acceder al archivo PDF: " + e.getMessage());
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VentaGetDTO>> obtenerVentasPorUsuario(@PathVariable Integer userId) {
        List<VentaGetDTO> dto = ventasService.obtenerVentasPorUsuario(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VentaGetDTO>> obtenerVentasPorCliente(@PathVariable Integer clienteId) {
        List<VentaGetDTO> dto = ventasService.obtenerVentasPorCliente(clienteId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
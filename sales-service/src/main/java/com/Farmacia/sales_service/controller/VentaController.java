package com.Farmacia.sales_service.controller;

import com.Farmacia.sales_service.DTO.VentaGetDTO;
import com.Farmacia.sales_service.DTO.VentaPostDTO;
import com.Farmacia.sales_service.service.IVentaService;
import com.Farmacia.sales_service.service.PdfGeneratorService;
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
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("venta")
public class VentaController {

    @Autowired
    private IVentaService ventasService;

    @Autowired
    private PdfGeneratorService pdfGenerator;


    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<VentaGetDTO> dto = ventasService.findAll();
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            VentaGetDTO venta = ventasService.findById(id).orElse(null);
            if (venta != null) {
                return new ResponseEntity<>(venta, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontro venta", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VentaPostDTO ventaDTO) {
        try {
            VentaGetDTO dto = ventasService.create(ventaDTO);
            return new ResponseEntity<>("Venta creada", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<?> cancel(@PathVariable Integer id) {
        try {
            VentaGetDTO venta = ventasService.cancel(id);
            return new ResponseEntity<>("Venta anulada", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Venta no encontrada" + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/factura/{id}")
    public ResponseEntity<?> descargarFactura(@PathVariable Integer id) {
        try {
            VentaGetDTO venta = ventasService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

            String rutaPDF = pdfGenerator.generarFacturaPDF(venta);

            File pdfFile = new File(rutaPDF);
            if (!pdfFile.exists()) {
                return new ResponseEntity<>("Factura no generada correctamente", HttpStatus.NOT_FOUND);
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=factura-" + venta.getId() + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfFile.length())
                    .body(resource);

        } catch (Exception e) {
            return new ResponseEntity<>("Error al generar factura: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> obtenerVentasPorUsuario(@PathVariable Integer userId) {
        try {
            List<VentaGetDTO> dto = ventasService.obtenerVentasPorUsuario(userId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> obtenerVentasPorCliente(@PathVariable Integer clienteId) {
        try {
            List<VentaGetDTO> dto = ventasService.obtenerVentasPorCliente(clienteId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.Farmacia.sales_service.service;

import com.Farmacia.sales_service.DTO.ClientesGetDTO;
import com.Farmacia.sales_service.DTO.MedicamentosGetDTO;
import com.Farmacia.sales_service.DTO.VentaDetalleDTO;
import com.Farmacia.sales_service.DTO.VentaGetDTO;
import com.Farmacia.sales_service.model.Venta;
import com.Farmacia.sales_service.repository.VentaRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PdfGeneratorService {
    @Value("${app.ruta.PDF}")
    private String RUTA_PDF;
    @Autowired
    private VentaRepository repo;
    @Autowired
    private ClienteFeignClient clienteService;

    @Autowired
    private MedicamentoFeignClient medicamentoService;

    public String generarFacturaPDF(VentaGetDTO venta) {
        String pathArchivo = null;
        Document document = null;

        try {
            File directory = new File(RUTA_PDF);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            pathArchivo = directory + File.separator + "factura-" + venta.getId() + ".pdf";
            document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(pathArchivo));

            document.open();
            agregarContenidoFactura(document, venta);
            document.close();

            return pathArchivo;

        } catch (Exception e) {
            if (document != null && document.isOpen()) {
                document.close();
            }
            throw new RuntimeException("Error al generar PDF: " + e.getMessage(), e);
        }
    }

    private void agregarContenidoFactura(Document document, VentaGetDTO venta) throws DocumentException {
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
        Font fontCuerpo = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);

        // Encabezado
        Paragraph encabezado = new Paragraph("Factura de Compra Farmacia", fontTitulo);
        encabezado.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(encabezado);

        // Fecha
        Paragraph fecha = new Paragraph("Fecha: " + venta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontCuerpo);
        fecha.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(fecha);

        Optional<Venta> ventaCliente = repo.findById(venta.getId()).filter(Venta::getActivo);
        ClientesGetDTO cliente = obtenerCliente(ventaCliente.get().getClienteId());

        Paragraph infoCliente = new Paragraph("Cliente: " + cliente.getNombre(), fontCuerpo);
        document.add(infoCliente);
        document.add(new Paragraph(" "));

        // Tabla de productos
        PdfPTable table = new PdfPTable(4); // 4 columnas
        table.setWidthPercentage(100);

        // Encabezados de tabla
        table.addCell(crearCelda("Producto", fontCuerpo, true));
        table.addCell(crearCelda("Cantidad", fontCuerpo, true));
        table.addCell(crearCelda("Precio Unit.", fontCuerpo, true));
        table.addCell(crearCelda("Subtotal", fontCuerpo, true));

        // Detalles
        for (VentaDetalleDTO detalle : venta.getDetalleventas()) {
            MedicamentosGetDTO medicamento = obtenerMedicamento(detalle.getMedicamentoId());

            table.addCell(crearCelda(medicamento.getNombre(), fontCuerpo, false));
            table.addCell(crearCelda(String.valueOf(detalle.getCantidad()), fontCuerpo, false));
            table.addCell(crearCelda("$" + detalle.getPrecioUnitario(), fontCuerpo, false));
            table.addCell(crearCelda("$" + (detalle.getCantidad() * detalle.getPrecioUnitario()), fontCuerpo, false));
        }

        document.add(table);
        document.add(new Paragraph(" "));

        // Total
        Paragraph total = new Paragraph("Total: $" + venta.getTotal(), fontTitulo);
        total.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(total);
    }

    private PdfPCell crearCelda(String texto, Font font, boolean negrita) {
        Font fontCelda = negrita ? new Font(font.getFamily(), font.getSize(), Font.BOLD, font.getColor()) : font;
        PdfPCell cell = new PdfPCell(new Phrase(texto, fontCelda));
        cell.setPadding(5);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    @CircuitBreaker(name = "customer-service", fallbackMethod = "fallbackObtenerCliente")
    @Retry(name = "customer-service")
    private ClientesGetDTO obtenerCliente(Integer clienteId) {
        ClientesGetDTO cliente = clienteService.obtenerId(clienteId);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }
        return cliente;
    }

    private ClientesGetDTO fallbackObtenerCliente(Throwable throwable) {
        throw new RuntimeException("Servicio de clientes no disponible" + throwable.getMessage());
    }

    @CircuitBreaker(name = "catalog-service", fallbackMethod = "fallbackObtenerMedicamento")
    @Retry(name = "catalog-service")
    private MedicamentosGetDTO obtenerMedicamento(Integer medicamentoId) {
        MedicamentosGetDTO medicamento = medicamentoService.obtenerMedicamentosPorId(medicamentoId);
        if (medicamento == null) {
            throw new RuntimeException("Medicamento no encontrado");
        }
        return medicamento;
    }

    private MedicamentosGetDTO fallbackObtenerMedicamento(Throwable throwable) {
        throw new RuntimeException("Servicio de medicamentos no disponible" + throwable.getMessage());
    }
}
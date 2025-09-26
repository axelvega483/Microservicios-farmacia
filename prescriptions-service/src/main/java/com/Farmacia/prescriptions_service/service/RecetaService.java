package com.Farmacia.prescriptions_service.service;

import com.Farmacia.prescriptions_service.DTO.*;
import com.Farmacia.prescriptions_service.model.RecetaMedica;
import com.Farmacia.prescriptions_service.repository.RecetaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecetaService implements IRecetaService {
    @Autowired
    private RecetaRepository repo;
    @Autowired
    private RecetaMedicaMapper mapper;
    @Autowired
    private MedicamentoFeingClient medicamento;
    @Autowired
    private ClienteFeignClient cliente;


    @Override
    public RecetaMedicaGetDTO create(RecetaMedicaPostDTO post) {
        if (post.getVigenteHasta().isBefore(LocalDate.now())) {
            throw new EntityNotFoundException("La fecha de vigencia no puede ser anterior a la fecha actual");
        }
        if (post.getMedicamentoIds().size() > 15) {
            throw new IllegalArgumentException("Una receta no puede tener más de 15 medicamentos");
        }
        RecetaMedica receta = mapper.create(post);
        RecetaMedica saved = repo.save(receta);

        return mapper.toDTO(saved);
    }

    @Override
    public RecetaMedicaGetDTO update(Integer id, RecetaMedicaUptadeDTO put) {
        RecetaMedica receta = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada"));
        if (put.getMedicamentoIds() != null && !put.getMedicamentoIds().isEmpty()) {
            if (put.getMedicamentoIds().size() > 15) {
                throw new IllegalArgumentException("Una receta no puede tener más de 15 medicamentos");
            }


        }
        receta = mapper.update(receta, put);
        RecetaMedica saved = repo.save(receta);
        return mapper.toDTO(saved);
    }

    @Override
    public Optional<RecetaMedicaGetDTO> findById(Integer id) {
        Optional<RecetaMedica> receta = repo.findById(id).filter(RecetaMedica::getActivo);
        if (receta.isPresent()) {
            RecetaMedicaGetDTO dto = mapper.toDTO(receta.get());
            ClientesGetDTO nombre = obtenerNombre(dto.getId());
            List<MedicamentosGetDTO> medicamentos = obtnerListaMedicamento(dto.getId());
            dto.setClienteNombre(nombre.getNombre());
            dto.setMedicamentosNombres(medicamentos);
            return Optional.of(dto);
        }

        return Optional.empty();
    }

    @CircuitBreaker(name = "customer-service", fallbackMethod = "fallbackObtenerClientes")
    @Retry(name = "cusmoter-service")
    private ClientesGetDTO obtenerNombre(Integer clienteId) {
        return cliente.findName(clienteId);
    }

    @CircuitBreaker(name = "catalog-service", fallbackMethod = "fallbackObtenerMedicamentos")
    @Retry(name = "catalog-service")
    private List<MedicamentosGetDTO> obtnerListaMedicamento(Integer recetaId) {
        return medicamento.obtenerMedicamentosPorReceta(recetaId).stream()
                .filter(MedicamentosGetDTO::getRecetaRequerida)
                .collect(Collectors.toList());
    }

    private Optional<ClientesGetDTO> fallbackObtenerClientes(Integer clienteId, Throwable throwable) {
        log.warn("Fallback para ventas del cliente: {}, Error: {}", clienteId, throwable.getMessage());
        return Optional.empty();
    }

    private List<MedicamentosGetDTO> fallbackObtenerMedicamentos(Integer clienteId, Throwable throwable) {
        log.warn("Fallback para recetas del cliente: {}, Error: {}", clienteId, throwable.getMessage());
        return Collections.emptyList();
    }

    @Override
    public void delete(Integer id) {
        Optional<RecetaMedica> recetaOptional = repo.findById(id);
        if (recetaOptional.isPresent()) {
            RecetaMedica recetaMedica = recetaOptional.get();
            recetaMedica.setActivo(Boolean.FALSE);
            repo.save(recetaMedica);
        }
    }

    @Override
    public List<RecetaMedicaGetDTO> findAll() {
        List<RecetaMedica> recetas = repo.findAll();
        List<RecetaMedicaGetDTO> dtos = new ArrayList<>();
        for (RecetaMedica receta : recetas) {
            RecetaMedicaGetDTO dto = mapper.toDTO(receta);
            ClientesGetDTO nombre = obtenerNombre(dto.getId());
            List<MedicamentosGetDTO> medicamentos = obtnerListaMedicamento(dto.getId());
            dto.setClienteNombre(nombre.getNombre());
            dto.setMedicamentosNombres(medicamentos);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<RecetaMedicaGetDTO> findByCliente(Integer id) {
        List<RecetaMedica> recetas = repo.findByClienteIdAndActivoTrue(id);
        return recetas.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}

package com.Farmacia.catalog_service.service;

import com.Farmacia.catalog_service.DTO.MedicamentoMapper;
import com.Farmacia.catalog_service.DTO.MedicamentoPostDTO;
import com.Farmacia.catalog_service.DTO.MedicamentoUpdateDTO;
import com.Farmacia.catalog_service.DTO.MedicamentosGetDTO;
import com.Farmacia.catalog_service.model.Medicamento;
import com.Farmacia.catalog_service.repository.MedicamentoRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoService implements IMedicamentoService {
    @Autowired
    private MedicamentoRepository repo;
    @Autowired
    private MedicamentoMapper mapper;
    @Autowired
    private ProveedorFeingClient proveedor;

    @Override
    public MedicamentosGetDTO create(MedicamentoPostDTO post) {
        if (medicamentoExiste(post.getNombre(), post.getProveedor())) {
            throw new EntityExistsException("El medicamento ya existe");
        }
        Medicamento medicamento = mapper.create(post);
        Medicamento saved = repo.save(medicamento);
        return mapper.toDTO(saved);
    }

    @Override
    public boolean medicamentoExiste(String nombre, Integer proveedorId) {
        return repo.findByNombreAndProveedor(nombre, proveedorId).isPresent();
    }

    @Override
    public void delete(Integer id) {
        Optional<Medicamento> medicamento = repo.findById(id);
        if (medicamento.isPresent()) {
            Medicamento m = medicamento.get();
            m.setActivo(Boolean.FALSE);
            repo.save(m);
        }
    }

    @Override
    @CircuitBreaker(name = "provider-service", fallbackMethod = "findByIdNoProveedor")
    @Retry(name = "provider-service")
    public Optional<MedicamentosGetDTO> findById(Integer id) {
        Optional<Medicamento> medicamento = repo.findById(id).filter(Medicamento::getActivo);
        if (medicamento.isPresent()) {
            MedicamentosGetDTO dto = mapper.toDTO(medicamento.get());
            dto.setProveedor(proveedor.obtenerProveedorPorId(dto.getProveedor().getId()));
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Optional<MedicamentosGetDTO> findByIdNoProveedor(Integer id, Throwable throwable) {
        System.err.println("Fallback ejecutado para findById(): " + throwable.getMessage());
        Optional<Medicamento> medicamento = repo.findById(id).filter(Medicamento::getActivo);
        if (medicamento.isPresent()) {
            MedicamentosGetDTO dto = mapper.toDTO(medicamento.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    @CircuitBreaker(name = "provider-service", fallbackMethod = "findByAllNoProveedor")
    @Retry(name = "provider-service")
    public List<MedicamentosGetDTO> findAll() {
        List<Medicamento> medicamentos = repo.findAll();
        List<MedicamentosGetDTO> dtos = new ArrayList<>();
        for (Medicamento medicamento : medicamentos) {
            MedicamentosGetDTO dto = mapper.toDTO(medicamento);
            dto.setProveedor(proveedor.obtenerProveedorPorId(dto.getProveedor().getId()));
            dtos.add(dto);
        }
        return dtos;
    }

    public List<MedicamentosGetDTO> findByAllNoProveedor(Throwable throwable) {
        System.err.println("Fallback ejecutado para findAll(): " + throwable.getMessage());
        List<Medicamento> medicamentos = repo.findAll();
        List<MedicamentosGetDTO> dtos = new ArrayList<>();
        for (Medicamento medicamento : medicamentos) {
            MedicamentosGetDTO dto = mapper.toDTO(medicamento);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<MedicamentosGetDTO> findByName(String nombre) {
        List<Medicamento> medicamentos = repo.findByNombre(nombre);
        List<MedicamentosGetDTO> dtos = new ArrayList<>();
        for (Medicamento medicamento : medicamentos) {
            MedicamentosGetDTO dto = mapper.toDTO(medicamento);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public MedicamentosGetDTO update(Integer id, MedicamentoUpdateDTO put) {
        Medicamento medicamento = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento no encontrado"));
        medicamento = mapper.update(medicamento, put);
        Medicamento saved = repo.save(medicamento);
        return mapper.toDTO(saved);
    }
}

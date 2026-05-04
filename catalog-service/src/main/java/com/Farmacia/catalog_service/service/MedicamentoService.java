package com.Farmacia.catalog_service.service;

import com.Farmacia.catalog_service.DTO.*;
import com.Farmacia.catalog_service.model.Medicamento;
import com.Farmacia.catalog_service.repository.MedicamentoRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (medicamentoExiste(post.nombre(), post.proveedor())) {
            throw new EntityExistsException("El medicamento ya existe");
        }
        Medicamento medicamento = mapper.create(post);
        Medicamento saved = repo.save(medicamento);
        return mapper.toDTO(saved);
    }

    @Override
    public boolean medicamentoExiste(String nombre, Integer proveedorId) {
        return Boolean.TRUE.equals(repo.existsByNombreAndProveedorIdAndActivoTrue(nombre, proveedorId));
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
            ProveedorGetDTO prov = proveedor.obtenerProveedorBasico(medicamento.get().getProveedorId());
            MedicamentosGetDTO dto = mapper.toDTOS(medicamento.get(), prov);
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
    public List<MedicamentosGetDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }


    @Override
    public List<MedicamentosGetDTO> findByName(String nombre) {
        return mapper.toDTOList(repo.findByNombreContainingIgnoreCaseAndActivoTrue(nombre));
    }

    @Override
    public MedicamentosGetDTO update(Integer id, MedicamentoUpdateDTO put) {
        Medicamento medicamento = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento no encontrado"));
        mapper.update(medicamento, put);
        Medicamento saved = repo.save(medicamento);
        return mapper.toDTO(saved);
    }

    @Override
    public List<MedicamentosGetDTO> proveedorId(Integer proveedorId) {
        List<Medicamento> medicamentos = repo.findByProveedorId(proveedorId);
        List<MedicamentosGetDTO> dtos = new ArrayList<>();
        for (Medicamento medicamento : medicamentos) {
            MedicamentosGetDTO dto = mapper.toDTO(medicamento);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<MedicamentosGetDTO> findByIds(List<Integer> ids) {
        List<Medicamento> medicamentos = repo.findAllById(ids);
        return mapper.toDTOList(medicamentos);
    }

    @Override
    public Optional<MedicamentosGetDTO> obtenerMedicamentosPorId(Integer medicamentoId) {
        Optional<Medicamento> medicamento = repo.findById(medicamentoId).filter(Medicamento::getActivo);
        if (medicamento.isPresent()) {
            MedicamentosGetDTO dto = mapper.toDTO(medicamento.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void actualizarStock(Integer id, Integer nuevoStock) {
        Medicamento medicamento = repo.findById(id)
                .filter(Medicamento::getActivo)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado con ID: " + id));

        medicamento.setStock(nuevoStock);
        repo.save(medicamento);
    }
}

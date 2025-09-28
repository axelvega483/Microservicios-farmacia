package com.Farmacia.provider_service.service;

import com.Farmacia.provider_service.DTO.ProveedorGetDTO;
import com.Farmacia.provider_service.DTO.ProveedorMapper;
import com.Farmacia.provider_service.DTO.ProveedorPostDTO;
import com.Farmacia.provider_service.DTO.ProveedorUpdateDTO;
import com.Farmacia.provider_service.model.Proveedor;
import com.Farmacia.provider_service.repository.ProveedorRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService implements IProveedorService {
    @Autowired
    private ProveedorRepository repo;
    @Autowired
    private ProveedorMapper mapper;
    @Autowired
    private MedicamentoFeignClient medicamento;

    @Override
    public ProveedorGetDTO create(ProveedorPostDTO post) {
        if (findByName(post.getNombre()).isPresent()) {
            throw new EntityExistsException("Proveedor existente");
        }
        Proveedor proveedor = mapper.create(post);
        Proveedor saved = repo.save(proveedor);
        return mapper.toDTO(saved);
    }

    @Override
    public ProveedorGetDTO update(Integer id, ProveedorUpdateDTO put) {
        Proveedor proveedor = repo.findById(id).orElse(null);
        if (proveedor == null) {
            throw new EntityExistsException("Proveedor no encontrado");
        }
        proveedor = mapper.update(proveedor, put);
        Proveedor saved = repo.save(proveedor);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        Optional<Proveedor> proveedorOptional = repo.findById(id);
        if (proveedorOptional.isPresent()) {
            Proveedor proveedor = proveedorOptional.get();
            proveedor.setActivo(Boolean.FALSE);
            repo.save(proveedor);
        }
    }

    @Override
    @CircuitBreaker(name = "catalog-service", fallbackMethod = "findByIdNoMedicamento")
    @Retry(name = "catalog-service")
    public Optional<ProveedorGetDTO> findById(Integer id) {
        Optional<Proveedor> proveedor = repo.findById(id).filter(Proveedor::getActivo);
        if (proveedor.isPresent()) {
            ProveedorGetDTO dto = mapper.toDTO(proveedor.get());
            dto.setMedicamentos(medicamento.obtenerMedicamentosPorProveedor(dto.getId()));
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Optional<ProveedorGetDTO> findByIdNoMedicamento(Integer id, Throwable throwable) {
        System.err.println("Fallback ejecutado para findByIdNoMedicamento(): " + throwable.getMessage());
        Optional<Proveedor> proveedor = repo.findById(id).filter(Proveedor::getActivo);
        if (proveedor.isPresent()) {
            ProveedorGetDTO dto = mapper.toDTO(proveedor.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    @CircuitBreaker(name = "catalog-service", fallbackMethod = "findAllNoMedicamento")
    @Retry(name = "catalog-service")
    public List<ProveedorGetDTO> findAll() {
        List<Proveedor> proveedores = repo.findAll();
        List<ProveedorGetDTO> dtos = new ArrayList<>();
        for (Proveedor proveedor : proveedores) {
            ProveedorGetDTO dto = mapper.toDTO(proveedor);
            dto.setMedicamentos(medicamento.obtenerMedicamentosPorProveedor(dto.getId()));
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ProveedorGetDTO> findAllNoMedicamento(Throwable throwable) {
        System.err.println("Fallback ejecutado para findAllNoMedicamento(): " + throwable.getMessage());
        List<Proveedor> proveedores = repo.findAll();
        List<ProveedorGetDTO> dtos = new ArrayList<>();
        for (Proveedor proveedor : proveedores) {
            ProveedorGetDTO dto = mapper.toDTO(proveedor);
            dtos.add(dto);
        }
        return dtos;
    }

    public Optional<ProveedorGetDTO> findByName(String nombre) {
        Optional<Proveedor> proveedor = repo.findByName(nombre).filter(Proveedor::getActivo);
        if (proveedor.isPresent()) {
            ProveedorGetDTO dto = mapper.toDTO(proveedor.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProveedorGetDTO> proveedorId(Integer id) {
        Optional<Proveedor> proveedor = repo.findById(id).filter(Proveedor::getActivo);
        if (proveedor.isPresent()) {
            ProveedorGetDTO dto = mapper.toDTO(proveedor.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }
}

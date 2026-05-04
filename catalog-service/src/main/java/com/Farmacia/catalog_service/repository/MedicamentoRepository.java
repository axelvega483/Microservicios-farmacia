package com.Farmacia.catalog_service.repository;

import com.Farmacia.catalog_service.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {
    Boolean existsByNombreAndProveedorIdAndActivoTrue(String nombre, Integer proveedorId);

    List<Medicamento> findByProveedorId(Integer proveedorId);

    List<Medicamento> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
}

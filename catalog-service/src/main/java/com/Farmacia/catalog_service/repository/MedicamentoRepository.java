package com.Farmacia.catalog_service.repository;

import com.Farmacia.catalog_service.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {
    @Query("SELECT m FROM Medicamento m WHERE m.nombre =:nombre AND m.proveedorId =:proveedorId AND m.activo = TRUE ")
    Optional<Medicamento> findByNombreAndProveedor(String nombre, Integer proveedorId);

    @Query("SELECT m FROM Medicamento m WHERE m.activo = TRUE AND m.nombre LIKE %:nombre%")
    List<Medicamento> findByNombre(String nombre);
}

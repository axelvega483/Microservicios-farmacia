package com.Farmacia.provider_service.repository;

import com.Farmacia.provider_service.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    @Query("SELECT p FROM Proveedor p WHERE LOWER(p.nombre) = LOWER(:nombre)")
    Optional<Proveedor> findByName(String nombre);
}

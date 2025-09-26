package com.Farmacia.prescriptions_service.repository;

import com.Farmacia.prescriptions_service.model.RecetaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<RecetaMedica, Integer> {
    List<RecetaMedica> findByClienteIdAndActivoTrue(Integer clienteId);
}

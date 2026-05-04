package com.Farmacia.customer_service.repository;

import com.Farmacia.customer_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Boolean existsByDniAndActivoTrue(String dni);
}

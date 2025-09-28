package com.Farmacia.sales_service.repository;

import com.Farmacia.sales_service.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByUserIdAndActivoTrue(Integer userId);

    List<Venta> findByClienteIdAndActivoTrue(Integer clienteId);
}

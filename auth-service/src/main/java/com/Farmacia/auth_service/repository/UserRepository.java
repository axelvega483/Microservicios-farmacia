package com.Farmacia.auth_service.repository;

import com.Farmacia.auth_service.model.User;
import com.Farmacia.auth_service.util.RolUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByDniAndActivoTrue(String dni);

    Optional<User> findByEmail(String email);

    Integer countByRol(RolUser rol);
}

package com.Farmacia.auth_service.service;

import com.Farmacia.auth_service.DTO.User.*;
import com.Farmacia.auth_service.model.User;
import com.Farmacia.auth_service.repository.UserRepository;
import com.Farmacia.auth_service.util.RolUser;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserServicie {
    @Autowired
    private UserRepository repo;

    @Autowired
    private VentaFeignClient venta;

    @Autowired
    private MapperDto mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        user.setActivo(Boolean.TRUE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    @CircuitBreaker(name = "sales-service", fallbackMethod = "findByUserNoVenta")
    @Retry(name = "sales-service")
    public Optional<UserGetDTO> findById(Integer id) {
        Optional<User> optUser = repo.findById(id).filter(User::isActivo);
        if (optUser.isPresent()) {
            User user = optUser.get();
            List<UserVentaDTO> ventas = venta.obtenerVentasPorUser(user.getId());
            UserGetDTO dto = mapper.toDTO(user, ventas);
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Optional<UserGetDTO> findByUserNoVenta(Integer id, Throwable throwable) {
        System.err.println("Fallback ejecutado para findByUserNoVenta(): " + throwable.getMessage());
        Optional<User> optUser = repo.findById(id).filter(User::isActivo);
        if (optUser.isPresent()) {
            UserGetDTO dto = mapper.toDTO(optUser.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public List<UserGetDTO> findAll() {
       return mapper.toDTOList(repo.findAll());
    }

    @Override
    public void delete(Integer id) {
        Optional<User> userOptional = repo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActivo(Boolean.FALSE);
            repo.save(user);
        }
    }

    @Override
    public UserGetDTO crear(UserPostDTO post) {
        if (existe(post.dni())) {
            throw new EntityExistsException("El Usuario ya existe");
        }
        User usuario = mapper.toEntity(post);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        User saved = repo.save(usuario);
        return mapper.toDTO(saved);
    }

    @Override
    public UserGetDTO actualizar(Integer id, UserPutDTO put) {
        User user = repo.findById(id).orElse(null);
        if (user == null) {
            throw new EntityExistsException("El Usuario no existe");
        }
        user = mapper.fromUpdateDTO(user, put);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = repo.save(user);
        return mapper.toDTO(save);
    }

    @Override
    public UserGetDTO actualizarRol(Integer id, UserRolDTO rol) {
        System.out.println("=== ACTUALIZAR ROL ===");
        System.out.println("ID a modificar: " + id);
        System.out.println("Nuevo rol: " + rol.rol());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + auth);
        System.out.println("Authentication name: " + (auth != null ? auth.getName() : "null"));
        System.out.println("Authentication authorities: " + (auth != null ? auth.getAuthorities() : "null"));

        if (auth == null || !auth.isAuthenticated()) {
            System.out.println("ERROR: No hay autenticación!");
            throw new IllegalStateException("Usuario no autenticado");
        }

        String email = auth.getName();
        System.out.println("Email autenticado: " + email);

        if (rol.rol() == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }

        User usuarioLogueado = repo.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado"));

        User usuario = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (usuario.getRol() == RolUser.ADMIN && rol.rol() != RolUser.ADMIN) {
            long admins = repo.countByRol(RolUser.ADMIN);
            if (admins <= 1) {
                throw new IllegalStateException("Debe existir al menos un ADMIN");
            }
        }

        if (usuarioLogueado.getId().equals(id)) {
            throw new IllegalStateException("No podés cambiar tu propio rol");
        }

        usuario.setRol(rol.rol());
        repo.save(usuario);
        return mapper.toDTO(usuario);
    }

    @Override
    public Boolean existe(String dni) {
        return repo.findByDniAndActivoTrue(dni).isPresent();
    }


    @Override
    public UserGetDTO obtenerId(Integer userId) {
        Optional<User> optUser = repo.findById(userId).filter(User::isActivo);
        return optUser.map(mapper::toDTO).orElse(null);
    }
}

package com.Farmacia.customer_service.controller;

import com.Farmacia.customer_service.DTO.ClientePostDTO;
import com.Farmacia.customer_service.DTO.ClienteUpdateDTO;
import com.Farmacia.customer_service.DTO.ClientesGetDTO;
import com.Farmacia.customer_service.service.IClienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("customer")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @PostMapping("crear")
    public ResponseEntity<ClientesGetDTO> create(
            @Valid @RequestBody ClientePostDTO postDTO) {
        ClientesGetDTO dto = clienteService.create(postDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<ClientesGetDTO>> findAll() {
        List<ClientesGetDTO> dto = clienteService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<ClientesGetDTO> findById(@PathVariable Integer id) {
        ClientesGetDTO cliente = clienteService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<ClientesGetDTO> update(@PathVariable Integer id, @RequestBody ClienteUpdateDTO putDTO) {
        ClientesGetDTO dto = clienteService.update(id, putDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<ClientesGetDTO> delete(@PathVariable Integer id) {
        ClientesGetDTO cliente = clienteService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("cliente no encontrado"));
        clienteService.delete(cliente.id());
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }


    @GetMapping("/nombre/{id}")
    public ResponseEntity<ClientesGetDTO> findName(@PathVariable Integer id) {
        ClientesGetDTO cliente = clienteService.findName(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
}
package com.vrsoftware.gerenciadorDeVendas.controller;

import com.vrsoftware.gerenciadorDeVendas.dto.ClientDTO;
import com.vrsoftware.gerenciadorDeVendas.entity.ClientEntity;
import com.vrsoftware.gerenciadorDeVendas.mapper.ClientMapper;
import com.vrsoftware.gerenciadorDeVendas.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        ClientEntity clientEntity = clientMapper.toEntity(clientDTO);
        ClientEntity clientCreated = clientService.createClient(clientEntity);
        ClientDTO clientDTOCreated = clientMapper.toDTO(clientCreated);
        return new ResponseEntity<>(clientDTOCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> listClient() {
        List<ClientEntity> clients = clientService.listClient();
        List<ClientDTO> clientDTOCreated = clientMapper.toDTOList(clients);
        return new ResponseEntity<>(clientDTOCreated, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ClientDTO> updateClient(@Valid @RequestBody ClientDTO clientDTO) {
        ClientEntity clientEntity = clientMapper.toEntity(clientDTO);
        ClientEntity clientUpdate = clientService.updateClient(clientEntity);
        ClientDTO clientDTOCreated = clientMapper.toDTO(clientUpdate);
        return new ResponseEntity<>(clientDTOCreated, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}

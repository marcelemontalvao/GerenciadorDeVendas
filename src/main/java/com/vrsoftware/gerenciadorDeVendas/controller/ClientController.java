package com.vrsoftware.gerenciadorDeVendas.controller;

import com.vrsoftware.gerenciadorDeVendas.dto.client.ClientRequestDTO;
import com.vrsoftware.gerenciadorDeVendas.dto.client.ClientResponseDTO;
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
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        ClientEntity clientEntity = clientMapper.toEntity(clientRequestDTO);
        ClientEntity clientCreated = clientService.createClient(clientEntity);
        ClientResponseDTO clientResponseDTOCreated = clientMapper.toDTO(clientCreated);
        return new ResponseEntity<>(clientResponseDTOCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> listClient() {
        List<ClientEntity> clients = clientService.listClient();
        List<ClientResponseDTO> clientResponseDTOCreated = clientMapper.toDTOList(clients);
        return new ResponseEntity<>(clientResponseDTOCreated, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable("id") Long id, @Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        ClientEntity clientEntity = clientMapper.toEntity(clientRequestDTO);
        ClientEntity clientUpdate = clientService.updateClient(id, clientEntity);
        ClientResponseDTO clientResponseDTOCreated = clientMapper.toDTO(clientUpdate);
        return new ResponseEntity<>(clientResponseDTOCreated, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}

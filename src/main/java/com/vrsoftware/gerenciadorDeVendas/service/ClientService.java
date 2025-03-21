package com.vrsoftware.gerenciadorDeVendas.service;

import com.vrsoftware.gerenciadorDeVendas.entity.ClientEntity;
import com.vrsoftware.gerenciadorDeVendas.exception.ClientNotFoundException;
import com.vrsoftware.gerenciadorDeVendas.repository.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientEntity createClient(ClientEntity client) {
        return clientRepository.save(client);
    }

    public List<ClientEntity> listClient() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id").
                and(Sort.by(Sort.Direction.ASC, "nome"));

        return clientRepository.findAll(sort);
    }

    public ClientEntity updateClient(Long id, ClientEntity updatedClient) throws ClientNotFoundException {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
        BeanUtils.copyProperties(updatedClient, clientEntity, "id");
        return clientRepository.save(clientEntity);
    }

    public void deleteClient(Long id) throws ClientNotFoundException {
        if(!clientRepository.existsById(id)) {
            throw new ClientNotFoundException();
        }
        clientRepository.deleteById(id);
    }
}

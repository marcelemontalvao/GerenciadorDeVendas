package com.vrsoftware.gerenciadorDeVendas.mapper;

import com.vrsoftware.gerenciadorDeVendas.dto.client.ClientRequestDTO;
import com.vrsoftware.gerenciadorDeVendas.dto.client.ClientResponseDTO;
import com.vrsoftware.gerenciadorDeVendas.entity.ClientEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientEntity toEntity(ClientRequestDTO clientRequestDTO);
    ClientResponseDTO toDTO(ClientEntity clientEntity);
    List<ClientResponseDTO> toDTOList(List<ClientEntity> clientEntities);
}

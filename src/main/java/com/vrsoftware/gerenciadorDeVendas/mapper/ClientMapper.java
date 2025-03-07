package com.vrsoftware.gerenciadorDeVendas.mapper;

import com.vrsoftware.gerenciadorDeVendas.dto.ClientDTO;
import com.vrsoftware.gerenciadorDeVendas.entity.ClientEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientEntity toEntity(ClientDTO clientDTO);
    ClientDTO toDTO(ClientEntity clientEntity);
    List<ClientDTO> toDTOList(List<ClientEntity> clientEntities);
}

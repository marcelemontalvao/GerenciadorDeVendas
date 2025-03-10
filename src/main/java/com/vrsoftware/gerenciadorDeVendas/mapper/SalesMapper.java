package com.vrsoftware.gerenciadorDeVendas.mapper;

import com.vrsoftware.gerenciadorDeVendas.dto.SalesItemDTO;
import com.vrsoftware.gerenciadorDeVendas.dto.SalesRequestDTO;
import com.vrsoftware.gerenciadorDeVendas.dto.SalesResponseDTO;
import com.vrsoftware.gerenciadorDeVendas.entity.SalesEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalesMapper {

    SalesResponseDTO toDTO(SalesEntity venda);
    SalesEntity toEntity(SalesRequestDTO vendaDTO);
    //SalesEntity toEntityItem(SalesItemDTO vendaDTO);
}

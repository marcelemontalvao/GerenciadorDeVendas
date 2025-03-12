package com.vrsoftware.gerenciadorDeVendas.mapper;

import com.vrsoftware.gerenciadorDeVendas.dto.sale.SalesRequestDTO;
import com.vrsoftware.gerenciadorDeVendas.dto.sale.SalesResponseDTO;
import com.vrsoftware.gerenciadorDeVendas.entity.SalesEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesMapper {

    SalesResponseDTO toDTO(SalesEntity venda);
    SalesEntity toEntity(SalesRequestDTO vendaDTO);
    //SalesEntity toEntityItem(SalesItemDTO vendaDTO);
}

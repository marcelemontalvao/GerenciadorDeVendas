package com.vrsoftware.gerenciadorDeVendas.mapper;

import com.vrsoftware.gerenciadorDeVendas.dto.ProductRequestDTO;
import com.vrsoftware.gerenciadorDeVendas.dto.ProductResponseDTO;
import com.vrsoftware.gerenciadorDeVendas.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(ProductRequestDTO productRequestDTO);
    ProductResponseDTO toDTO(ProductEntity productEntity);
    List<ProductResponseDTO> toDTOList(List<ProductEntity> productEntities);
}

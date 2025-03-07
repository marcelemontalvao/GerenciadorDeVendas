package com.vrsoftware.gerenciadorDeVendas.mapper;

import com.vrsoftware.gerenciadorDeVendas.dto.ProductDTO;
import com.vrsoftware.gerenciadorDeVendas.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(ProductDTO clientDTO);
    ProductDTO toDTO(ProductEntity productEntity);
    List<ProductDTO> toDTOList(List<ProductEntity> productEntities);
}

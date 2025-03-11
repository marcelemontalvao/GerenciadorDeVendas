package com.vrsoftware.gerenciadorDeVendas.controller;

import com.vrsoftware.gerenciadorDeVendas.dto.ProductRequestDTO;
import com.vrsoftware.gerenciadorDeVendas.dto.ProductResponseDTO;
import com.vrsoftware.gerenciadorDeVendas.entity.ProductEntity;
import com.vrsoftware.gerenciadorDeVendas.mapper.ProductMapper;
import com.vrsoftware.gerenciadorDeVendas.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductEntity productEntity = productMapper.toEntity(productRequestDTO);
        ProductEntity productCreated = productService.createProduct(productEntity);
        ProductResponseDTO productResponseDTOCreated = productMapper.toDTO(productCreated);
        return new ResponseEntity<>(productResponseDTOCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listProduct() {
        List<ProductEntity> products = productService.listProduct();
        List<ProductResponseDTO> productResponseDTOCreated = productMapper.toDTOList(products);
        return new ResponseEntity<>(productResponseDTOCreated, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductEntity productEntity = productMapper.toEntity(productRequestDTO);
        ProductEntity productUpdate = productService.updateProduct(id, productEntity);
        ProductResponseDTO productResponseDTOCreated = productMapper.toDTO(productUpdate);
        return new ResponseEntity<>(productResponseDTOCreated, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}

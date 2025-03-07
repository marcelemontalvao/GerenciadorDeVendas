package com.vrsoftware.gerenciadorDeVendas.controller;

import com.vrsoftware.gerenciadorDeVendas.dto.ProductDTO;
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
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductEntity productEntity = productMapper.toEntity(productDTO);
        ProductEntity productCreated = productService.createProduct(productEntity);
        ProductDTO productDTOCreated = productMapper.toDTO(productCreated);
        return new ResponseEntity<>(productDTOCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listProduct() {
        List<ProductEntity> products = productService.listProduct();
        List<ProductDTO> productDTOCreated = productMapper.toDTOList(products);
        return new ResponseEntity<>(productDTOCreated, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductEntity productEntity = productMapper.toEntity(productDTO);
        ProductEntity productUpdate = productService.updateProduct(productEntity);
        ProductDTO productDTOCreated = productMapper.toDTO(productUpdate);
        return new ResponseEntity<>(productDTOCreated, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}

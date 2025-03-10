package com.vrsoftware.gerenciadorDeVendas.service;

import com.vrsoftware.gerenciadorDeVendas.entity.ProductEntity;
import com.vrsoftware.gerenciadorDeVendas.exception.ProductNotFoundException;
import com.vrsoftware.gerenciadorDeVendas.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    public List<ProductEntity> listProduct() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id").
                and(Sort.by(Sort.Direction.ASC, "descricao"));

        return productRepository.findAll(sort);
    }

    public ProductEntity updateProduct(ProductEntity product) throws ProductNotFoundException {
        if(!productRepository.existsById(product.getId())) {
            throw new ProductNotFoundException();
        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) throws ProductNotFoundException {
        if(!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }
}

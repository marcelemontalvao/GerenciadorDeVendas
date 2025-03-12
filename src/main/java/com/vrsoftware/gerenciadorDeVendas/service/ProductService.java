package com.vrsoftware.gerenciadorDeVendas.service;

import com.vrsoftware.gerenciadorDeVendas.entity.ClientEntity;
import com.vrsoftware.gerenciadorDeVendas.entity.ProductEntity;
import com.vrsoftware.gerenciadorDeVendas.exception.ClientNotFoundException;
import com.vrsoftware.gerenciadorDeVendas.exception.ProductNotFoundException;
import com.vrsoftware.gerenciadorDeVendas.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
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

    public ProductEntity updateProduct(Long id, ProductEntity updatedProduct) throws ProductNotFoundException {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        BeanUtils.copyProperties(updatedProduct, productEntity, "id");

        return productRepository.save(productEntity);
    }

    public void deleteProduct(Long id) throws ProductNotFoundException {
        if(!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }
}

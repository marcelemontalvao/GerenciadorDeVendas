package com.vrsoftware.gerenciadorDeVendas.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {

    private Long id;
    private String descricao;
    private BigDecimal preco;
}

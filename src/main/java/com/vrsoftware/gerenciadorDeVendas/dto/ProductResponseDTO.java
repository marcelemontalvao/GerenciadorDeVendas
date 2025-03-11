package com.vrsoftware.gerenciadorDeVendas.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {

    private Long id;
    private String descricao;
    private BigDecimal preco;
}

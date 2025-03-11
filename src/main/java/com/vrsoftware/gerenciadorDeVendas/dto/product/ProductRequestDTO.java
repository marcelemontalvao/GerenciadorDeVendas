package com.vrsoftware.gerenciadorDeVendas.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;

    @NotNull(message = "O preço é obrigatório.")
    @PositiveOrZero(message = "O preço deve ser zero ou positivo.")
    private BigDecimal preco;
}

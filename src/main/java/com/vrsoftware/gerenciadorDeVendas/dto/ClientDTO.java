package com.vrsoftware.gerenciadorDeVendas.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientDTO {

    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório.")
    private String nome;

    @NotNull(message = "O limite de compra é obrigatório.")
    @PositiveOrZero(message = "O limite de compra deve ser zero ou positivo.")
    @JsonProperty("limite_compra")
    private BigDecimal limiteCompra;

    @NotNull(message = "O dia de fechamento é obrigatório.")
    @Min(value = 1, message = "O dia de fechamento não pode ser menor que 1.")
    @Max(value = 31, message = "O dia de fechamento não pode ser maior que 31.")
    @JsonProperty("dia_fechamento")
    private Integer diaFechamento;

}

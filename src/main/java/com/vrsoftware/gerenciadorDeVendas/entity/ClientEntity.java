package com.vrsoftware.gerenciadorDeVendas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "clientes")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório")
    private String nome;

    @Column(name = "limite_compra")
    @NotNull(message = "O limite de compra é obrigatório.")
    private BigDecimal limiteCompra;

    @Column(name = "dia_fechamento")
    @NotNull(message = "O dia de fechamento é obrigatório.")
    @Min(value = 1, message = "O dia não pode ser menor que 1")
    @Max(value = 31, message = "O dia não pode ser maior de 31")
    @PositiveOrZero(message = "O limite de compra deve ser zero ou positivo.")
    private Integer diaFechamento;

}

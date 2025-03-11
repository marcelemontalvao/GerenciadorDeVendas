package com.vrsoftware.gerenciadorDeVendas.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientResponseDTO {

    private Long id;
    private String nome;
    private BigDecimal limiteCompra;
    private Integer diaFechamento;

}

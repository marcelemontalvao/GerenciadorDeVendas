package com.vrsoftware.gerenciadorDeVendas.dto.saleItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesItemDTO {
    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal precoUnitario;
}

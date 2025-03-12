package com.vrsoftware.gerenciadorDeVendas.dto.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vrsoftware.gerenciadorDeVendas.dto.saleItem.SalesItemDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class SalesResponseDTO {
    private Long id;
    private Long clientId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVenda;
    private List<SalesItemDTO> itens;
    private BigDecimal total;
}

package com.vrsoftware.gerenciadorDeVendas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class SalesRequestDTO {

    private Long clientId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVenda;
    private List<SalesItemDTO> itens;

}

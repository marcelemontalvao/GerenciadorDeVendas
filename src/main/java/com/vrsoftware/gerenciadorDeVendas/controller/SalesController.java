package com.vrsoftware.gerenciadorDeVendas.controller;

import com.vrsoftware.gerenciadorDeVendas.dto.sale.SalesRequestDTO;
import com.vrsoftware.gerenciadorDeVendas.dto.sale.SalesResponseDTO;
import com.vrsoftware.gerenciadorDeVendas.entity.ClientEntity;
import com.vrsoftware.gerenciadorDeVendas.entity.ProductEntity;
import com.vrsoftware.gerenciadorDeVendas.entity.SalesEntity;
import com.vrsoftware.gerenciadorDeVendas.mapper.SalesMapper;
import com.vrsoftware.gerenciadorDeVendas.service.SalesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vendas")
public class SalesController {

    private SalesService salesService;
    private final SalesMapper salesMapper;

    public SalesController(SalesService salesService, SalesMapper salesMapper) {
        this.salesService = salesService;
        this.salesMapper = salesMapper;
    }

    @PostMapping
    public ResponseEntity<SalesResponseDTO> createSales(@RequestBody SalesRequestDTO salesRequestDTO) {
        SalesEntity createdSales = salesService.createSales(salesRequestDTO);
        SalesResponseDTO responseDTO = salesService.convertToDTO(createdSales);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{salesId}")
    public ResponseEntity<SalesResponseDTO> getSales(@PathVariable Long salesId) {
        SalesEntity salesEntity = salesService.findSalesById(salesId);
        //SalesResponseDTO responseDTO = salesMapper.toDTO(salesEntity);
        SalesResponseDTO responseDTO = salesService.convertToDTO(salesEntity);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAllSales() {
        List<SalesEntity> sales = salesService.findAll();
        List<SalesResponseDTO> salesResponseDTOS = sales.stream().map((sell) -> salesService.convertToDTO(sell)).toList();
        return new ResponseEntity<>(salesResponseDTOS, HttpStatus.OK);
    }

    @PutMapping("/{salesId}")
    public ResponseEntity<SalesResponseDTO> updateSales(@PathVariable Long salesId, @RequestBody SalesRequestDTO salesRequestDTO) {
        SalesEntity sales = salesService.findSalesById(salesId);
        SalesEntity updatedSales = salesService.updateSales(sales, salesRequestDTO);
        //SalesResponseDTO responseDTO = salesMapper.toDTO(updatedSales);
        SalesResponseDTO responseDTO = salesService.convertToDTO(updatedSales);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{salesId}/itens/{productId}")
    public ResponseEntity<Void> updateItemQuantity(@PathVariable Long salesId, @PathVariable Long productId, @RequestParam int newQuantity) {
        SalesEntity sales = salesService.findSalesById(salesId);
        salesService.updateItemQuantity(sales, productId, newQuantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{salesId}")
    public ResponseEntity<Void> deleteSales(@PathVariable Long salesId) {
        salesService.deleteSales(salesId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{salesId}/itens/{productId}")
    public ResponseEntity<Void> removeProductFromSale(
            @PathVariable Long salesId,
            @PathVariable Long productId) {
        salesService.removeProductFromSale(salesId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<SalesResponseDTO>> filterSales(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) Long produtoId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
        LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;

        List<SalesEntity> sales = salesService.findByFilters(clientId, produtoId, start, end);
        List<SalesResponseDTO> salesResponseDTOS = sales.stream().map((sell) -> salesService.convertToDTO(sell)).toList();
        return new ResponseEntity<>(salesResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/agrupar/cliente")
    public ResponseEntity<Map<ClientEntity, BigDecimal>> getSalesGroupedByClient(@RequestParam LocalDate startDate,
                                                                                 @RequestParam LocalDate endDate) {
        Map<ClientEntity, BigDecimal> salesGroupedByClient = salesService.getSalesGroupedByClient(startDate, endDate);
        return new ResponseEntity<>(salesGroupedByClient, HttpStatus.OK);
    }

    @GetMapping("/agrupar/produto")
    public ResponseEntity<Map<ProductEntity, BigDecimal>> getSalesGroupedByProduct(@RequestParam LocalDate startDate,
                                                                                   @RequestParam LocalDate endDate) {
        Map<ProductEntity, BigDecimal> salesGroupedByProduct = salesService.getSalesGroupedByProduct(startDate, endDate);
        return new ResponseEntity<>(salesGroupedByProduct, HttpStatus.OK);
    }
}

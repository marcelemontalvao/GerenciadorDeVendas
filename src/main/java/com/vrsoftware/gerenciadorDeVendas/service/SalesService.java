package com.vrsoftware.gerenciadorDeVendas.service;

import com.vrsoftware.gerenciadorDeVendas.dto.saleItem.SalesItemDTO;
import com.vrsoftware.gerenciadorDeVendas.dto.sale.SalesRequestDTO;
import com.vrsoftware.gerenciadorDeVendas.dto.sale.SalesResponseDTO;
import com.vrsoftware.gerenciadorDeVendas.entity.ClientEntity;
import com.vrsoftware.gerenciadorDeVendas.entity.ProductEntity;
import com.vrsoftware.gerenciadorDeVendas.entity.SalesEntity;
import com.vrsoftware.gerenciadorDeVendas.entity.SalesItemEntity;
import com.vrsoftware.gerenciadorDeVendas.exception.ProductNotFoundException;
import com.vrsoftware.gerenciadorDeVendas.repository.ClientRepository;
import com.vrsoftware.gerenciadorDeVendas.repository.ProductRepository;
import com.vrsoftware.gerenciadorDeVendas.repository.SalesRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class SalesService {

    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public SalesService(SalesRepository salesRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.salesRepository = salesRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    public SalesEntity createSales(SalesRequestDTO sell) {
        Map<Long, SalesItemEntity> itemsMap = convertSalesItems(sell.getItens());
        ClientEntity client = getClient(sell.getClientId());
        SalesEntity salesEntity = buildSalesEntity(sell, client, itemsMap);
        checkCreditLimit(client, salesEntity);
        return salesRepository.save(salesEntity);
    }

    private Map<Long, SalesItemEntity> convertSalesItems(List<SalesItemDTO> itensDTO) {
        Map<Long, SalesItemEntity> onlyItems = new HashMap<>();
        for (SalesItemDTO itemDTO : itensDTO) {
            SalesItemEntity sellItem = createSalesItemEntity(itemDTO);
            Long productId = itemDTO.getProdutoId();
            if (onlyItems.containsKey(productId)) {
                throw new IllegalArgumentException("Produto já adicionado. Use a função de alterar quantidade.");
            } else {
                onlyItems.put(productId, sellItem);
            }
        }
        return onlyItems;
    }

    private SalesItemEntity createSalesItemEntity(SalesItemDTO itemDTO) {
        if (itemDTO.getProdutoId() == null) {
            throw new ProductNotFoundException("Não tem id do produto na requisição");
        }
        ProductEntity product = productRepository.findById(itemDTO.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado para o id " + itemDTO.getProdutoId()));

        SalesItemEntity sellItem = new SalesItemEntity();
        sellItem.setProduto(product);
        sellItem.setQuantidade(itemDTO.getQuantidade());
        sellItem.setPrecoUnitario(itemDTO.getPrecoUnitario());
        return sellItem;
    }

    private ClientEntity getClient(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para o id " + clientId));
    }

    private SalesEntity buildSalesEntity(SalesRequestDTO sell, ClientEntity client, Map<Long, SalesItemEntity> itemsMap) {
        SalesEntity salesEntity = new SalesEntity();
        salesEntity.setCliente(client);
        salesEntity.setDataVenda(sell.getDataVenda() != null ? sell.getDataVenda() : LocalDate.now());

        List<SalesItemEntity> itens = new ArrayList<>(itemsMap.values());
        for (SalesItemEntity itemEntity : itens) {
            itemEntity.setVenda(salesEntity);
        }
        salesEntity.setItens(itens);
        return salesEntity;
    }

    private void checkCreditLimit(ClientEntity client, SalesEntity salesEntity) {
        LocalDate nextCloseDate = calculateNextClosingDate(client.getDiaFechamento(), LocalDate.now());
        LocalDate currentCloseDate = nextCloseDate.minusMonths(1);
        BigDecimal totalPeriod = calculateTotalSellPeriod(client, currentCloseDate);
        BigDecimal newTotal = totalPeriod.add(salesEntity.getTotal());

        if (newTotal.compareTo(client.getLimiteCompra()) > 0) {
            BigDecimal available = client.getLimiteCompra().subtract(totalPeriod);
            LocalDate nextClosingDate = calculateNextClosingDate(client.getDiaFechamento(), salesEntity.getDataVenda());
            throw new RuntimeException("Limite de crédito excedido. Valor disponível: " + available + ", próximo fechamento: " + nextClosingDate);
        }
    }

    private LocalDate calculateNextClosingDate(int closingDay, LocalDate saleDate) {
        YearMonth currentMonth = YearMonth.of(saleDate.getYear(), saleDate.getMonth());
        int effectiveClosingDay = Math.min(closingDay, currentMonth.lengthOfMonth());
        LocalDate potentialClosingDate = LocalDate.of(saleDate.getYear(), saleDate.getMonth(), effectiveClosingDay);

        if (saleDate.isBefore(potentialClosingDate)) {
            return potentialClosingDate;
        } else {
            YearMonth nextMonth = currentMonth.plusMonths(1);
            effectiveClosingDay = Math.min(closingDay, nextMonth.lengthOfMonth());
            return LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), effectiveClosingDay);
        }
    }

    public SalesResponseDTO convertToDTO(SalesEntity salesEntity) {
        SalesResponseDTO dto = new SalesResponseDTO();
        dto.setClientId(salesEntity.getCliente().getId());
        dto.setDataVenda(salesEntity.getDataVenda());
        dto.setTotal(salesEntity.getTotal());

        List<SalesItemDTO> itensDTO = salesEntity.getItens().stream().map(item -> {
            SalesItemDTO itemDTO = new SalesItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProdutoId(item.getProduto().getId());
            itemDTO.setQuantidade(item.getQuantidade());
            itemDTO.setPrecoUnitario(item.getPrecoUnitario());
            return itemDTO;
        }).toList();

        dto.setItens(itensDTO);
        return dto;
    }

    public List<SalesEntity> findAll() {
        return salesRepository.findAll();
    }

    public SalesEntity findSalesById(Long salesId) {
        return salesRepository.findById(salesId)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }

    public void updateItemQuantity(SalesEntity sales, Long productId, int newQuantity) {
        for (SalesItemEntity item : sales.getItens()) {
            if (item.getProduto().getId().equals(productId)) {
                item.setQuantidade(newQuantity);
                break;
            }
        }
        salesRepository.save(sales);
    }

    public void deleteSales(Long salesId) {
        SalesEntity sell = salesRepository.findById(salesId)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
        salesRepository.delete(sell);
    }

    @Transactional
    public void removeProductFromSale(Long salesId, Long productId) {
        SalesEntity sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));

        SalesItemEntity itemToRemove = null;
        for (SalesItemEntity item : sales.getItens()) {
            if (item.getProduto().getId().equals(productId)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove != null) {
            sales.getItens().remove(itemToRemove);

            salesRepository.save(sales);
        } else {
            throw new EntityNotFoundException("Produto não encontrado na venda");
        }
    }

    public SalesEntity updateSales(SalesEntity sales, SalesRequestDTO updatedSell) {
        ClientEntity clientEntity = getClient(updatedSell.getClientId());
        sales.setCliente(clientEntity);
        sales.setDataVenda(updatedSell.getDataVenda());

        List<SalesItemEntity> existingItems = sales.getItens();

        Iterator<SalesItemEntity> iterator = existingItems.iterator();
        while (iterator.hasNext()) {
            SalesItemEntity existingItem = iterator.next();
            boolean existsInUpdated = updatedSell.getItens().stream()
                    .anyMatch(item -> item.getId() != null && item.getId().equals(existingItem.getId()));
            if (!existsInUpdated) {
                existingItem.setVenda(null);
                iterator.remove();
            }
        }

        for (SalesItemDTO updatedItem : updatedSell.getItens()) {
            if (updatedItem.getId() == null) {
                SalesItemEntity newItem = new SalesItemEntity();
                ProductEntity product = productRepository.findById(updatedItem.getProdutoId())
                        .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado para o id " + updatedItem.getProdutoId()));
                newItem.setProduto(product);
                newItem.setQuantidade(updatedItem.getQuantidade());
                newItem.setPrecoUnitario(updatedItem.getPrecoUnitario());
                newItem.setVenda(sales);
                existingItems.add(newItem);
            } else {
                Optional<SalesItemEntity> existingItemOpt = existingItems.stream()
                        .filter(item -> item.getId().equals(updatedItem.getId()))
                        .findFirst();
                if (existingItemOpt.isPresent()) {
                    SalesItemEntity existingItem = existingItemOpt.get();
                    existingItem.setQuantidade(updatedItem.getQuantidade());
                    existingItem.setPrecoUnitario(updatedItem.getPrecoUnitario());
                } else {
                    throw new IllegalArgumentException("Sales item com id " + updatedItem.getId() + " não encontrado nesta venda.");
                }
            }
        }
        return salesRepository.save(sales);
    }

    private BigDecimal calculateTotalSellPeriod(ClientEntity client, LocalDate dataReferencia) {
        List<SalesEntity> sells = salesRepository.findByClienteIdAndDataVendaAfter(client.getId(), dataReferencia);

        BigDecimal total = BigDecimal.ZERO;
        for (SalesEntity sell : sells) {
            total = total.add(sell.getTotal());
        }

        return total;
    }

    public List<SalesEntity> findByFilters(Long clientId, Long produtoId, LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null && inicio.isAfter(fim)) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim.");
        }

        if (clientId != null) {
            return salesRepository.findByClienteId(clientId);
        } else if (produtoId != null) {
            return salesRepository.findByProdutoId(produtoId);
        } else if(inicio != null && fim != null) {
            return salesRepository.findByDataVendaBetween(inicio, fim);
        }
        return salesRepository.findAll();
    }

    public Map<ClientEntity, BigDecimal> groupByClientAndTotal(List<SalesEntity> sells) {
        Map<ClientEntity, BigDecimal> groupedByClient = new HashMap<>();
        for (SalesEntity sell : sells) {
            ClientEntity client = sell.getCliente();
            BigDecimal total = groupedByClient.getOrDefault(client, BigDecimal.ZERO);
            groupedByClient.put(client, total.add(sell.getTotal()));
        }
        return groupedByClient;
    }

    public Map<ProductEntity, BigDecimal> groupByProductAndTotal(List<SalesEntity> sells) {
        Map<ProductEntity, BigDecimal> groupedByProduct = new HashMap<>();
        for (SalesEntity sell : sells) {
            for (SalesItemEntity item : sell.getItens()) {
                ProductEntity product = productRepository.findById(item.getProduto().getId())
                        .orElseThrow(() -> new ProductNotFoundException("Product not found"));
                BigDecimal total = groupedByProduct.getOrDefault(product, BigDecimal.ZERO);
                groupedByProduct.put(product, total.add(item.getPrecoUnitario()));
            }
        }
        return groupedByProduct;
    }

    public Map<ClientEntity, BigDecimal> getSalesGroupedByClient(LocalDate startDate, LocalDate endDate) {
        List<SalesEntity> sales = findByFilters(null, null, startDate, endDate);
        return groupByClientAndTotal(sales);
    }

    public Map<ProductEntity, BigDecimal> getSalesGroupedByProduct(LocalDate startDate, LocalDate endDate) {
        List<SalesEntity> sales = findByFilters(null, null, startDate, endDate);
        return groupByProductAndTotal(sales);
    }
}

package com.vrsoftware.gerenciadorDeVendas.repository;
import com.vrsoftware.gerenciadorDeVendas.entity.SalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<SalesEntity, Long>{

    List<SalesEntity> findByClienteId(Long clientId);

    @Query("SELECT s FROM SalesEntity s JOIN s.itens i WHERE i.produto.id = :produtoId")
    List<SalesEntity> findByProdutoId(@Param("produtoId") Long produtoId);

    List<SalesEntity> findByClienteIdAndDataVendaBetween(Long clientId, LocalDate inicio, LocalDate fim);

    @Query("SELECT s FROM SalesEntity s WHERE s.dataVenda BETWEEN :startDate AND :endDate")
    List<SalesEntity> findByDataVendaBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<SalesEntity> findByClienteIdAndDataVendaAfter(Long clienteId, LocalDate dataReferencia);
}

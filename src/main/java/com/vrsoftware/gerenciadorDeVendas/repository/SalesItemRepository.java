package com.vrsoftware.gerenciadorDeVendas.repository;

import com.vrsoftware.gerenciadorDeVendas.entity.SalesItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesItemRepository extends JpaRepository<SalesItemEntity, Long> {
}

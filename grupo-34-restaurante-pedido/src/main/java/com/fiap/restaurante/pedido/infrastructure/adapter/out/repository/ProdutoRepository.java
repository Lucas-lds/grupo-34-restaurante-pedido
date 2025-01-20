package com.fiap.restaurante.pedido.infrastructure.adapter.out.repository;

import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    List<ProdutoEntity> findProductByCategoria(String categoria);
}

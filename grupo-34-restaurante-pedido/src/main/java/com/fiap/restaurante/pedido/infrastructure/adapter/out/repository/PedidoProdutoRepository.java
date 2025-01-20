package com.fiap.restaurante.pedido.infrastructure.adapter.out.repository;

import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoProdutoRepository extends JpaRepository<PedidoProdutoEntity, Long>{

    List<PedidoProdutoEntity> findByPedidoId(Long id);

}

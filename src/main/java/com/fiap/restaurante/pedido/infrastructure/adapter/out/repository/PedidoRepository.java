package com.fiap.restaurante.pedido.infrastructure.adapter.out.repository;

import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository {

    void save(PedidoEntity pedidoEntity);

    Optional<PedidoEntity> findById(UUID id);

    List<PedidoEntity> findAll();

    void delete(PedidoEntity pedidoEntity);

    List<PedidoEntity> findAllOrderedByStatus();
}

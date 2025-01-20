package com.fiap.restaurante.pedido.infrastructure.adapter.out.repository;

import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    @Query("SELECT p FROM PedidoEntity p WHERE p.status <> 'FINISHED' " +
            "ORDER BY " +
            "CASE p.status " +
            "WHEN 'DONE' THEN 1 " +
            "WHEN 'PREPARING' THEN 2 " +
            "WHEN 'RECEIVED' THEN 3 " +
            "END, p.createdAt ASC")
    List<PedidoEntity> findAllOrderedByStatus();

}

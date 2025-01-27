package com.fiap.restaurante.pedido.application.port.out.service;

import com.fiap.restaurante.pedido.core.domain.Pedido;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.UUID;

public interface PedidoServicePortOut {
    Pedido atualizarStatusPedido(Integer status, UUID id) throws BadRequestException;

    void checkoutPedido(Pedido pedido);

    Pedido listarPedidoPorId(UUID id);

    List<Pedido> listarPedidos();
}

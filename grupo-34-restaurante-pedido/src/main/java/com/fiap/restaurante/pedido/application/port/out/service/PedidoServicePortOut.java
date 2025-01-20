package com.fiap.restaurante.pedido.application.port.out.service;

import com.fiap.restaurante.pedido.core.domain.Pedido;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface PedidoServicePortOut {
    Pedido atualizarStatusPedido(Integer status, Long id) throws BadRequestException;

    Pedido checkoutPedido(Pedido pedido);

    Pedido listarPedidoPorId(Long id);

    List<Pedido> listarPedidos();
}

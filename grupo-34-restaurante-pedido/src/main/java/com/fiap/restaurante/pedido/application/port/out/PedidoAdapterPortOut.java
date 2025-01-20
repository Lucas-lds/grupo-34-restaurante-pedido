package com.fiap.restaurante.pedido.application.port.out;


import com.fiap.restaurante.pedido.core.domain.Pedido;
import com.fiap.restaurante.pedido.core.domain.enums.OrderStatus;

import java.util.List;

public interface PedidoAdapterPortOut {

    Pedido atualizarStatusPedido(OrderStatus status, Long id);

    Pedido checkoutPedido(Pedido pedido);

    Pedido listarPedidoPorId(Long id);

    List<Pedido> listarPedidos();
}

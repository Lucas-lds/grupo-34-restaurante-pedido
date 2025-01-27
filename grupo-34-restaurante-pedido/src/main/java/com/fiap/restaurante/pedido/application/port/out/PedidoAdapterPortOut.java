package com.fiap.restaurante.pedido.application.port.out;


import com.fiap.restaurante.pedido.core.domain.Pedido;
import com.fiap.restaurante.pedido.core.domain.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface PedidoAdapterPortOut {

    Pedido atualizarStatusPedido(OrderStatus status, UUID id);

    void checkoutPedido(Pedido pedido);

    Pedido listarPedidoPorId(UUID id);

    List<Pedido> listarPedidos();
}

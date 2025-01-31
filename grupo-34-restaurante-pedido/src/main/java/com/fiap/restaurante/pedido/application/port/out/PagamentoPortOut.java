package com.fiap.restaurante.pedido.application.port.out;

import com.fiap.restaurante.pedido.core.domain.Pedido;
import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;

public interface PagamentoPortOut {
    void enviarPagamento(PedidoEntity pedido);
}

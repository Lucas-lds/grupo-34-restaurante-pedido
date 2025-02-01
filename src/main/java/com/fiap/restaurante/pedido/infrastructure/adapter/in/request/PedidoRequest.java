package com.fiap.restaurante.pedido.infrastructure.adapter.in.request;

import com.fiap.restaurante.pedido.core.domain.Pedido;

import java.util.List;

public record PedidoRequest(Long idCliente, List<PedidoProdutoRequest> listaPedidoProdutos) {

    public Pedido toDomain() {
        return new Pedido(idCliente, listaPedidoProdutos.stream().map(PedidoProdutoRequest::toDomain).toList());
    }
}

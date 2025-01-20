package com.fiap.restaurante.pedido.infrastructure.adapter.in.request;

import com.fiap.restaurante.pedido.core.domain.PedidoProduto;

public record PedidoProdutoRequest(ProdutoPedidoRequest produto, Integer quantidade) {

    public PedidoProduto toDomain() {
        return new PedidoProduto(produto.toDomain(), quantidade);
    }
}

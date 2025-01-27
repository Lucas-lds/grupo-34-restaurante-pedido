package com.fiap.restaurante.pedido.infrastructure.adapter.in.request;

import com.fiap.restaurante.pedido.core.domain.ProdutoQuantidade;

public record PedidoProdutoRequest(ProdutoPedidoRequest produto, Integer quantidade) {

    public ProdutoQuantidade toDomain() {
        return new ProdutoQuantidade(produto.toDomain(), quantidade);
    }
}

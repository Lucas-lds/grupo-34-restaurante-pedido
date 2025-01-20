package com.fiap.restaurante.pedido.infrastructure.adapter.in.request;

import com.fiap.restaurante.pedido.core.domain.Produto;

public record ProdutoPedidoRequest(Long id) {

    public Produto toDomain() {
        return new Produto(id);
    }
}

package com.fiap.restaurante.pedido.core.domain;

public class PedidoProduto {
    private Produto produto;
    private Integer quantidade;

    public PedidoProduto(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}

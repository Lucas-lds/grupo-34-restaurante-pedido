package com.fiap.restaurante.pedido.core.domain;

import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.ProdutoQuantidadeEntity;

public class ProdutoQuantidade {
    private Produto produto;
    private Integer quantidade;

    public ProdutoQuantidade(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ProdutoQuantidadeEntity toEntity() {
        return new ProdutoQuantidadeEntity(produto.toEntity(), quantidade);
    }
}

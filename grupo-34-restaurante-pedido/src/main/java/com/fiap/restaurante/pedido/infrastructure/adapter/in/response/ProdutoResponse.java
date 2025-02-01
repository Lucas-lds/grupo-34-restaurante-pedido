package com.fiap.restaurante.pedido.infrastructure.adapter.in.response;

import com.fiap.restaurante.pedido.core.domain.Produto;

public record ProdutoResponse(Long idProduto, String nome, String categoria, Double preco, String descricao, String imagemUrl) {

    public static ProdutoResponse fromDomain(Produto produto) {
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getCategoria(), produto.getPreco(), produto.getDescricao(), produto.getImagemUrl());
    }

    public Produto toDomain() {
        return new Produto(idProduto, nome, categoria, preco, descricao, imagemUrl);
    }
}

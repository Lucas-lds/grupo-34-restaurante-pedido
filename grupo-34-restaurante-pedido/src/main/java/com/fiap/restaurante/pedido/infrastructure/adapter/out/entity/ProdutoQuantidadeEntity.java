package com.fiap.restaurante.pedido.infrastructure.adapter.out.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import com.fiap.restaurante.pedido.core.domain.ProdutoQuantidade;

@DynamoDbBean
public class ProdutoQuantidadeEntity {

    private ProdutoEntity produto;
    private int quantidade;

    public ProdutoQuantidadeEntity() {
    }

    @DynamoDbAttribute("produto")
    public ProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    @DynamoDbAttribute("quantidade")
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public ProdutoQuantidade toDomain() {
        return new ProdutoQuantidade(produto.toDomain(), quantidade);
    }

    public ProdutoQuantidadeEntity(ProdutoEntity produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public static ProdutoQuantidadeEntity fromDomain(ProdutoQuantidade produtoQuantidade) {
        ProdutoQuantidadeEntity produtoQuantidadeEntity = new ProdutoQuantidadeEntity();
        produtoQuantidadeEntity.setProduto(ProdutoEntity.fromDomain(produtoQuantidade.getProduto()));
        produtoQuantidadeEntity.setQuantidade(produtoQuantidade.getQuantidade());
        return produtoQuantidadeEntity;
    }
}

package com.fiap.restaurante.pedido.infrastructure.adapter.out.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import com.fiap.restaurante.pedido.core.domain.Produto;

@DynamoDbBean
public class ProdutoEntity {

    private Long id;

    public ProdutoEntity() {
    }

    public ProdutoEntity(Long id) {
        this.id = id;
    }

    @DynamoDbAttribute("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto toDomain() {
        return new Produto(id);
    }

    public static ProdutoEntity fromDomain(Produto produto) {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(produto.getId());
        return produtoEntity;
    }
}

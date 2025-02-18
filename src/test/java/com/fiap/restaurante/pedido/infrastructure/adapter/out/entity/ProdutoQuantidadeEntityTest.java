package com.fiap.restaurante.pedido.infrastructure.adapter.out.entity;

import com.fiap.restaurante.pedido.core.domain.Produto;
import com.fiap.restaurante.pedido.core.domain.ProdutoQuantidade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoQuantidadeEntityTest {

    @Test
    void testGettersAndSetters() {
        ProdutoEntity produto = new ProdutoEntity(1L);
        ProdutoQuantidadeEntity entity = new ProdutoQuantidadeEntity();

        entity.setProduto(produto);
        entity.setQuantidade(2);

        assertEquals(produto, entity.getProduto());
        assertEquals(2, entity.getQuantidade());
    }

    @Test
    void testToDomain() {
        ProdutoEntity produtoEntity = new ProdutoEntity(1L);
        ProdutoQuantidadeEntity entity = new ProdutoQuantidadeEntity(produtoEntity, 3);

        ProdutoQuantidade domain = entity.toDomain();

        assertEquals(produtoEntity.toDomain().getId(), domain.getProduto().getId());


        assertEquals(3, domain.getQuantidade());
    }

    @Test
    void testFromDomain() {
        Produto produto = new Produto(1L, "Hambúrguer", "LANCHE", 25.0, "Hambúrguer artesanal", "url1");
        ProdutoQuantidade domain = new ProdutoQuantidade(produto, 4);

        ProdutoQuantidadeEntity entity = ProdutoQuantidadeEntity.fromDomain(domain);

        assertEquals(produto.getId(), entity.getProduto().getId());


        assertEquals(4, entity.getQuantidade());
    }
}

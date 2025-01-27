package com.fiap.restaurante.pedido.core.domain;

import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.ProdutoEntity;

public class Produto {
    private Long id;
    private String nome;
    private String categoria;
    private double preco;
    private String descricao;
    private String imagemUrl;

    public Produto(Long id, String nome, String categoria, double preco, String descricao, String imagemUrl) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
    }

    public Produto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public ProdutoEntity toEntity() {
        return new ProdutoEntity(id);
    }
}
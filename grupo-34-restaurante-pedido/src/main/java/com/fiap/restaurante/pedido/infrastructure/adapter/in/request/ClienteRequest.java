package com.fiap.restaurante.pedido.infrastructure.adapter.in.request;


import com.fiap.restaurante.pedido.core.domain.Cliente;

public record ClienteRequest(String cpf, String nome, String email, String telefone) {

    public Cliente toDomain() {
        return new Cliente(null, cpf, nome, email, telefone);
    }
}
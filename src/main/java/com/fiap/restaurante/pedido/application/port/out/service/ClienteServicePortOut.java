package com.fiap.restaurante.pedido.application.port.out.service;


import com.fiap.restaurante.pedido.core.domain.Cliente;

public interface ClienteServicePortOut {
    Cliente cadastrar(Cliente cliente);

    Cliente buscar(String cpf);

    void autenticarCliente(String cpf);
}

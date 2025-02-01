package com.fiap.restaurante.pedido.application.port.out.usecase;


import com.fiap.restaurante.pedido.core.domain.Cliente;

public interface ClienteUseCasePortOut {

    Cliente buscarCliente(String cpf);

    Cliente cadastrarCliente(Cliente cliente);

    void validarAutenticacaoCliente(String cpf);
}

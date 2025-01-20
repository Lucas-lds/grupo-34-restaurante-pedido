package com.fiap.restaurante.pedido.application.port.out;

import com.fiap.restaurante.pedido.core.domain.Cliente;

public interface ClienteAdapterPortOut {
    Cliente buscar(String cpf);

    Cliente cadastrar(Cliente cliente);
}

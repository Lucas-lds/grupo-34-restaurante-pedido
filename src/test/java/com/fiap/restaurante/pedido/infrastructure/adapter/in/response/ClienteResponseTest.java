package com.fiap.restaurante.pedido.infrastructure.adapter.in.response;

import com.fiap.restaurante.pedido.core.domain.Cliente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteResponseTest {

    @Test
    void shouldCreateClienteResponseWithCorrectValues() {
        Long idCliente = 1L;
        String cpf = "12345678901";
        String nome = "João Silva";
        String email = "joao@example.com";
        String telefone = "11999999999";

        ClienteResponse response = new ClienteResponse(idCliente, cpf, nome, email, telefone);

        assertEquals(idCliente, response.idCliente());
        assertEquals(cpf, response.cpf());
        assertEquals(nome, response.nome());
        assertEquals(email, response.email());
        assertEquals(telefone, response.telefone());
    }

    @Test
    void fromDomainShouldMapClienteToClienteResponse() {
        Cliente cliente = new Cliente(1L, "12345678901", "João Silva", "joao@example.com", "11999999999");


        ClienteResponse response = ClienteResponse.fromDomain(cliente);

        assertEquals(cliente.getIdCliente(), response.idCliente());
        assertEquals(cliente.getCpf(), response.cpf());
        assertEquals(cliente.getNome(), response.nome());
        assertEquals(cliente.getEmail(), response.email());
        assertEquals(cliente.getTelefone(), response.telefone());
    }
}

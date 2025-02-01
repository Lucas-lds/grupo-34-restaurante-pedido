package com.fiap.restaurante.pedido.core.usecase;


import com.fiap.restaurante.pedido.application.port.out.service.ClienteServicePortOut;
import com.fiap.restaurante.pedido.application.port.out.usecase.ClienteUseCasePortOut;
import com.fiap.restaurante.pedido.core.domain.Cliente;

public class ClienteUseCase implements ClienteUseCasePortOut {

    private final ClienteServicePortOut clienteService;

    public ClienteUseCase(ClienteServicePortOut clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public Cliente buscarCliente(String cpf) {
        return clienteService.buscar(cpf);
    }

    @Override
    public Cliente cadastrarCliente(Cliente cliente) {
        return clienteService.cadastrar(cliente);
    }

    @Override
    public void validarAutenticacaoCliente(String cpf) {
        clienteService.autenticarCliente(cpf);
    }


}

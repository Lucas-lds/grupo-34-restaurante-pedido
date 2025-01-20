package com.fiap.restaurante.pedido.infrastructure.exception;

public class ClientePossuiCadastroCognito extends RuntimeException {
    public ClientePossuiCadastroCognito(String message) {
        super(message);
    }
}

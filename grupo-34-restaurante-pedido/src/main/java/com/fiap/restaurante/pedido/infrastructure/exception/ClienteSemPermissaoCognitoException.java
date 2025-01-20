package com.fiap.restaurante.pedido.infrastructure.exception;

public class ClienteSemPermissaoCognitoException extends RuntimeException {
    public ClienteSemPermissaoCognitoException(String message) {
        super(message);
    }
}

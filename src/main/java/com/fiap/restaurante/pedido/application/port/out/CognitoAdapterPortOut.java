package com.fiap.restaurante.pedido.application.port.out;


import com.fiap.restaurante.pedido.core.dto.ClienteCognitoRequestDTO;

public interface CognitoAdapterPortOut {

    void cadastrarClienteCognito(ClienteCognitoRequestDTO cliente);
}

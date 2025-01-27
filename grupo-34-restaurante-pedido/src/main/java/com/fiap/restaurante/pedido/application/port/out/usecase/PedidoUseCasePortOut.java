package com.fiap.restaurante.pedido.application.port.out.usecase;

import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.response.PedidoResponse;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.UUID;

public interface PedidoUseCasePortOut {

    PedidoResponse atualizarStatusPedido(Integer status, UUID id) throws BadRequestException;

    void checkoutPedido(PedidoRequest pedido);

    PedidoResponse listarPedidoPorId(UUID id);

    List<PedidoResponse> listarPedidos();
}

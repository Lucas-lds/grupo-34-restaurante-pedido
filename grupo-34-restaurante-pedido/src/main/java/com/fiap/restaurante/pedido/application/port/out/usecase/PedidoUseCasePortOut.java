package com.fiap.restaurante.pedido.application.port.out.usecase;

import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.response.PedidoResponse;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface PedidoUseCasePortOut {

    PedidoResponse atualizarStatusPedido(Integer status, Long id) throws BadRequestException;

    PedidoResponse checkoutPedido(PedidoRequest pedido);

    PedidoResponse listarPedidoPorId(Long id);

    List<PedidoResponse> listarPedidos();
}

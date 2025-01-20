package com.fiap.restaurante.pedido.core.usecase;

import com.fiap.restaurante.pedido.application.port.out.service.PedidoServicePortOut;
import com.fiap.restaurante.pedido.application.port.out.usecase.PedidoUseCasePortOut;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.response.PedidoResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoUseCase implements PedidoUseCasePortOut {

    private final PedidoServicePortOut pedidoServicePortOut;

    public PedidoUseCase(PedidoServicePortOut pedidoServicePortOut) {
        this.pedidoServicePortOut = pedidoServicePortOut;
    }

    @Override
    public PedidoResponse atualizarStatusPedido(Integer status, Long id) throws BadRequestException {
        return PedidoResponse.fromDomain(pedidoServicePortOut.atualizarStatusPedido(status, id));
    }

    @Override
    public PedidoResponse checkoutPedido(PedidoRequest pedido) {
        return PedidoResponse.fromDomain(pedidoServicePortOut.checkoutPedido(pedido.toDomain()));
    }

    @Override
    public PedidoResponse listarPedidoPorId(Long id) {
        return PedidoResponse.fromDomain(pedidoServicePortOut.listarPedidoPorId(id));
    }

    @Override
    public List<PedidoResponse> listarPedidos() {
        return pedidoServicePortOut.listarPedidos().stream()
            .map(pedido -> PedidoResponse.fromDomain(pedido)).toList();
    }
    
}

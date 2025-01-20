package com.fiap.restaurante.pedido.application.service;

import com.fiap.restaurante.pedido.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.pedido.application.port.out.service.PedidoServicePortOut;
import com.fiap.restaurante.pedido.core.domain.Pedido;
import com.fiap.restaurante.pedido.core.domain.enums.OrderStatus;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService implements PedidoServicePortOut {

    private final PedidoAdapterPortOut pedidoAdapterPortOut;

    public PedidoService(PedidoAdapterPortOut pedidoAdapterPortOut) {
        this.pedidoAdapterPortOut = pedidoAdapterPortOut;
    }

    @Override
    public Pedido atualizarStatusPedido(Integer status, Long id) throws BadRequestException {
        if (status == 2)
            return pedidoAdapterPortOut.atualizarStatusPedido(OrderStatus.PREPARING, id);
        else if (status == 3)
            return pedidoAdapterPortOut.atualizarStatusPedido(OrderStatus.DONE, id);
        else if (status == 4)
            return pedidoAdapterPortOut.atualizarStatusPedido(OrderStatus.FINISHED, id);
        else if (status == 5)
            return pedidoAdapterPortOut.atualizarStatusPedido(OrderStatus.CANCELED, id);
        else
            throw new BadRequestException("Status não permitido.");
    }

    @Override
    public Pedido checkoutPedido(Pedido pedido) {
        pedido.setStatus(OrderStatus.RECEIVED);
        return pedidoAdapterPortOut.checkoutPedido(pedido);
    }

    @Override
    public Pedido listarPedidoPorId(Long id) {
        return pedidoAdapterPortOut.listarPedidoPorId(id);
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoAdapterPortOut.listarPedidos();
    }


}

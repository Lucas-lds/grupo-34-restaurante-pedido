package com.fiap.restaurante.pedido.core.domain;

import com.fiap.restaurante.pedido.core.domain.enums.OrderStatus;
import lombok.Setter;

import java.util.List;

@Setter
public class Pedido {
    private Long id;
    private OrderStatus status;
    private Long idCliente;
    private List<PedidoProduto> listaPedidoProdutos;

    public Pedido(Long idCliente, List<PedidoProduto> list) {
        this.idCliente = idCliente;
        this.listaPedidoProdutos = list;
    }

    public Pedido() {
    }

    public List<PedidoProduto> getListaPedidoProdutos() {
        return listaPedidoProdutos;
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

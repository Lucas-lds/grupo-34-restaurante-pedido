package com.fiap.restaurante.pedido.core.domain;

import com.fiap.restaurante.pedido.core.domain.enums.OrderStatus;
import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Pedido {
    private String id;
    private OrderStatus status;
    private Long idCliente;
    private List<ProdutoQuantidade> listaProdutoQuantidade;

    public Pedido(Long idCliente, List<ProdutoQuantidade> list) {
        this.idCliente = idCliente;
        this.listaProdutoQuantidade = list;
    }

    public Pedido(String id, OrderStatus status, Long idCliente, List<ProdutoQuantidade> listaProdutoQuantidade) {
        this.id = id;
        this.status = status;
        this.idCliente = idCliente;
        this.listaProdutoQuantidade = listaProdutoQuantidade;
    }

    public Pedido() {
    }

    public PedidoEntity toEntity() {
        return new PedidoEntity(id, LocalDateTime.now(), status.toString(), idCliente,
                listaProdutoQuantidade.stream().map(ProdutoQuantidade::toEntity).toList());
    }

}

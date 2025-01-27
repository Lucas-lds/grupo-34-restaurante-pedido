package com.fiap.restaurante.pedido.infrastructure.adapter.in.response;


import com.fiap.restaurante.pedido.core.domain.Pedido;

import java.util.ArrayList;
import java.util.List;

public record PedidoResponse(String id, String status, Long idCliente, List<PedidoProdutoResponse> listaPedidoProduto) {

    public static PedidoResponse fromDomain(Pedido pedido){
        List<PedidoProdutoResponse> pedidoProdutoList = new ArrayList<>();
        if(pedido.getListaProdutoQuantidade() != null)
            pedidoProdutoList = pedido.getListaProdutoQuantidade().stream()
                .map(pedidoProduto -> new PedidoProdutoResponse(ProdutoResponse.fromDomain(pedidoProduto.getProduto()),
                pedidoProduto.getQuantidade())).toList();

        return new PedidoResponse(pedido.getId(), pedido.getStatus().toString(), pedido.getIdCliente(), pedidoProdutoList);
         
    }

}
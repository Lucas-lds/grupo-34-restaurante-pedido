package com.fiap.restaurante.pedido.application.port.out;

import com.fiap.restaurante.pedido.core.domain.Produto;
import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;

public interface ProdutoPortOut {

    Produto consultarProduto(Long idProduto);
}

package com.fiap.restaurante.pedido.application.port.out;


import com.fiap.restaurante.pedido.core.domain.Produto;

import java.util.List;

public interface ProdutoAdapterPortOut {

    List<Produto> listarProdutos();

    List<Produto> listarProdutoPorCategoria(String categoria);

    Produto criarProduto(Produto produto);

    Produto atualizarProduto(Long id, Produto produto);

    void deletarPorId(Long id);
}

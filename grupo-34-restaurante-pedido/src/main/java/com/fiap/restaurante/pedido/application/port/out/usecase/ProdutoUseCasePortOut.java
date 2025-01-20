package com.fiap.restaurante.pedido.application.port.out.usecase;

import com.fiap.restaurante.pedido.core.domain.Produto;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface ProdutoUseCasePortOut {
    List<Produto> listarProdutos();

    List<Produto> listarProdutoPorCategoria(String categoria);

    Produto criarProduto(Produto produto);

    Produto atualizarProduto(Long id, Produto produto) throws BadRequestException;

    void deletarPorId(Long id);

}

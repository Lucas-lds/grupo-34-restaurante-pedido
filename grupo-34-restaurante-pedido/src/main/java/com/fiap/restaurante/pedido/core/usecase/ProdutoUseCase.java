package com.fiap.restaurante.pedido.core.usecase;


import com.fiap.restaurante.pedido.application.port.out.service.ProdutoServicePortOut;
import com.fiap.restaurante.pedido.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.restaurante.pedido.core.domain.Produto;
import org.apache.coyote.BadRequestException;

import java.util.List;

public class ProdutoUseCase implements ProdutoUseCasePortOut {
    
    private final ProdutoServicePortOut produtoService;

    public ProdutoUseCase(ProdutoServicePortOut produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos().stream().toList();
    }

    @Override
    public List<Produto> listarProdutoPorCategoria(String categoria) {
        return produtoService.listarProdutoPorCategoria(categoria);
    }

    @Override
    public Produto criarProduto(Produto produto) {
        return produtoService.criarProduto(produto);
    }

    @Override
    public Produto atualizarProduto(Long id, Produto produto) throws BadRequestException {
        return produtoService.atualizarProduto(id, produto);
    }

    @Override
    public void deletarPorId(Long id) {
        produtoService.deletarPorId(id);
    }
}

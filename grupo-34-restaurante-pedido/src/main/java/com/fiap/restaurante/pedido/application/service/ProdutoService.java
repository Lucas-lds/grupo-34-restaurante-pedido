package com.fiap.restaurante.pedido.application.service;

import com.fiap.restaurante.pedido.application.port.out.ProdutoAdapterPortOut;
import com.fiap.restaurante.pedido.application.port.out.service.ProdutoServicePortOut;
import com.fiap.restaurante.pedido.core.domain.Produto;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService implements ProdutoServicePortOut {

    private final ProdutoAdapterPortOut produtoAdapterPortOut;

    public ProdutoService(ProdutoAdapterPortOut produtoAdapterPortOut) {
        this.produtoAdapterPortOut = produtoAdapterPortOut;
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoAdapterPortOut.listarProdutos();
    }

    @Override
    public List<Produto> listarProdutoPorCategoria(String categoria) {
        return produtoAdapterPortOut.listarProdutoPorCategoria(categoria);
    }

    @Override
    public Produto criarProduto(Produto produto) {
        return produtoAdapterPortOut.criarProduto(produto);
    }

    @Override
    public Produto atualizarProduto(Long id, Produto produto) throws BadRequestException {
        return produtoAdapterPortOut.atualizarProduto(id, produto);
    }

    @Override
    public void deletarPorId(Long id) {
        produtoAdapterPortOut.deletarPorId(id);
    }
}

package com.fiap.restaurante.pedido.core.usecase;

import com.fiap.restaurante.pedido.application.port.out.service.ProdutoServicePortOut;
import com.fiap.restaurante.pedido.core.domain.Produto;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoUseCaseTest {

    @Mock
    private ProdutoServicePortOut produtoService;

    @InjectMocks
    private ProdutoUseCase produtoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarProdutos() {
        List<Produto> produtos = Arrays.asList(
            new Produto(1L, "Hambúrguer", "LANCHE", 25.0, "Hambúrguer artesanal", "url1"),
            new Produto(2L, "Refrigerante", "BEBIDA", 8.0, "Refrigerante 350ml", "url2")
        );
        when(produtoService.listarProdutos()).thenReturn(produtos);

        List<Produto> result = produtoUseCase.listarProdutos();

        assertEquals(2, result.size());
        verify(produtoService, times(1)).listarProdutos();
    }

    @Test
    void testListarProdutoPorCategoria() {
        List<Produto> produtos = Arrays.asList(
            new Produto(1L, "Hambúrguer", "LANCHE", 25.0, "Hambúrguer artesanal", "url1"),
            new Produto(2L, "Batata Frita", "LANCHE", 15.0, "Batata frita crocante", "url2")
        );
        when(produtoService.listarProdutoPorCategoria("LANCHE")).thenReturn(produtos);

        List<Produto> result = produtoUseCase.listarProdutoPorCategoria("LANCHE");

        assertEquals(2, result.size());
        verify(produtoService, times(1)).listarProdutoPorCategoria("LANCHE");
    }

    @Test
    void testCriarProduto() {
        Produto produto = new Produto(1L, "Hambúrguer", "LANCHE", 25.0, "Hambúrguer artesanal", "url1");
        when(produtoService.criarProduto(produto)).thenReturn(produto);

        Produto result = produtoUseCase.criarProduto(produto);

        assertNotNull(result);
        verify(produtoService, times(1)).criarProduto(produto);
    }

    @Test
    void testAtualizarProduto() throws BadRequestException {
        Produto produto = new Produto(1L, "Hambúrguer", "LANCHE", 25.0, "Hambúrguer artesanal", "url1");
        when(produtoService.atualizarProduto(1L, produto)).thenReturn(produto);

        Produto result = produtoUseCase.atualizarProduto(1L, produto);

        assertNotNull(result);
        verify(produtoService, times(1)).atualizarProduto(1L, produto);
    }

    @Test
    void testAtualizarProdutoThrowsBadRequestException() throws BadRequestException {
        Produto produto = new Produto(1L, "Hambúrguer", "LANCHE", 25.0, "Hambúrguer artesanal", "url1");
        when(produtoService.atualizarProduto(1L, produto)).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> produtoUseCase.atualizarProduto(1L, produto));
        verify(produtoService, times(1)).atualizarProduto(1L, produto);
    }

    @Test
    void testDeletarPorId() {
        doNothing().when(produtoService).deletarPorId(1L);

        produtoUseCase.deletarPorId(1L);

        verify(produtoService, times(1)).deletarPorId(1L);
    }
}

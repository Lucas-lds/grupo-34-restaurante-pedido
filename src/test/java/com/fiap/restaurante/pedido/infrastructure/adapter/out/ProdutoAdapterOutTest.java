package com.fiap.restaurante.pedido.infrastructure.adapter.out;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.pedido.core.domain.Produto;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.response.ProdutoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class ProdutoAdapterOutTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProdutoAdapterOut produtoAdapterOut;

    @Value("${produto.service.url}")
    private String produtoServiceUrl;

    private Long idProduto;

    @BeforeEach
    void setUp() {
        idProduto = 1L;
    }

    @Test
    void consultarProdutoDeveRetornarProdutoComSucesso() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ProdutoResponse produtoResponse = new ProdutoResponse(idProduto, "Produto Teste", "Categoria Teste", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
        ResponseEntity<ProdutoResponse> response = new ResponseEntity<>(produtoResponse, HttpStatus.OK);

        doReturn(response).when(restTemplate).exchange(eq(produtoServiceUrl + "/" + idProduto), eq(HttpMethod.GET), eq(requestEntity), eq(ProdutoResponse.class));

        Produto produto = produtoAdapterOut.consultarProduto(idProduto);

        assertNotNull(produto);
        assertEquals(idProduto, produto.getId());
        assertEquals("Produto Teste", produto.getNome());
        assertEquals("Categoria Teste", produto.getCategoria());
        assertEquals(100.0, produto.getPreco());
        assertEquals("Descricao Teste", produto.getDescricao());
        assertEquals("http://imagem.url/teste.jpg", produto.getImagemUrl());
        verify(restTemplate, times(1)).exchange(eq(produtoServiceUrl + "/" + idProduto), eq(HttpMethod.GET), eq(requestEntity), eq(ProdutoResponse.class));
    }

    @Test
    void consultarProdutoDeveLancarExcecaoQuandoProdutoNaoEncontrado() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ProdutoResponse> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(response).when(restTemplate).exchange(eq(produtoServiceUrl + "/" + idProduto), eq(HttpMethod.GET), eq(requestEntity), eq(ProdutoResponse.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoAdapterOut.consultarProduto(idProduto);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
        verify(restTemplate, times(1)).exchange(eq(produtoServiceUrl + "/" + idProduto), eq(HttpMethod.GET), eq(requestEntity), eq(ProdutoResponse.class));
    }
}

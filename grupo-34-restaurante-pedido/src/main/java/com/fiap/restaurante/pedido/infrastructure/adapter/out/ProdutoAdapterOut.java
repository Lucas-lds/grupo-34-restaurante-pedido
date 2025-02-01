package com.fiap.restaurante.pedido.infrastructure.adapter.out;

import com.fiap.restaurante.pedido.application.port.out.ProdutoPortOut;
import com.fiap.restaurante.pedido.core.domain.Produto;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.response.ProdutoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProdutoAdapterOut implements ProdutoPortOut {

    private final RestTemplate restTemplate;

    @Value("${produto.service.url}")
    private String produtoServiceUrl;

    public ProdutoAdapterOut(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Produto consultarProduto(Long idProduto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ProdutoResponse> response =
                restTemplate.exchange(produtoServiceUrl + "/" + idProduto, HttpMethod.GET,
                        requestEntity, ProdutoResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            ProdutoResponse produtoResponse = response.getBody();
            assert produtoResponse != null;
            return produtoResponse.toDomain();
        }

        throw new RuntimeException("Produto não encontrado");
    }
}

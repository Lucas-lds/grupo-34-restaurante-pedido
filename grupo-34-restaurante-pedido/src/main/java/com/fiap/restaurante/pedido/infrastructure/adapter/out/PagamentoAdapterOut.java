package com.fiap.restaurante.pedido.infrastructure.adapter.out;

import com.fiap.restaurante.pedido.application.port.out.PagamentoPortOut;
import com.fiap.restaurante.pedido.core.domain.Pedido;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.PagamentoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
public class PagamentoAdapterOut implements PagamentoPortOut {

    private final RestTemplate restTemplate;

    @Value("${pagamento.service.url}")
    private String pagamentoServiceUrl;

    public PagamentoAdapterOut(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void enviarPagamento(PedidoEntity pedido) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        PagamentoRequest pagamentoRequest = new PagamentoRequest(pedido.getId());

        HttpEntity<PagamentoRequest> request = new HttpEntity<>(pagamentoRequest, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(pagamentoServiceUrl,
                request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Pagamento enviado com sucesso!");
        } else {
            log.error("Erro ao enviar pagamento: {}", response.getStatusCode());
        }
    }
}

package com.fiap.restaurante.pedido.infrastructure.adapter.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.PagamentoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class PagamentoAdapterOutTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PagamentoAdapterOut pagamentoAdapterOut;

    @Value("${pagamento.service.url}")
    private String pagamentoServiceUrl;

    private PedidoEntity pedido;

    @BeforeEach
    void setUp() {
        pedido = new PedidoEntity();
        pedido.setId("123456");
    }

    @Test
    void enviarPagamentoDeveEnviarPagamentoComSucesso() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        PagamentoRequest pagamentoRequest = new PagamentoRequest(pedido.getId());
        HttpEntity<PagamentoRequest> request = new HttpEntity<>(pagamentoRequest, headers);
        ResponseEntity<String> response = new ResponseEntity<>("Pagamento enviado com sucesso!", HttpStatus.OK);

        when(restTemplate.postForEntity(eq(pagamentoServiceUrl), eq(request), eq(String.class))).thenReturn(response);

        pagamentoAdapterOut.enviarPagamento(pedido);

        ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate, times(1)).postForEntity(eq(pagamentoServiceUrl), captor.capture(), eq(String.class));

        HttpEntity<PagamentoRequest> capturedRequest = captor.getValue();
        assertNotNull(capturedRequest);
        assertEquals("123456", capturedRequest.getBody().idPedido());
    }

    @Test
    void enviarPagamentoDeveLogarErroQuandoNaoEnviarPagamentoComSucesso() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        PagamentoRequest pagamentoRequest = new PagamentoRequest(pedido.getId());
        HttpEntity<PagamentoRequest> request = new HttpEntity<>(pagamentoRequest, headers);
        ResponseEntity<String> response = new ResponseEntity<>("Erro ao enviar pagamento", HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.postForEntity(eq(pagamentoServiceUrl), eq(request), eq(String.class))).thenReturn(response);

        pagamentoAdapterOut.enviarPagamento(pedido);

        ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate, times(1)).postForEntity(eq(pagamentoServiceUrl), captor.capture(), eq(String.class));

        HttpEntity<PagamentoRequest> capturedRequest = captor.getValue();
        assertNotNull(capturedRequest);
        assertEquals("123456", capturedRequest.getBody().idPedido());
    }
}

package com.fiap.restaurante.pedido.infrastructure.adapter.in.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.pedido.application.port.out.usecase.PedidoUseCasePortOut;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.PedidoController;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.StatusRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.response.PedidoResponse;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @Mock
    private PedidoUseCasePortOut pedidoUseCasePortOut;

    @InjectMocks
    private PedidoController pedidoController;

    private PedidoRequest pedidoRequest;
    private StatusRequest statusRequest;
    private PedidoResponse pedidoResponse;
    private UUID pedidoId;

    @BeforeEach
    void setUp() {
        pedidoId = UUID.randomUUID();
        pedidoRequest = new PedidoRequest(12345L, List.of());
        statusRequest = new StatusRequest(2);
        pedidoResponse = new PedidoResponse(pedidoId.toString(), "RECEIVED", 12345L, List.of());
    }

    @Test
    void atualizarStatusPedidoDeveRetornarPedidoResponseComSucesso() throws BadRequestException {
        when(pedidoUseCasePortOut.atualizarStatusPedido(anyInt(), eq(pedidoId))).thenReturn(pedidoResponse);

        PedidoResponse response = pedidoController.atualizarStatusPedido(pedidoId, statusRequest);

        assertNotNull(response);
        assertEquals("RECEIVED", response.status());
        verify(pedidoUseCasePortOut, times(1)).atualizarStatusPedido(anyInt(), eq(pedidoId));
    }

    @Test
    void atualizarStatusPedidoDeveLancarBadRequestException() throws BadRequestException {
        when(pedidoUseCasePortOut.atualizarStatusPedido(anyInt(), eq(pedidoId))).thenThrow(new BadRequestException("Status não permitido."));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            pedidoController.atualizarStatusPedido(pedidoId, statusRequest);
        });

        assertEquals("Status não permitido.", exception.getMessage());
    }

    @Test
    void checkoutPedidoDeveRetornarResponseEntityComSucesso() {
        doNothing().when(pedidoUseCasePortOut).checkoutPedido(any(PedidoRequest.class));

        ResponseEntity<String> response = pedidoController.checkoutPedido(pedidoRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Pedido criado com sucesso", response.getBody());
        verify(pedidoUseCasePortOut, times(1)).checkoutPedido(any(PedidoRequest.class));
    }

    @Test
    void getPedidoByIdDeveRetornarPedidoResponseComSucesso() {
        when(pedidoUseCasePortOut.listarPedidoPorId(eq(pedidoId))).thenReturn(pedidoResponse);

        PedidoResponse response = pedidoController.getPedidoById(pedidoId);

        assertNotNull(response);
        assertEquals(pedidoId.toString(), response.id());
        verify(pedidoUseCasePortOut, times(1)).listarPedidoPorId(eq(pedidoId));
    }

    @Test
    void listarPedidosDeveRetornarListaDePedidoResponses() {
        List<PedidoResponse> pedidoResponses = List.of(pedidoResponse);
        when(pedidoUseCasePortOut.listarPedidos()).thenReturn(pedidoResponses);

        List<PedidoResponse> response = pedidoController.listarPedidos();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(pedidoUseCasePortOut, times(1)).listarPedidos();
    }
}

package com.fiap.restaurante.pedido.core;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.pedido.application.port.out.service.PedidoServicePortOut;
import com.fiap.restaurante.pedido.core.domain.Pedido;
import com.fiap.restaurante.pedido.core.domain.enums.OrderStatus;
import com.fiap.restaurante.pedido.core.usecase.PedidoUseCase;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.response.PedidoResponse;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PedidoUseCaseTest {

    @Mock
    private PedidoServicePortOut pedidoServicePortOut;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    private Pedido pedido;
    private PedidoRequest pedidoRequest;
    private PedidoResponse pedidoResponse;
    private UUID pedidoId;

    @BeforeEach
    void setUp() {
        pedidoId = UUID.randomUUID();
        pedido = new Pedido(pedidoId.toString(), OrderStatus.RECEIVED, 12345L, List.of());
        pedidoRequest = new PedidoRequest(12345L, List.of());
        pedidoResponse = PedidoResponse.fromDomain(pedido);
    }

    @Test
    void atualizarStatusPedidoDeveAtualizarStatusComSucesso() throws BadRequestException {
        when(pedidoServicePortOut.atualizarStatusPedido(anyInt(), eq(pedidoId))).thenReturn(pedido);

        PedidoResponse response = pedidoUseCase.atualizarStatusPedido(2, pedidoId);

        assertNotNull(response);
        assertEquals(OrderStatus.RECEIVED.toString(), response.status());
        verify(pedidoServicePortOut, times(1)).atualizarStatusPedido(anyInt(), eq(pedidoId));
    }

    @Test
    void atualizarStatusPedidoDeveLancarBadRequestExceptionParaStatusNaoPermitido() throws BadRequestException {
        doThrow(new BadRequestException("Status não permitido.")).when(pedidoServicePortOut).atualizarStatusPedido(anyInt(), eq(pedidoId));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            pedidoUseCase.atualizarStatusPedido(99, pedidoId);
        });

        assertEquals("Status não permitido.", exception.getMessage());
    }

    @Test
    void checkoutPedidoDeveChamarCheckoutPedidoDoService() {
        doNothing().when(pedidoServicePortOut).checkoutPedido(any(Pedido.class));

        pedidoUseCase.checkoutPedido(pedidoRequest);

        verify(pedidoServicePortOut, times(1)).checkoutPedido(any(Pedido.class));
    }

    @Test
    void listarPedidoPorIdDeveRetornarPedidoComSucesso() {
        when(pedidoServicePortOut.listarPedidoPorId(eq(pedidoId))).thenReturn(pedido);

        PedidoResponse response = pedidoUseCase.listarPedidoPorId(pedidoId);

        assertNotNull(response);
        assertEquals(pedidoId.toString(), response.id());
        verify(pedidoServicePortOut, times(1)).listarPedidoPorId(eq(pedidoId));
    }

    @Test
    void listarPedidosDeveRetornarListaDePedidos() {
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoServicePortOut.listarPedidos()).thenReturn(pedidos);

        List<PedidoResponse> listaPedidos = pedidoUseCase.listarPedidos();

        assertNotNull(listaPedidos);
        assertFalse(listaPedidos.isEmpty());
        assertEquals(1, listaPedidos.size());
        verify(pedidoServicePortOut, times(1)).listarPedidos();
    }
}

package com.fiap.restaurante.pedido.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.pedido.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.pedido.core.domain.Pedido;
import com.fiap.restaurante.pedido.core.domain.enums.OrderStatus;
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
public class PedidoServiceTest {

    @Mock
    private PedidoAdapterPortOut pedidoAdapterPortOut;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private UUID pedidoId;

    @BeforeEach
    void setUp() {
        pedidoId = UUID.randomUUID();
        pedido = new Pedido(pedidoId.toString(), OrderStatus.RECEIVED, 12345L, List.of());
    }

    @Test
    void atualizarStatusPedidoDeveAtualizarStatusParaPreparing() throws BadRequestException {
        when(pedidoAdapterPortOut.atualizarStatusPedido(eq(OrderStatus.PREPARING), eq(pedidoId))).thenReturn(pedido);

        Pedido pedidoAtualizado = pedidoService.atualizarStatusPedido(2, pedidoId);

        assertNotNull(pedidoAtualizado);
        assertEquals(OrderStatus.RECEIVED, pedidoAtualizado.getStatus());
        verify(pedidoAdapterPortOut, times(1)).atualizarStatusPedido(eq(OrderStatus.PREPARING), eq(pedidoId));
    }

    @Test
    void atualizarStatusPedidoDeveLancarBadRequestExceptionParaStatusNaoPermitido() {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            pedidoService.atualizarStatusPedido(99, pedidoId);
        });

        assertEquals("Status não permitido.", exception.getMessage());
    }

    @Test
    void checkoutPedidoDeveDefinirStatusParaReceived() {
        doNothing().when(pedidoAdapterPortOut).checkoutPedido(any(Pedido.class));

        pedidoService.checkoutPedido(pedido);

        assertEquals(OrderStatus.RECEIVED, pedido.getStatus());
        verify(pedidoAdapterPortOut, times(1)).checkoutPedido(any(Pedido.class));
    }

    @Test
    void listarPedidoPorIdDeveRetornarPedidoComSucesso() {
        when(pedidoAdapterPortOut.listarPedidoPorId(eq(pedidoId))).thenReturn(pedido);

        Pedido pedidoEncontrado = pedidoService.listarPedidoPorId(pedidoId);

        assertNotNull(pedidoEncontrado);
        assertEquals(pedidoId.toString(), pedidoEncontrado.getId());
        verify(pedidoAdapterPortOut, times(1)).listarPedidoPorId(eq(pedidoId));
    }

    @Test
    void listarPedidosDeveRetornarListaDePedidos() {
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoAdapterPortOut.listarPedidos()).thenReturn(pedidos);

        List<Pedido> listaPedidos = pedidoService.listarPedidos();

        assertNotNull(listaPedidos);
        assertFalse(listaPedidos.isEmpty());
        assertEquals(1, listaPedidos.size());
        verify(pedidoAdapterPortOut, times(1)).listarPedidos();
    }
}

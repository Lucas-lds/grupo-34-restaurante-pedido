package com.fiap.restaurante.pedido.infrastructure.adapter.out;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fiap.restaurante.pedido.application.port.out.PagamentoPortOut;
import com.fiap.restaurante.pedido.application.port.out.ProdutoPortOut;
import com.fiap.restaurante.pedido.core.domain.Pedido;
import com.fiap.restaurante.pedido.core.domain.Produto;
import com.fiap.restaurante.pedido.core.domain.ProdutoQuantidade;
import com.fiap.restaurante.pedido.core.domain.enums.OrderStatus;
import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;
import com.fiap.restaurante.pedido.infrastructure.adapter.out.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PedidoAdapterOutTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ModelMapper mapper;

    @Mock
    private PagamentoPortOut pagamentoPortOut;

    @Mock
    private ProdutoPortOut produtoPortOut;

    @InjectMocks
    private PedidoAdapterOut pedidoAdapterOut;

    private Pedido pedido;
    private PedidoEntity pedidoEntity;
    private Produto produto;
    private ProdutoQuantidade produtoQuantidade;
    private UUID pedidoId;

    @BeforeEach
    void setUp() {
        pedidoId = UUID.randomUUID();

        produto = new Produto(1L, "Produto Teste", "Categoria Teste", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
        produtoQuantidade = new ProdutoQuantidade(produto, 2);

        pedido = new Pedido(pedidoId.toString(), OrderStatus.RECEIVED, 12345L, List.of(produtoQuantidade));

        pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(pedidoId.toString());
        pedidoEntity.setIdCliente(12345L);
        pedidoEntity.setStatus(OrderStatus.RECEIVED.toString());
        pedidoEntity.setCreatedAt(LocalDateTime.now());
        pedidoEntity.setProdutosQuantidades(List.of(produtoQuantidade.toEntity()));
    }

    @Test
    void atualizarStatusPedidoDeveAtualizarStatusComSucesso() {
        when(pedidoRepository.findById(eq(pedidoId))).thenReturn(Optional.of(pedidoEntity));
        when(mapper.map(any(), eq(Pedido.class))).thenReturn(pedido);

        Pedido pedidoAtualizado = pedidoAdapterOut.atualizarStatusPedido(OrderStatus.DONE, pedidoId);

        assertNotNull(pedidoAtualizado);
        assertEquals(OrderStatus.RECEIVED, pedidoAtualizado.getStatus());
        verify(pedidoRepository, times(1)).findById(eq(pedidoId));
        verify(pedidoRepository, times(1)).save(any(PedidoEntity.class));
    }

    @Test
    void checkoutPedidoDeveSalvarPedidoComSucesso() {
        doNothing().when(pagamentoPortOut).enviarPagamento(any(PedidoEntity.class));
//        when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);

        pedidoAdapterOut.checkoutPedido(pedido);

        verify(pagamentoPortOut, times(1)).enviarPagamento(any(PedidoEntity.class));
        verify(pedidoRepository, times(1)).save(any(PedidoEntity.class));
    }

    @Test
    void listarPedidoPorIdDeveRetornarPedidoComSucesso() {
        when(pedidoRepository.findById(eq(pedidoId))).thenReturn(Optional.of(pedidoEntity));
        when(produtoPortOut.consultarProduto(eq(1L))).thenReturn(produto);

        Pedido pedidoEncontrado = pedidoAdapterOut.listarPedidoPorId(pedidoId);

        assertNotNull(pedidoEncontrado);
        assertEquals(pedidoId.toString(), pedidoEncontrado.getId());
        verify(pedidoRepository, times(1)).findById(eq(pedidoId));
        verify(produtoPortOut, times(1)).consultarProduto(eq(1L));
    }

    @Test
    void listarPedidoPorIdDeveLancarExcecaoQuandoPedidoNaoEncontrado() {
        when(pedidoRepository.findById(eq(pedidoId))).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoAdapterOut.listarPedidoPorId(pedidoId);
        });

        assertEquals("Pedido não encontrado", exception.getMessage());
        verify(pedidoRepository, times(1)).findById(eq(pedidoId));
    }

    @Test
    void listarPedidosDeveRetornarListaDePedidos() {
        List<PedidoEntity> pedidoEntities = List.of(pedidoEntity);
        when(pedidoRepository.findAllOrderedByStatus()).thenReturn(pedidoEntities);
        when(produtoPortOut.consultarProduto(anyLong())).thenReturn(produto);

        List<Pedido> pedidos = pedidoAdapterOut.listarPedidos();

        assertNotNull(pedidos);
        assertFalse(pedidos.isEmpty());
        verify(pedidoRepository, times(1)).findAllOrderedByStatus();
        verify(produtoPortOut, times(1)).consultarProduto(anyLong());
    }
}

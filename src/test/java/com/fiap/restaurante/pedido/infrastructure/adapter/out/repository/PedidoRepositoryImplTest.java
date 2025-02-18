package com.fiap.restaurante.pedido.infrastructure.adapter.out.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;

@ExtendWith(MockitoExtension.class)
public class PedidoRepositoryImplTest {

    @Mock
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Mock
    private DynamoDbTable<PedidoEntity> pedidoTable;

    @InjectMocks
    private PedidoRepositoryImpl pedidoRepository;

    private PedidoEntity pedidoEntity;
    private UUID pedidoId;

    @BeforeEach
    void setUp() {
        pedidoId = UUID.randomUUID();
        
        pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(pedidoId.toString());
        pedidoEntity.setIdCliente(12345L);
        pedidoEntity.setStatus("RECEIVED");
        pedidoEntity.setCreatedAt(LocalDateTime.now());

        lenient().when(dynamoDbEnhancedClient.table("Pedido", TableSchema.fromClass(PedidoEntity.class)))
            .thenReturn(pedidoTable);

    }

    @Test
    void save_ShouldSavePedidoEntity() {
        pedidoRepository.save(pedidoEntity);
        
        verify(pedidoTable).putItem(pedidoEntity);
    }

    @Test
    void findAll_ShouldReturnAllPedidos() {
        Page<PedidoEntity> page = Page.create(List.of(pedidoEntity));
        when(pedidoTable.scan()).thenReturn(PageIterable.create(() -> List.of(page).iterator()));

        List<PedidoEntity> result = pedidoRepository.findAll();
        
        assertEquals(1, result.size());
        assertEquals(pedidoEntity, result.get(0));
    }

    @Test
    void delete_ShouldDeletePedidoEntity() {
        pedidoRepository.delete(pedidoEntity);
        
        verify(pedidoTable).deleteItem(pedidoEntity);
    }

    @Test
    void findAllOrderedByStatus_ShouldReturnSortedPedidos() {
        PedidoEntity pedido1 = createPedido("RECEIVED");
        PedidoEntity pedido2 = createPedido("PREPARING");
        PedidoEntity pedido3 = createPedido("DONE");
        
        Page<PedidoEntity> page = Page.create(List.of(pedido1, pedido2, pedido3));
        when(pedidoTable.scan()).thenReturn(PageIterable.create(() -> List.of(page).iterator()));

        List<PedidoEntity> result = pedidoRepository.findAllOrderedByStatus();
        
        assertEquals(3, result.size());
        assertEquals("DONE", result.get(0).getStatus());
        assertEquals("PREPARING", result.get(1).getStatus());
        assertEquals("RECEIVED", result.get(2).getStatus());
    }

    @Test
    void getStatusPriority_ShouldReturnCorrectPriorities() {
        assertEquals(1, pedidoRepository.getStatusPriority("DONE"));
        assertEquals(2, pedidoRepository.getStatusPriority("PREPARING"));
        assertEquals(3, pedidoRepository.getStatusPriority("RECEIVED"));
        assertEquals(Integer.MAX_VALUE, pedidoRepository.getStatusPriority("UNKNOWN"));
    }

    private PedidoEntity createPedido(String status) {
        PedidoEntity pedido = new PedidoEntity();
        pedido.setId(UUID.randomUUID().toString());
        pedido.setStatus(status);
        pedido.setCreatedAt(LocalDateTime.now());
        return pedido;
    }
}

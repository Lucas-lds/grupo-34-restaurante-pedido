package com.fiap.restaurante.pedido.infrastructure.adapter.out.repository;

import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private static final String TABLE_NAME = "Pedido"; // Nome da tabela no DynamoDB

    @Autowired
    public PedidoRepositoryImpl(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    }

    @Override
    public void save(PedidoEntity pedidoEntity) {
        DynamoDbTable<PedidoEntity> pedidoTable = dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromClass(PedidoEntity.class));
        pedidoTable.putItem(pedidoEntity);
    }

    @Override
    public Optional<PedidoEntity> findById(UUID id) {
        DynamoDbTable<PedidoEntity> pedidoTable = dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromClass(PedidoEntity.class));
        PedidoEntity pedidoEntity = pedidoTable.getItem(r -> r.key(k -> k.partitionValue(String.valueOf(id))));
        return Optional.ofNullable(pedidoEntity);
    }

    @Override
    public List<PedidoEntity> findAll() {
        DynamoDbTable<PedidoEntity> pedidoTable = dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromClass(PedidoEntity.class));

        PageIterable<PedidoEntity> pageIterable = pedidoTable.scan();

        return StreamSupport.stream(pageIterable.spliterator(), false)
                .flatMap(page -> page.items().stream())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(PedidoEntity pedidoEntity) {
        DynamoDbTable<PedidoEntity> pedidoTable = dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromClass(PedidoEntity.class));
        pedidoTable.deleteItem(pedidoEntity);
    }

    @Override
    public List<PedidoEntity> findAllOrderedByStatus() {
        DynamoDbTable<PedidoEntity> pedidoTable = dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromClass(PedidoEntity.class));
        PageIterable<PedidoEntity> pageIterable = pedidoTable.scan();

        return StreamSupport.stream(pageIterable.spliterator(), false)
                .flatMap(page -> page.items().stream())
                .filter(pedido -> !pedido.getStatus().equals("FINISHED")).sorted((pedido1, pedido2) -> {
                    int statusPriority1 = getStatusPriority(pedido1.getStatus());
                    int statusPriority2 = getStatusPriority(pedido2.getStatus());
                    if (statusPriority1 == statusPriority2) {
                        return pedido1.getCreatedAt().compareTo(pedido2.getCreatedAt());
                    }
                    return Integer.compare(statusPriority1, statusPriority2);
                }).collect(Collectors.toList());
    }

    public int getStatusPriority(String status) {

        return switch (status) {
            case "DONE" -> 1;
            case "PREPARING" -> 2;
            case "RECEIVED" -> 3;
            default -> Integer.MAX_VALUE;
        };
    }
}

package com.fiap.restaurante.pedido.infrastructure.adapter.out.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import com.fiap.restaurante.pedido.core.domain.Pedido;
import com.fiap.restaurante.pedido.core.domain.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DynamoDbBean
public class PedidoEntity {

    private String id;
    private LocalDateTime createdAt;
    private String status;
    private Long idCliente;
    private List<ProdutoQuantidadeEntity> produtos;


    public PedidoEntity() {
        this.id = UUID.randomUUID().toString();

    }

    public PedidoEntity(String id, LocalDateTime createdAt, String status, Long idCliente, List<ProdutoQuantidadeEntity> produtos) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = createdAt;
        this.status = status;
        this.idCliente = idCliente;
        this.produtos = produtos;
    }


    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    @DynamoDbSortKey
    @DynamoDbAttribute("createdAt")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @DynamoDbAttribute("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @DynamoDbAttribute("idCliente")
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    @DynamoDbAttribute("produtos")
    public List<ProdutoQuantidadeEntity> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoQuantidadeEntity> produtos) {
        this.produtos = produtos;
    }

    // Conversão para o objeto de domínio (Pedido)
    public Pedido toDomain() {
        return new Pedido(id, OrderStatus.valueOf(status),
                idCliente,
                produtos.stream()
                        .map(ProdutoQuantidadeEntity::toDomain)
                        .toList());
    }
}

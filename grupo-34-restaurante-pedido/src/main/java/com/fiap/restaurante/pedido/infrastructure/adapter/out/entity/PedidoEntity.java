package com.fiap.restaurante.pedido.infrastructure.adapter.out.entity;

import com.fiap.restaurante.pedido.core.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pedidos")
public class PedidoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long idCliente;

    @Column(name = "data_pedido")
    private LocalDateTime createdAt;

}

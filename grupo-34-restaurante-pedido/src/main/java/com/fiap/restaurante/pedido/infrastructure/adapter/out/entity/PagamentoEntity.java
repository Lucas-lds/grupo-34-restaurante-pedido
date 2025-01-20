package com.fiap.restaurante.pedido.infrastructure.adapter.out.entity;

import com.fiap.restaurante.pedido.core.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pagamentos")
public class PagamentoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Long idPedido;

}

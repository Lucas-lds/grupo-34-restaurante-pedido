package com.fiap.restaurante.pedido.infrastructure.adapter.out.entity;

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
@Table(name = "tb_pedido_produtos")
public class PedidoProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido_produto")
    private Long id;

    @ManyToOne
    @JoinColumn(name = ("id_pedido"))
    private PedidoEntity pedido;

    @ManyToOne
    @JoinColumn(name = ("id_produto"))
    private ProdutoEntity produto;

    private Integer quantidade;

}

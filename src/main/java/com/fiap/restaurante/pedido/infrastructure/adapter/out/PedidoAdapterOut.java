package com.fiap.restaurante.pedido.infrastructure.adapter.out;


import com.fiap.restaurante.pedido.application.port.out.PagamentoPortOut;
import com.fiap.restaurante.pedido.application.port.out.PedidoAdapterPortOut;
import com.fiap.restaurante.pedido.application.port.out.ProdutoPortOut;
import com.fiap.restaurante.pedido.core.domain.Pedido;
import com.fiap.restaurante.pedido.core.domain.Produto;
import com.fiap.restaurante.pedido.core.domain.ProdutoQuantidade;
import com.fiap.restaurante.pedido.core.domain.enums.OrderStatus;
import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;
import com.fiap.restaurante.pedido.infrastructure.adapter.out.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class PedidoAdapterOut implements PedidoAdapterPortOut {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PagamentoPortOut pagamentoPortOut;
    @Autowired
    private ProdutoPortOut produtoPortOut;


    @Override
    public Pedido atualizarStatusPedido(OrderStatus status, UUID id) {
        var pedido = pedidoRepository.findById(id);
        pedido.ifPresent(t -> {
            t.setStatus(status.toString());
            pedidoRepository.save(t);
        });
        return mapper.map(pedido.get(), Pedido.class);
    }

    @Override
    public void checkoutPedido(Pedido pedido) {
        var pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(UUID.randomUUID().toString());
        pedidoEntity.setCreatedAt(LocalDateTime.now());
        pedidoEntity.setStatus(String.valueOf(pedido.getStatus()));
        pedidoEntity.setIdCliente(pedido.getIdCliente());

        pedidoEntity.setProdutosQuantidades(pedido.getListaProdutoQuantidade().stream().map(ProdutoQuantidade::toEntity).toList());

//        pagamentoPortOut.enviarPagamento(pedidoEntity);
        this.pedidoRepository.save(pedidoEntity);
    }

    @Override
    public Pedido listarPedidoPorId(UUID id) {
        var pedidoOptional = pedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            var pedido = pedidoOptional.get().toDomain();

            pedido.getListaProdutoQuantidade().forEach(produtoQuantidade -> {
                if (produtoQuantidade.getProduto() != null && produtoQuantidade.getProduto().getId() != null) {
                    produtoQuantidade.setProduto(preencherProduto(produtoQuantidade.getProduto().getId()));
                }
            });

            return pedido;
        } else {
            throw new RuntimeException("Pedido não encontrado");
        }
    }

    @Override
    public List<Pedido> listarPedidos() {
        var listaPedidosEntity = pedidoRepository.findAllOrderedByStatus();
        var listaPedidos = listaPedidosEntity.stream().map(PedidoEntity::toDomain).toList();

        listaPedidos.forEach(pedido -> pedido.getListaProdutoQuantidade().forEach(produtoQuantidade -> {
            if (produtoQuantidade.getProduto() != null && produtoQuantidade.getProduto().getId() != null) {
                produtoQuantidade.setProduto(preencherProduto(produtoQuantidade.getProduto().getId()));
            }
        }));
        return listaPedidos;
    }

    private Produto preencherProduto(Long idProduto) {
        return produtoPortOut.consultarProduto(idProduto);
    }
}

package com.fiap.restaurante.pedido.infrastructure.adapter.in;

import com.fiap.restaurante.pedido.application.port.out.usecase.PedidoUseCasePortOut;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.StatusRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.response.PedidoResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedido", description = "Operações de pedidos")
public class PedidoController {

    private final PedidoUseCasePortOut pedidoUseCasePortOut;

    public PedidoController(PedidoUseCasePortOut pedidoUseCasePortOut) {
        this.pedidoUseCasePortOut = pedidoUseCasePortOut;
    }

    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido.")
    @PutMapping("/{id}")
    public PedidoResponse atualizarStatusPedido(@PathVariable UUID id, @RequestBody StatusRequest statusRequest) throws BadRequestException {
        return pedidoUseCasePortOut.atualizarStatusPedido(statusRequest.status(), id);
    }

    @Operation(summary = "Checkout do pedido", description = "Finaliza o checkout de um pedido.")
    @PostMapping
    public ResponseEntity<String> checkoutPedido(@RequestBody PedidoRequest pedido) {
        pedidoUseCasePortOut.checkoutPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido criado com sucesso");
    }

    @Operation(summary = "Buscar pedido por ID", description = "Busca um pedido específico pelo seu ID.")
    @GetMapping("/{id}")
    public PedidoResponse getPedidoById(@PathVariable UUID id) {
        return pedidoUseCasePortOut.listarPedidoPorId(id);
    }

    @Operation(summary = "Listar todos os pedidos", description = "Lista todos os pedidos registrados no sistema.")
    @GetMapping
    public List<PedidoResponse> listarPedidos() {
        return pedidoUseCasePortOut.listarPedidos();
    }
}

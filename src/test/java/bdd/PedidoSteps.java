package bdd;

import com.fiap.restaurante.pedido.application.port.out.usecase.PedidoUseCasePortOut;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.PedidoController;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.PedidoProdutoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.ProdutoPedidoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.response.PedidoResponse;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.StatusRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class PedidoSteps {

    @Autowired
    private PedidoController pedidoController;

    @Autowired
    private PedidoUseCasePortOut pedidoUseCasePortOut;

    private PedidoRequest pedidoRequest;
    private PedidoResponse pedidoResponse;
    private ResponseEntity<String> responseEntity;
    private StatusRequest statusRequest;
    private UUID pedidoId;

    @Given("que eu tenho os dados de um novo pedido")
    public void queEuTenhoOsDadosDeUmNovoPedido() {
        ProdutoPedidoRequest produto1 = new ProdutoPedidoRequest(1L);
        ProdutoPedidoRequest produto2 = new ProdutoPedidoRequest(2L);
        PedidoProdutoRequest pedidoProduto1 = new PedidoProdutoRequest(produto1, 2);
        PedidoProdutoRequest pedidoProduto2 = new PedidoProdutoRequest(produto2, 3);
        pedidoRequest = new PedidoRequest(123L, List.of(pedidoProduto1, pedidoProduto2));
    }

    @When("eu solicitar o checkout do pedido")
    public void euSolicitarOCheckoutDoPedido() {
        doNothing().when(pedidoUseCasePortOut).checkoutPedido(pedidoRequest);
        responseEntity = pedidoController.checkoutPedido(pedidoRequest);
        verify(pedidoUseCasePortOut, times(1)).checkoutPedido(pedidoRequest);
    }

    @Then("o pedido deve ser criado com sucesso")
    public void oPedidoDeveSerCriadoComSucesso() {
        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals("Pedido criado com sucesso", responseEntity.getBody());
    }

    @Given("que um pedido existe no sistema com id {string}")
    public void queUmPedidoExisteNoSistemaComId(String id) {
        pedidoId = UUID.fromString(id);
        pedidoResponse = new PedidoResponse(pedidoId.toString(), "PENDENTE", 123L, List.of());
        when(pedidoUseCasePortOut.listarPedidoPorId(pedidoId)).thenReturn(pedidoResponse);
    }

    @When("eu solicitar a busca pelo pedido com id {string}")
    public void euSolicitarABuscaPeloPedidoComId(String id) {
        pedidoResponse = pedidoController.getPedidoById(UUID.fromString(id));
    }

    @Then("o pedido deve ser retornado com sucesso")
    public void oPedidoDeveSerRetornadoComSucesso() {
        assertNotNull(pedidoResponse);
        assertEquals(pedidoId.toString(), pedidoResponse.id());
        assertEquals("PENDENTE", pedidoResponse.status());
    }

    @Given("que existem pedidos no sistema")
    public void queExistemPedidosNoSistema() {
        PedidoResponse pedido1 = new PedidoResponse(UUID.randomUUID().toString(), "PENDENTE", 123L, List.of());
        PedidoResponse pedido2 = new PedidoResponse(UUID.randomUUID().toString(), "CONFIRMADO", 456L, List.of());
        List<PedidoResponse> pedidos = List.of(pedido1, pedido2);
        when(pedidoUseCasePortOut.listarPedidos()).thenReturn(pedidos);
    }

    @When("eu solicitar a lista de todos os pedidos")
    public void euSolicitarAListaDeTodosOsPedidos() {
        List<PedidoResponse> pedidos = pedidoController.listarPedidos();
        assertNotNull(pedidos);
        assertTrue(pedidos.size() > 0);
    }

    @Then("eu devo receber uma lista de pedidos")
    public void euDevoReceberUmaListaDePedidos() {
        List<PedidoResponse> pedidos = pedidoController.listarPedidos();
        assertNotNull(pedidos);
        assertTrue(pedidos.size() > 0);
    }
}

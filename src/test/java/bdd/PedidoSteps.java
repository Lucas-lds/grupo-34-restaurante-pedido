package bdd;

import com.fiap.restaurante.pedido.infrastructure.adapter.in.PedidoController;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.PedidoRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.request.StatusRequest;
import com.fiap.restaurante.pedido.infrastructure.adapter.in.response.PedidoResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = CucumberSpringConfiguration.class)
public class PedidoSteps {

    @Autowired
    private PedidoController pedidoController;

    private UUID pedidoId;
    private PedidoRequest pedidoRequest;
    private StatusRequest statusRequest;
    private PedidoResponse pedidoResponse;
    private ResponseEntity<?> responseEntity;


    @Given("que eu tenho os dados de um novo pedido")
    public void queEuTenhoOsDadosDeUmNovoPedido() {
        pedidoRequest = new PedidoRequest(1L, List.of());
    }

    @When("eu solicitar o checkout do pedido")
    public void euSolicitarOCheckoutDoPedido() {
        responseEntity = pedidoController.checkoutPedido(pedidoRequest);
    }

    @Then("o pedido deve ser criado com sucesso")
    public void oPedidoDeveSerCriadoComSucesso() {
        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals("Pedido criado com sucesso", responseEntity.getBody());
    }


}

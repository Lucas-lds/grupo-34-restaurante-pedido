package bdd;

import com.fiap.restaurante.pedido.RestaurantePedidoApplication;
import com.fiap.restaurante.pedido.application.port.out.usecase.PedidoUseCasePortOut;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.mockito.Mockito;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {RestaurantePedidoApplication.class, SpringGlue.TestConfig.class})
public class SpringGlue {

    // Configuração do mock
    @TestConfiguration
    static class TestConfig {
        @Bean
        public PedidoUseCasePortOut pedidoUseCasePortOut() {
            return Mockito.mock(PedidoUseCasePortOut.class);
        }
    }
}

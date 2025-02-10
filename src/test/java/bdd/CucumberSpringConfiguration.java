package bdd;

import com.fiap.restaurante.pedido.RestaurantePedidoApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = {RestaurantePedidoApplication.class})
public class CucumberSpringConfiguration {

}

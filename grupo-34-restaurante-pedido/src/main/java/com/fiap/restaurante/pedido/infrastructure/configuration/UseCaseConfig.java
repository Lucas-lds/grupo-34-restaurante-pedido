package com.fiap.restaurante.pedido.infrastructure.configuration;


import com.fiap.restaurante.pedido.application.port.out.service.ClienteServicePortOut;
import com.fiap.restaurante.pedido.application.port.out.service.ProdutoServicePortOut;
import com.fiap.restaurante.pedido.application.port.out.usecase.ClienteUseCasePortOut;
import com.fiap.restaurante.pedido.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.restaurante.pedido.core.usecase.ClienteUseCase;
import com.fiap.restaurante.pedido.core.usecase.ProdutoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

//    @Bean
//    public ClienteUseCasePortOut clienteUseCase(ClienteServicePortOut clienteService) {
//        return new ClienteUseCase(clienteService);
//    }
//
//  @Bean
//    public ProdutoUseCasePortOut produtoUseCase(ProdutoServicePortOut produtoService) {
//        return new ProdutoUseCase(produtoService);
//    }

}
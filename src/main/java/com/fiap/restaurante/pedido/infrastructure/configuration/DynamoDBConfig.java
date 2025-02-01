package com.fiap.restaurante.pedido.infrastructure.configuration;

import com.fiap.restaurante.pedido.infrastructure.adapter.out.entity.PedidoEntity;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.logging.Logger;

@Configuration
public class DynamoDBConfig {

    private static final Logger logger = Logger.getLogger(DynamoDBConfig.class.getName());

    @Value("${amazon.dynamodb.endpoint}")
    private String dynamoDbEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String awsAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String awsSecretKey;

    @Value("${amazon.region}")
    private String region;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(dynamoDbEndpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(awsAccessKey, awsSecretKey)))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        createTableIfNotExists(enhancedClient);
        return enhancedClient;
    }

    private void testConnection(DynamoDbClient dynamoDbClient) {
        try {
            ListTablesResponse response = dynamoDbClient.listTables(ListTablesRequest.builder().build());
            logger.info("Tabelas no DynamoDB: " + response.tableNames());
        } catch (DynamoDbException e) {
            logger.severe("Erro ao conectar ao DynamoDB: " + e.getMessage());
        }
    }

    private void createTableIfNotExists(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        DynamoDbTable<PedidoEntity> pedidoTable = dynamoDbEnhancedClient.table("Pedido",
                TableSchema.fromBean(PedidoEntity.class));

        try {
            pedidoTable.describeTable();
            logger.info("A tabela 'Pedido' já existe e está ativa.");
        } catch (DynamoDbException e) {
            try {
                pedidoTable.createTable();
                logger.info("Tabela 'Pedido' criada com sucesso.");
            } catch (DynamoDbException ex) {
                logger.severe("Erro ao criar a tabela 'Pedido': " + ex.getMessage());
            }
        }
    }
}

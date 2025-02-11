Feature: Operações de pedidos

  Scenario: Checkout do pedido
    Given que eu tenho os dados de um novo pedido
    When eu solicitar o checkout do pedido
    Then o pedido deve ser criado com sucesso

  Scenario: Buscar pedido por ID
    Given que um pedido existe no sistema com id "123e4567-e89b-12d3-a456-426614174000"
    When eu solicitar a busca pelo pedido com id "123e4567-e89b-12d3-a456-426614174000"
    Then o pedido deve ser retornado com sucesso

  Scenario: Listar todos os pedidos
    Given que existem pedidos no sistema
    When eu solicitar a lista de todos os pedidos
    Then eu devo receber uma lista de pedidos

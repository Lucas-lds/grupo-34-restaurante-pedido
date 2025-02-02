### OUTPUT PARA O SERVIÇO DE PEDIDO ###
output "pedido_service_url" {
  value = kubernetes_service.pedido_service.metadata[0].name
}
### SERVICE PARA O SERVIÇO DE PEDIDO ###
resource "kubernetes_service" "pedido_service" {
  metadata {
    name = "pedido-service"
    labels = {
      app = "pedido"
    }
  }

  spec {
    selector = {
      app = "pedido"
    }

    port {
      port        = 80
      target_port = 8080
    }

    type = "ClusterIP"
  }
}
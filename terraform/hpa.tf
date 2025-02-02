### HPA PARA O SERVIÇO DE PEDIDO ###
resource "kubernetes_horizontal_pod_autoscaler" "pedido_hpa" {
  metadata {
    name = "pedido-hpa"
  }

  spec {
    scale_target_ref {
      kind        = "Deployment"
      name        = kubernetes_deployment.pedido-api.metadata[0].name
      api_version = "apps/v1"
    }

    min_replicas = 1
    max_replicas = 10

    metric {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 50
        }
      }
    }
  }
}
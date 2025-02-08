### HPA PARA O SERVIÇO DE PEDIDO ###
resource "kubernetes_horizontal_pod_autoscaler" "pedido_hpa" {
  metadata {
    name = "pedido-hpa"
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = kubernetes_deployment.pedido-api.metadata[0].name
    }

    min_replicas                      = 1
    max_replicas                      = 10
    target_cpu_utilization_percentage = 70
  }
}
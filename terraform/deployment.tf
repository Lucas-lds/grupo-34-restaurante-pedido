### DEPLOYMENT PARA O SERVIÇO DE PEDIDO ###
resource "kubernetes_deployment" "pedido-api" {
  #depends_on = [aws_eks_cluster.eks-cluster, aws_eks_node_group.eks-cluster]

  metadata {
    name = "pedido"
    labels = {
      app = "pedido"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "pedido"
      }
    }

    template {
      metadata {
        labels = {
          app = "pedido"
        }
      }

      spec {
        service_account_name = kubernetes_service_account.db_pedido_dynamodb_account.metadata[0].name

        container {
          name  = "pedido"
          image = "717279688908.dkr.ecr.us-east-1.amazonaws.com/repositorio:v11"

          env {
            name  = "DYNAMODB_ENDPOINT"
            value = "https://dynamodb.${var.region}.amazonaws.com"
          }
          env {
            name  = "DYNAMODB_TABLE_NAME"
            value = aws_dynamodb_table.pedido_table.name
          }

          resources {
            limits = {
              cpu    = "1"
              memory = "1Gi"
            }
            requests = {
              cpu    = "500m"
              memory = "250Mi"
            }
          }

          port {
            container_port = 8080
          }

          liveness_probe {
            http_get {
              path = "/api/v1/actuator/health"
              port = 8080
            }
            initial_delay_seconds = 120
            period_seconds        = 10
            timeout_seconds       = 5
            failure_threshold     = 3
          }
        }
      }
    }
  }
}
resource "null_resource" "init_dynamodb" {
  provisioner "local-exec" {
    command     = <<EOT
python3 -m venv venv
./venv/bin/pip install boto3
./venv/bin/python ../docker/init_dynamodb.py
EOT
    interpreter = ["/bin/bash", "-c"]
  }

  depends_on = [aws_dynamodb_table.pedido_table]
}
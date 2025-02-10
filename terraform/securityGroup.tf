# Grupo de segurança para o microserviço pedido
resource "aws_security_group" "ssh_cluster_ms_pedido" {
  name   = "ssh_cluster_ms_pedido"
  vpc_id = "vpc-0bb3319f6293b884b"
}

resource "aws_security_group_rule" "ssh_cluster_ms_pedido_in" {
  type              = "ingress"
  from_port         = 22
  to_port           = 22
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.ssh_cluster_ms_pedido.id
}

resource "aws_security_group_rule" "ssh_cluster_ms_pedido_https" {
  type              = "ingress"
  from_port         = 443
  to_port           = 443
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.ssh_cluster_ms_pedido.id
}

resource "aws_security_group_rule" "ssh_cluster_ms_pedido_http_80" {
  type              = "ingress"
  from_port         = 80
  to_port           = 80
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.ssh_cluster_ms_pedido.id
}

resource "aws_security_group_rule" "ssh_cluster_ms_pedido_http" {
  type              = "ingress"
  from_port         = 8080
  to_port           = 8080
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.ssh_cluster_ms_pedido.id
}

resource "aws_security_group_rule" "ssh_cluster_ms_pedido_rds" {
  type              = "ingress"
  from_port         = 3306
  to_port           = 3306
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.ssh_cluster_ms_pedido.id
}

resource "aws_security_group_rule" "ssh_cluster_ms_pedido_out" {
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.ssh_cluster_ms_pedido.id
}


## GRUPO DE SEGURANÇA DO RDS (rds_sg) ###
resource "aws_security_group" "rds_sg" {
  name        = "rds-security-group-ms-pedido"
  description = "Security group for RDS"
  vpc_id      = "vpc-0bb3319f6293b884b"
}

resource "aws_security_group_rule" "rds_in" {
  type              = "ingress"
  from_port         = 3306
  to_port           = 3306
  protocol          = "tcp"
  security_group_id = aws_security_group.rds_sg.id
  cidr_blocks       = ["172.31.0.0/16"]
}

resource "aws_security_group_rule" "rds_out" {
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  security_group_id = aws_security_group.rds_sg.id
  cidr_blocks       = ["0.0.0.0/0"]
}
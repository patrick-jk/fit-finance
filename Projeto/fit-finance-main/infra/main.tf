provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "fit_finance_server" {
  ami           = "ami-00beae93a2d981137"
  instance_type = "t2.micro"
  key_name      = aws_key_pair.keypair.key_name

  user_data = file("user_data.sh")

  vpc_security_group_ids = [aws_security_group.security_group.id]

  tags = {
    Name = "fit-finance-server"
  }
}

resource "aws_key_pair" "keypair" {
  key_name = "terraform-keypair"
  public_key = file("~/.ssh/id_rsa.pub")
}

resource "aws_security_group" "security_group" {
  name        = "fit-finance-security-group"
  description = "Allow HTTP, MySQL, Prometheus, and SSH access"

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 80
    to_port   = 80
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 8080
    to_port   = 8080
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 3306
    to_port   = 3306
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 9090
    to_port   = 9090
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port   = 65535
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "example" {
  ami           = "ami-0c7217cdde317cfec"
  instance_type = "t2.micro"
  key_name      = "nextgen-devops-team"
  vpc_id        = "vpc-05d7fb7f1331f8f16"
  subnet_id     = "subnet-09bb946c638fdd9a3"
  security_groups = [
    "nextgen-devops-sg",
  ]
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "example" {
  ami           = "ami-0c7217cdde317cfec"
  instance_type = "t2.micro"
  key_name      = "nextgen-devops-team"
  subnet_id     = "subnet-09bb946c638fdd9a3"
}

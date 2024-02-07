pipeline{
agent any
    stages{
        stage('Git Clone'){
            steps{
                script{
                    git 'https://github.com/AbhishekRaoV/GenAI_POC.git'
                }
            }
        }

        stage('Generate terraform script'){
            
                steps{
                
                    script{
                        ws('GenAI_POC/'){
                        '''
                        sgpt "give me a plain terraform script without any headings, description, notes to provision aws ec2 instance usign following details ami id : ami-0c7217cdde317cfec, instance type : t2.micro, key pair : nextgen-devops-team, vpc id : vpc-05d7fb7f1331f8f16 (cmt-coe-vpc), subnet id : subnet-09bb946c638fdd9a3, security group : nextgen-devops-sg" --no-cache > terraform.txt
                        sed -n '/```/,/```/ {/```/!p}' terraform.txt > main.tf
                        '''
                    }
                }
            }
        }

        stage('Generate Pipeline script'){
            steps{
            
                    script{
                        ws('GenAI_POC/'){
                    '''
                    cat main.tf | sgpt "give me a plain jenkins pipeline script without any headings, description, notes to terraform init, terraform validate, and terraform apply to provision aws ec2 instance" --no-cache > pipeline.txt
                    sed -n '/```/,/```/ {/```/!p}' pipeline.txt > JenkinsFile
                    '''
                    }
                }
            }
        }

        stage('Git Push'){
            steps{
           
                script{
                     ws('GenAI_POC/'){
                    withCredentials([usernamePassword(credentialsId: '0c427375-967f-4826-a073-8d4476ad97b8', passwordVariable: 'token', usernameVariable: 'username')]) {
                        sh """
                            git add .
                            git commit -m "mian.tf and JenkinsFile ${BUILD_NUMBER}"
                            git push https://${username}:${token}@github.com/AbhishekRaoV/GenAI_POC.git 
                        """
                    }
                }
            }
        }
        }
    }
}

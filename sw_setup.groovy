pipeline{
    agent{
        label 'slave1'
    }
    stages{
        stage('clean workspace'){
            steps{
              cleanWs()  
            }
            
        }
        stage('git checkout'){
            steps{
             git branch: 'main', credentialsId: 'anusha_git_creds', url: 'https://github.com/yoshikapriyatham/testautomation.git'
            }
            
        }
        stage('Build'){
            steps{
              script{
               sh " ansible-playbook sw_install.yml"
            }  
            }
            
        }
    }
}

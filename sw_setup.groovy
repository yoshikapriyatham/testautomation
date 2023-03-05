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
               /* withCredentials([sshUserPrivateKey(credentialsId: 'node_creds', keyFileVariable: 'private_key', usernameVariable: 'user_name')]) {
                    script{
                        //sh " ansible-playbook sw_install.yml disableHostKeyChecking: true"
                          sh """
                          export ANSIBLE_HOST_KEY_CHECKING=False
                          ansible-playbook credentialsId: 'node_creds' sw_install.yml 
                          """
                        } 
                        }*/
                    
                    script {
                    ansiblePlaybook(
                        playbook: './sw_install.yml',
                        credentialsId: 'node_creds',
                        disableHostKeyChecking: true,
                        colorized: true)
                
                    }
            
            }
            
        }
    }
}

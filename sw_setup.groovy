pipeline{
    agent{
        label 'slave1'
    }
   environment {
    node_KEY = credentials('node_creds') // Secret value is 'sec%ret'
    ans_user="${env.node_KEY_USR}"
    pri_key="${env.node_KEY_PSW}"
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
                          ansible-playbook --private-key='$pri_key' -u='$ans_user' sw_install.yml 
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

pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/Razett/ProcureHub.git'
        BRANCH = 'master'
        DEPLOY_PATH = '/home/mit'
        APP_NAME = 'GoldenKids.jar'
        SSH_CREDENTIALS_ID = 'your_ssh_credentials_id'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${BRANCH}", url: "${GIT_REPO}"
            }
        }

     stage('Build') {
         steps {
             sh './gradlew clean build -x test'
         }
     }

        stage('Deploy') {
            steps {
                script {
                    // SERVER_LIST를 script 블록 내부에서 선언
                    def SERVER_LIST = [
                        "m-it.iptime.org:8030",
                        "m-it.iptime.org:8025"
                    ]

                    for (server in SERVER_LIST) {
                        def (serverAddress, port) = server.split(':')
                        echo "Deploying to ${serverAddress}:${port}"
                        sshagent (credentials: [SSH_CREDENTIALS_ID]) {
                            sh """
                            scp build/libs/${APP_NAME} root@${serverAddress}:${DEPLOY_PATH}/new_${APP_NAME}
                            ssh root@${serverAddress} << EOF
                            cd ${DEPLOY_PATH}
                            if [ -f "${APP_NAME}" ]; then
                                mv ${APP_NAME} backup_${APP_NAME}
                            fi
                            mv new_${APP_NAME} ${APP_NAME}
                            nohup java -jar ${APP_NAME} > app.log 2>&1 &
                            EOF
                            """
                        }
                    }
                }
            }
        }

        stage('Post-deployment Cleanup') {
            steps {
                script {
                    def SERVER_LIST = [
                        "m-it.iptime.org:8030",
                        "m-it.iptime.org:8025"
                    ]

                    for (server in SERVER_LIST) {
                        def (serverAddress, port) = server.split(':')
                        sshagent (credentials: [SSH_CREDENTIALS_ID]) {
                            sh """
                            ssh root@${serverAddress} << EOF
                            if [ -f "${DEPLOY_PATH}/backup_${APP_NAME}" ]; then
                                rm ${DEPLOY_PATH}/backup_${APP_NAME}
                            fi
                            EOF
                            """
                        }
                    }
                }
            }
        }
    }
}

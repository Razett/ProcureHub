pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/Razett/ProcureHub.git'
        BRANCH = 'master'
        DEPLOY_PATH = '/home/mit'
        APP_NAME = 'ProcureHub-0.0.1-SNAPSHOT.jar'
        SSH_CREDENTIALS_ID = 'GoldenKidsWeb-MIT'
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
                    // Define SERVER_LIST within the script block
                    def serverList = [
                        "m-it.iptime.org:2230",
                        "m-it.iptime.org:2225"
                    ]

                    // Iterate over SERVER_LIST and perform deployment
                    for (server in serverList) {
                        def (serverAddress, port) = server.split(':')
                        sshagent (credentials: [SSH_CREDENTIALS_ID]) {
                            sh """
                            scp -P ${port} -o StrictHostKeyChecking=no build/libs/${APP_NAME} mit@${serverAddress}:${DEPLOY_PATH}/new_${APP_NAME}
                            ssh -p ${port} -o StrictHostKeyChecking=no mit@${serverAddress} << EOF
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
                    def serverList = [
                        "m-it.iptime.org:2230",
                        "m-it.iptime.org:2225"
                    ]

                    for (server in serverList) {
                        def (serverAddress, port) = server.split(':')
                        sshagent (credentials: [SSH_CREDENTIALS_ID]) {
                            sh """
                            ssh -p ${port} -o StrictHostKeyChecking=no mit@${serverAddress} << EOF
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

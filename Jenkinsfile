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
                sh './gradlew clean build -Dspring.config.location=/var/jenkins/secure/application_secret.properties -x test'
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
                            // Step 1: SCP the file to the remote server
                            sh """
                            scp -P ${port} -o StrictHostKeyChecking=no build/libs/${APP_NAME} mit@${serverAddress}:${DEPLOY_PATH}/new_${APP_NAME}
                            """

                            // Step 2: Execute commands on the remote server after the file has been copied
                            sh """
                            ssh -p ${port} -o StrictHostKeyChecking=no mit@${serverAddress} << EOF

                            cd ${DEPLOY_PATH}
                            if [ -f "${APP_NAME}" ]; then
                                mv ${APP_NAME} backup_${APP_NAME}
                            fi
                            mv new_${APP_NAME} ${APP_NAME}
                            nohup java -jar ${APP_NAME} &
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

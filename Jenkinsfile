pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/Razett/ProcureHub.git'
        BRANCH = 'main'
        DEPLOY_PATH = '/home/mit' // 홈 디렉토리 내 배포할 경로
        APP_NAME = 'GoldenKids.jar' // 애플리케이션 JAR 파일 이름
        SSH_CREDENTIALS_ID = 'your_ssh_credentials_id' // SSH credentials ID
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${BRANCH}", url: "${GIT_REPO}"
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build' // 또는 mvnw clean package
            }
        }

        stage('Deploy') {
            steps {
                script {
                    def serverList = [
                        "m-it.iptime.org:8030",
                        "m-it.iptime.org:8025"
                    ] // 배포할 서버 목록

                    def deployToServer = { serverAddress, port ->
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

                    for (server in serverList) {
                        def (serverAddress, port) = server.split(':')
                        deployToServer(serverAddress, port)
                    }
                }
            }
        }

        stage('Post-deployment Cleanup') {
            steps {
                script {
                    def serverList = [
                        "m-it.iptime.org:8030",
                        "m-it.iptime.org:8025"
                    ] // 배포할 서버 목록

                    for (server in serverList) {
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

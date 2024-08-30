pipeline {
    agent any

    options {
            // 매 빌드 전에 워크스페이스 정리
            skipDefaultCheckout()
            buildDiscarder(logRotator(numToKeepStr: '5')) // 오래된 빌드 기록 유지 개수 설정
        }

    environment {
        GIT_REPO = 'https://github.com/Razett/ProcureHub.git'
        BRANCH = 'master'
        DEPLOY_PATH = '/home/mit'
        APP_NAME = 'ProcureHub-0.0.1-SNAPSHOT.jar'
        RELEASE_NAME = 'release.jar'
        SSH_CREDENTIALS_ID = 'goldenkidsWeb'
        SECRET_FILE_ID = 'secret' // Jenkins에 설정한 Secret file ID
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${BRANCH}", url: "${GIT_REPO}"
            }
        }

        stage('Prepare environment') {
            steps {
                script {
                    // Secret file을 가져와서 사용
                    withCredentials([file(credentialsId: SECRET_FILE_ID, variable: 'SECRET_FILE_PATH')]) {
                        // SECRET_FILE_PATH는 Jenkins가 파일을 다운로드한 경로
                        sh "cp \$SECRET_FILE_PATH src/main/resources/application-secret.properties"
                    }
                }
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
                            // Check and kill process running on port 8080
                            sh """
                            ssh -p ${port} -o StrictHostKeyChecking=no mit@${serverAddress} << EOF

                            PID=\$(lsof -t -i:8080)
                            if [ -n "\$PID" ]; then
                                echo "Killing process \$PID on port 8080"
                                kill -9 \$PID
                            else
                                echo "No process found on port 8080"
                            fi
                                fuser -k -n tcp 8080 || true

                            exit
                            EOF
                            """

                            // Step 1: SCP the file to the remote server
                            sh """
                            scp -P ${port} -o StrictHostKeyChecking=no build/libs/${APP_NAME} mit@${serverAddress}:${DEPLOY_PATH}/new_${APP_NAME}
                            """

                            // Step 2: Execute commands on the remote server after the file has been copied
                            sh """
                            ssh -p ${port} -o StrictHostKeyChecking=no mit@${serverAddress} << EOF

                            cd ${DEPLOY_PATH}
                            if [ -f "${RELEASE_NAME}" ]; then
                                mv ${RELEASE_NAME} backup_${RELEASE_NAME}
                            fi
                            mv new_${APP_NAME} ${RELEASE_NAME}
                            nohup java -jar ${RELEASE_NAME} > log.log 2>&1 &

                            exit
                            EOF
                            """

                            // Health check after deployment
                            def healthCheckCmd = """
                            ssh -p ${port} -o StrictHostKeyChecking=no mit@${serverAddress} << EOF
                            curl -f http://localhost:8080/health || exit 1
                            exit
                            EOF
                            """
                            // Retry up to 3 times if health check fails
                            retry(3) {
                                sh healthCheckCmd
                            }
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
                            if [ -f "${DEPLOY_PATH}/backup_${RELEASE_NAME}" ]; then
                                rm ${DEPLOY_PATH}/backup_${RELEASE_NAME}
                            fi
                            exit
                            EOF
                            """
                        }
                    }
                }
            }
        }
    }
}
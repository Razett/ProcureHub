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
        SSH_CREDENTIALS_ID = 'GoldenKidsWeb-MIT'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Git 저장소에서 최신 변경 사항을 강제로 가져오기
                    sh """
                    rm -rf *
                    git init
                    git fetch origin ${BRANCH}
                    git reset --hard origin/${BRANCH}
                    """
                }
            }
        }

        stage('Prepare environment') {
            steps {
                script {
                    // application-secret.properties 파일 생성
                    writeFile file: 'src/main/resources/application-secret.properties', text: """
                        spring.application.name=ProcureHub

                        # Database
                        spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
                        #spring.datasource.url=jdbc:mariadb://3.34.94.105:3306/ProcureHUB
                        spring.datasource.url=jdbc:mariadb://m-it.iptime.org:8026/ProcureHUB
                        spring.datasource.username=dev
                        spring.datasource.password=glkids1234

                        # Redis
                        spring.data.redis.host=3.34.94.105
                        spring.data.redis.password=glkids1234
                        spring.data.redis.port=6379
                    """
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
                            PID=\$(lsof -ti:8080)
                            if [ -n "\$PID" ]; then
                                kill -9 \$PID
                            fi
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
                            if [ -f "${APP_NAME}" ]; then
                                mv ${APP_NAME} backup_${APP_NAME}
                            fi
                            mv new_${APP_NAME} ${APP_NAME}
                            nohup java -jar ${APP_NAME} > log.log &
                            exit
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

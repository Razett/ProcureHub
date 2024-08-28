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
        SSH_CREDENTIALS_ID = 'GoldenKidsWeb-MIT'
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
                    // application-secret.properties 파일 생성
                    writeFile file: 'src/main/resources/application-secret.properties', text: """
                        spring.application.name=ProcureHub

                        # Database
                        spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
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
                    def serverList = [
                        "m-it.iptime.org:2230",
                        "m-it.iptime.org:2225"
                    ]

                    for (server in serverList) {
                        def (serverAddress, port) = server.split(':')
                        sshagent (credentials: [SSH_CREDENTIALS_ID]) {
                            // Check and kill process running on port 8080
                            sh """
                            ssh -p ${port} -o StrictHostKeyChecking=no mit@${serverAddress} << EOF
                            echo "Killing process on port 8080 if exists..."
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
                            set +e
                            cd ${DEPLOY_PATH}
                            echo "Moving release to backup_release if exists..."
                            if [ -f "${RELEASE_NAME}" ]; then
                                mv ${RELEASE_NAME} backup_${RELEASE_NAME}
                            fi
                            mv new_${APP_NAME} ${RELEASE_NAME}
                            nohup java -jar ${RELEASE_NAME} > log.log &
                            echo "Application started"
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
                            echo "Cleaning up backup files..."
                            if [ -f "${DEPLOY_PATH}/backup_${RELEASE_NAME}" ]; then
                                rm ${DEPLOY_PATH}/backup_${RELEASE_NAME} || true
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

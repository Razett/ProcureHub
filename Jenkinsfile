pipeline {
    agent any

    environment {
        // 서버 주소와 관련된 환경 변수 설정
        SERVER1 = 'm-it.iptime.org'
        SERVER2 = 'm-it.iptime.org'
        PORT1 = '8030'
        PORT2 = '8025'
    }

    stages {
        stage('Checkout') {
            steps {
                // Git에서 코드 체크아웃
                git url: 'https://github.com/Razett/ProcureHub.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                // Gradle 빌드
                script {
                    // Gradle Wrapper 사용 (권장)
                    sh './gradlew build'
                }
            }
        }

        stage('Deploy to Server 1') {
            steps {
                // 서버 1에 배포 (롤링 배포를 위한 예제)
                script {
                    deployToServer(SERVER1, PORT1)
                }
            }
        }

        stage('Deploy to Server 2') {
            steps {
                // 서버 2에 배포 (롤링 배포를 위한 예제)
                script {
                    deployToServer(SERVER2, PORT2)
                }
            }
        }
    }

    // Deploy 단계에서 사용할 함수 정의
    def deployToServer(server, port) {
        // 롤링 배포를 수행하는 커맨드 예제
        // 여기에 실제 배포 명령어를 입력
        echo "Deploying to ${server}:${port}"
        sh """
        curl -X POST http://${server}:${port}/deploy \
            -F 'file=@build/libs/your-app.jar' \
            -F 'version=${env.BUILD_NUMBER}'
        """
    }
}
pipeline {
    //  Где pipeline будет выполняться. Метка ноды в Jenkins (Android-билд-агент)
    agent { label 'android' }

    // Ограничение времени + запрет параллельных сборок
    options {
        timeout(time: 60, unit: 'MINUTES')
        disableConcurrentBuilds()
    }

    stages {
        // Проверка, что SDK найден
        stage('Info') {
            steps {
                sh '''
                    echo "Ветка: $BRANCH_NAME"
                    echo "ANDROID_HOME: $ANDROID_HOME"
                    chmod +x gradlew
                '''
            }
        }

        stage('Unit tests') {
            steps {
                sh './gradlew testDebugUnitTest --no-daemon'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/build/test-results/**/*.xml'
                }
            }
        }

        // Статический анализ кода
        stage('Lint') {
            steps {
                sh './gradlew lintDebug --no-daemon'
            }
        }

        // Debug APK: для develop и для Pull Request
        stage('Debug APK') {
            when {
                anyOf {
                    branch 'develop'
                    changeRequest()
                }
            }
            steps {
                sh './gradlew assembleDebug --no-daemon'
                archiveArtifacts artifacts: '**/build/outputs/apk/debug/*.apk', fingerprint: true
            }
        }

        // Release AAB: для main и release/*
        stage('Release AAB') {
            when {
                anyOf {
                    branch 'main'
                    branch pattern: 'release/.*', comparator: 'REGEXP'
                }
            }
            steps {
                sh './gradlew bundleRelease --no-daemon'
                archiveArtifacts artifacts: '**/build/outputs/bundle/release/*.aab', fingerprint: true
            }
        }
    }

    post {
        success { echo 'Сборка успешна' }
        failure { echo 'Сборка упала, смотрите лог выше' }
        cleanup { cleanWs() }
    }
}
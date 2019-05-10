pipeline {
    agent any
    options {
        ansiColor('xterm')
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
            post {
                always {
                    junit(testResults: '**/build/test-results/test/TEST-*.xml', allowEmptyResults: true)
                }
            }
        }
    }
}
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
        stage('Integration tests') {
            steps {
                dir('docker') {
                    sh 'docker-compose up -d'
                }
                sleep(time:10, unit: "SECONDS")
                sh './gradlew :authentication-core:integrationTest'
            }
            post {
                always {
                    junit(testResults: '**/build/test-results/integrationTest/TEST-*.xml', allowEmptyResults: true)
                    dir('docker') {
                        sh 'docker-compose down -v'
                    }
                }
            }
        }
        stage('Publish to local maven repository') {
            when {
                branch 'master'
            }
            steps {
                sh './gradlew publishToMavenLocal'
            }
        }
    }
}
pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                bat '''
                @echo off
                chcp 65001 > nul
                set MAVEN_OPTS=-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8
                mvn clean test
                '''
            }
        }

        stage('Generate Allure Report') {
            steps {
                script {
                    def allureHome = tool 'Allure'

                    bat """
                    dir allure-results
                    "${allureHome}\\bin\\allure.bat" generate allure-results --clean -o allure-report
                    """
                }
            }
        }
    }

    post {
        always {

            // نشر التقرير داخل Jenkins Allure Plugin
            allure(
                results: [[path: 'allure-results']]
            )

            // الاحتفاظ بنسخة HTML كـ Artifact
            archiveArtifacts artifacts: 'allure-report/**', fingerprint: true
        }
    }
}
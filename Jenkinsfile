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
            script {
                try {
                    bat '''
                    @echo off
                    chcp 65001 > nul
                    set MAVEN_OPTS=-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8
                    mvn clean test
                    '''
                } catch (Exception e) {
                    echo "Tests failed, continuing pipeline..."
                }
            }
        }
    }
}

post {
    always {
        script {
            bat 'dir allure-results'

            def allureHome = tool 'Allure'

            bat """
            "${allureHome}\\bin\\allure.bat" generate allure-results --clean -o allure-report
            """
        }

        archiveArtifacts artifacts: 'allure-report/**', fingerprint: true
        archiveArtifacts artifacts: 'allure-results/**', fingerprint: true
        archiveArtifacts artifacts: 'target/surefire-reports/**', fingerprint: true
    }
}

}
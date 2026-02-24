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
                bat 'chcp 65001'
                bat 'mvn clean test'
            }
        }
    }
}

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
               mvn clean test -q
               '''
           }
       }


   post {
        always {
            script {
                def allureHome = tool 'Allure'

                bat """
                "${allureHome}\\bin\\allure.bat" generate target\\allure-results --clean -o target\\allure-report
                """
            }

            archiveArtifacts artifacts: 'target/allure-report/**'
        }

   } }
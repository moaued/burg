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
               mvn clean test  -Dfile.encoding=UTF-8 -q
               '''
           }
       }
    }
}

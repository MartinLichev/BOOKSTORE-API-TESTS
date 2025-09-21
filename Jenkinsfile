pipeline {
  agent {
    docker { image 'maven:3.9.9-eclipse-temurin-17' }
  }
  options {
    timestamps()
    ansiColor('xterm')
  }
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build & Test') {
      steps {
        sh 'mvn -q -ntp -B clean test'
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
          archiveArtifacts artifacts: 'target/surefire-reports/**/*.*, target/allure-results/**/*.*', allowEmptyArchive: true
          script {
            try {
              allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            } catch (ignored) {
              echo 'Allure plugin not configured; skipping publish.'
            }
          }
        }
      }
    }
  }
}

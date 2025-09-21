pipeline {
  agent any
  options { ansiColor('xterm'); timestamps() }

  environment {
    // flip to true only if the API insists on application/json; v=1.0
    USE_VERSIONED_MEDIA_TYPE = 'false'
    VERSIONED_MEDIA_TYPE     = 'application/json; v=1.0'
    BASE_URL                 = 'https://fakerestapi.azurewebsites.net'
    API_BASE_PATH            = '/api/v1'
  }

  stages {
    stage('Sanity: Docker host') {
      steps {
        sh 'which docker && docker version'
      }
    }

    stage('Build & Test in Docker') {
      steps {
        script {
          docker.withTool('DockerCLI') {
            docker.image('maven:3.9.9-eclipse-temurin-17')
                  .inside('-v $HOME/.m2:/root/.m2') {
              sh """
                mvn -q -ntp -B clean test \
                  -DbaseUrl=${BASE_URL} \
                  -DapiBasePath=${API_BASE_PATH} \
                  -DuseVersionedMediaType=${USE_VERSIONED_MEDIA_TYPE} \
                  -DversionedMediaType='${VERSIONED_MEDIA_TYPE}'
              """
            }
          }
        }
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
          archiveArtifacts artifacts: 'target/surefire-reports/**/*.*, target/allure-results/**/*.*', allowEmptyArchive: true
          script {
            try {
              allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            } catch (e) { echo 'Allure plugin not configured; skipping.' }
          }
        }
      }
    }
  }
}

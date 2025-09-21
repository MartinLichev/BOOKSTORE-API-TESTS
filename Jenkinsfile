pipeline {
  agent any
  options { ansiColor('xterm'); timestamps() }

  parameters {
    choice(name: 'MAVEN_PROFILE', choices: ['full','smoke','regression'], description: 'Which test suite to run')
    string(name: 'BASE_URL', defaultValue: 'https://fakerestapi.azurewebsites.net', description: 'API base URL')
    string(name: 'API_BASE_PATH', defaultValue: '/api/v1', description: 'Base path')
    booleanParam(name: 'USE_VERSIONED_MEDIA_TYPE', defaultValue: false, description: 'Use application/json; v=1.0')
    string(name: 'VERSIONED_MEDIA_TYPE', defaultValue: 'application/json; v=1.0', description: 'Versioned content type')
  }

  stages {
    stage('Sanity: Docker host') { steps { sh 'which docker && docker version' } }

    stage('Build & Test in Docker') {
      steps {
        script {
          docker.withTool('DockerCLI') {
            docker.image('maven:3.9.9-eclipse-temurin-17').inside('-v $HOME/.m2:/root/.m2') {
         
              sh """
                mvn -q -ntp -B -P ${MAVEN_PROFILE} clean test \
                  -DbaseUrl=${BASE_URL} \
                  -DapiBasePath=${API_BASE_PATH} \
                  -DuseVersionedMediaType=${USE_VERSIONED_MEDIA_TYPE} \
                  -DversionedMediaType="${VERSIONED_MEDIA_TYPE}" \
                  -DlogOnFailure=true
              """
            }
          }
        }
      }
    }
  }

  post {
    always {
      junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
      archiveArtifacts artifacts: 'target/surefire-reports/**/*.*, target/allure-results/**/*.*', allowEmptyArchive: true
      script { try { allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']] } catch(e) { echo 'Allure not configured; skipping.' } }
    }
  }
}

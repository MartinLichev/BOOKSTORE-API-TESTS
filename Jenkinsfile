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
pipeline {
  agent any
  options { ansiColor('xterm'); timestamps() }

  parameters {
    choice(name: 'MAVEN_PROFILE', choices: ['full','smoke','regression'], description: 'Which test suite to run')
    string(name: 'BASE_URL',              defaultValue: 'https://fakerestapi.azurewebsites.net', description: 'API base URL')
    string(name: 'API_BASE_PATH',         defaultValue: '/api/v1',                               description: 'Base path')
    booleanParam(name: 'USE_VERSIONED_MEDIA_TYPE', defaultValue: false,                          description: 'Use application/json; v=1.0')
    string(name: 'VERSIONED_MEDIA_TYPE',  defaultValue: 'application/json; v=1.0',               description: 'Versioned content type')
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Sanity: Docker host') {
      steps { sh 'which docker && docker version' }
    }

    stage('Build & Test in Docker') {
      steps {
        script {
          // Uses Docker CLI tool you configured in Manage Jenkins → Tools (Installation root /usr/local on macOS)
          docker.withTool('DockerCLI') {
            docker.image('maven:3.9.9-eclipse-temurin-17')
                  .inside('-v $HOME/.m2:/root/.m2') {

              // IMPORTANT: triple **double** quotes so $VAR expands inside container shell
              sh """
                echo "== EFFECTIVE PARAMETERS =="
                echo "MAVEN_PROFILE=${MAVEN_PROFILE}"
                echo "BASE_URL=${BASE_URL}"
                echo "API_BASE_PATH=${API_BASE_PATH}"
                echo "USE_VERSIONED_MEDIA_TYPE=${USE_VERSIONED_MEDIA_TYPE}"
                echo "VERSIONED_MEDIA_TYPE=${VERSIONED_MEDIA_TYPE}"
                echo

                # Quick connectivity probe from inside the container
                (apk add --no-cache curl >/dev/null 2>&1 || true)
                echo "== Curl probe =="
                echo "Curling: ${BASE_URL}${API_BASE_PATH}/Books"
                curl -I --max-time 10 "${BASE_URL}${API_BASE_PATH}/Books" || true
                echo

                mvn -q -ntp -B -P ${MAVEN_PROFILE} clean test \\
                  -DbaseUrl=${BASE_URL} \\
                  -DapiBasePath=${API_BASE_PATH} \\
                  -DuseVersionedMediaType=${USE_VERSIONED_MEDIA_TYPE} \\
                  -DversionedMediaType="${VERSIONED_MEDIA_TYPE}" \\
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
      script {
        try {
          // Requires Allure Jenkins Plugin + Allure Commandline tool configured in Manage Jenkins → Tools
          allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
        } catch (e) {
          echo 'Allure plugin not configured; skipping publish.'
        }
      }
    }
  }
}

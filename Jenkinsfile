pipeline {
    agent { label 'linuxgit' }

    options {
        timestamps()
        skipDefaultCheckout(true)
    }

    environment {
        GIT_REPO   = 'https://github.com/vishnukumar-vv/p2p-maven.git'
        BRANCH     = 'main'
        MAVEN_HOME = tool 'Maven3'
        JAVA_HOME  = tool 'openjdk21'
        PATH       = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: BRANCH,
                    url: GIT_REPO,
                    credentialsId: 'gitHub_id'
            }
        }

        stage('Lint') {
            steps {
                echo 'Running Maven Checkstyle lint'
                sh '''
                    echo "JAVA_HOME=$JAVA_HOME"
                    java -version
                    mvn -version

                    mvn checkstyle:check > lint_report.txt 2>&1 || true

                    echo "----- Lint Report -----"
                    cat lint_report.txt
                '''
            }
            post {
                always {
                    archiveArtifacts artifacts: 'lint_report.txt', fingerprint: true
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Running Maven build'
                sh '''
                    mvn clean package -DskipTests
                '''
            }
        }

        stage('Unit Tests') {
            steps {
                sh '''
                    mvn test
                '''
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        success {
            echo '✅ Build, Lint, and Tests completed successfully'
        }
        failure {
            echo '❌ Pipeline failed – check logs'
        }
    }
}

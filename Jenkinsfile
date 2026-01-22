pipeline {
    agent { label 'linuxgit' }

    //options {
        //timestamps()
       // skipDefaultCheckout(true)
    //}

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

        stage('Lint & Build (Parallel)') {
            parallel {

                stage('Lint') {
                    steps {
                        echo 'Running Maven Checkstyle lint'
                        sh '''
                            java -version
                            mvn -version
                            mvn checkstyle:check > lint_report.txt 2>&1 || true
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
            echo '✅ Pipeline completed successfully'
        }
        failure {
            echo '❌ Pipeline failed – check logs'
        }
    }
}

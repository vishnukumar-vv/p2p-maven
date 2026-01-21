pipeline {
    agent { label 'linuxgit' }

    options {
        timestamps()
    }

    environment {
        GIT_REPO   = 'https://github.com/vishnukumar-vv/p2p-maven.git'
        BRANCH     = 'main'
        MAVEN_HOME = tool 'Maven3'
        JAVA_HOME  = tool 'openjdk21'
        PATH       = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {

        stage('Checkout repo') {
            steps {
                git branch: BRANCH,
                    url: GIT_REPO,
                    credentialsId: 'gitHub_id'
            }
        }

        stage('Lint') {
            steps {
                echo 'Running Maven lint checks'
                sh '''
                    cd maven
                    mvn -version
                    mvn checkstyle:check > lint_report.txt 2>&1 || true
                    cat lint_report.txt
                '''
            }
            post {
                always {
                    archiveArtifacts artifacts: 'maven/lint_report.txt', fingerprint: true
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Running maven build...'
                sh '''
                    cd maven
                    mvn clean package
                '''
            }
        }

        stage('Unit Tests') {
            steps {
                sh '''
                    cd maven
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
}

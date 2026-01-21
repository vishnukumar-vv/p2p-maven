stage('Lint') {
    steps {
        echo 'Running Maven lint checks'
        sh '''
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
        echo 'Running maven build...'
        sh '''
            mvn clean package
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

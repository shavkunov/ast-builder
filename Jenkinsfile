pipeline {

    agent {
        docker {
            image 'gradle:alpine' 
        }
    }

    stages {
        stage('build') { 
            steps {
                sh 'gradle build' 
            }
        }
    }

}
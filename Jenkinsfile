pipeline {
    agent { 
        label 'local' // Specify the label of your local Jenkins runner
    }

    stages {
        stage('Test') {
            steps {
                script {
                    // Run Maven test phase
                    sh 'mvn test'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Run Maven install phase
                    sh 'mvn install'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Copy the WAR file to the Tomcat directory
                    sh 'cp target/java-hello-world.war /opt/homebrew/opt/tomcat/libexec/webapps/jenkins1-tomcat3.war'
                }
            }
        }
    }
}

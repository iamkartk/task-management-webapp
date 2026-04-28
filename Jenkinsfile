pipeline {
	agent any 

	environment {
		// Set environment variables (TASK_VERSION will be injected during build)
		TASK_VERSION = "${BUILD_NUMBER}"
	}

	stages {
		stage('Clone Repository'){
			steps {
				// Step 1: Clone the GitHub repository
				git 'https://github.com/iamkartk/task-management-webapp.git'
			}
		}

		stage('Build Maven Project'){
			steps {
				// Step 2: Build the Maven project
				script { 
					sh 'mvn clean package -DskipTests'
				}
			}
		}

		stage('Build Docker Image'){
			steps {
				script {
					// Step 3: Build Docker image
					sh 'docker build -t task-service:${TASK_VERSION} .'
				}
			}
		}

		stage('Update .env File'){
			steps {
				// Step 4: Update the .env file with the build version
				script {
					sh 'echo "TASK_VERSION=${TASK_VERSION}" > .env'
				}
			}
		}

		stage('Deploy with Docker Compose'){
			steps {
				// Step 5: Stop and remove old containers, pull the latest image, and recreate container
				script {
					sh '''
					docker compose stop task-service 2>/dev/null || true
					docker compose rm -f task-service 2>/dev/null || true
					docker compose pull task-service || true
					docker compose up -d --no-deps task-service
					'''
				}
			}
		}

		stage('Clean up old docker images and containers'){
			steps {
				// Step 6: Clean up old Docker images, keep the latest two
				script {
					sh '''
					docker images task-service --format "{{.Tag}}" | sort -n | sed '$d' | sed '$d' | while read tag; do
                        docker rmi task-service:$tag 2>/dev/null || true
                    done
					'''
				}
			}
		}

	}

	post {
		always {
			// Clean up workspaces, even if the pipeline fails
			cleanWs()
		}
	}

}
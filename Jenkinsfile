pipeline {
	agent any 

	environment {
		// Set environment variables (TASK_VERSION will be injected during build)
		TASK_VERSION = "${BUILD_NUMBER}"
	}

	stages {
		

		stage('Build Maven Project'){
			steps {
				// Step 1: Build the Maven project
				script { 
					sh 'cd task-service && /opt/homebrew/bin/mvn clean package -DskipTests'
				}
			}
		}

		stage('Build Docker Image'){
			steps {
				script {
					// Step 2: Build Docker image
					sh 'cd task-service && /usr/local/bin/docker build -t task-service:${TASK_VERSION} .'
				}
			}
		}

		stage('Update .env File'){
			steps {
				// Step 3: Update the .env file with the build version
				script {
					sh 'echo "TASK_VERSION=${TASK_VERSION}" > .env'
				}
			}
		}

		stage('Deploy with Docker Compose'){
			steps {
				// Step 4: Stop and remove old containers, pull the latest image, and recreate container
				script {
					sh '''
					/usr/local/bin/docker rm -f task-service 2>/dev/null || true
            		/usr/local/bin/docker compose stop task-service 2>/dev/null || true
            		/usr/local/bin/docker compose rm -f task-service 2>/dev/null || true
            		/usr/local/bin/docker compose up -d --no-deps task-service
					'''
				}
			}
		}

		stage('Clean up old docker images and containers'){
			steps {
				// Step 5: Clean up old Docker images, keep the latest two
				script {
					sh '''
					/usr/local/bin/docker images task-service --format "{{.Tag}}" | sort -n | sed '$d' | sed '$d' | while read tag; do
                        /usr/local/bin/docker rmi task-service:$tag 2>/dev/null || true
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
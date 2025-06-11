How to build for development or production environments
use the --target flag on the Dockerfile

# DEV

docker buildx build --target dev -t classicmodelsapi-frontend-dev:latest --load -f Dockerfile .

docker run -d -p 5173:5173 --name frontend-dev classicmodelsapi-frontend-dev:latest
docker exec -it frontend-dev sh

# PROD

docker build -t mutistage:latest --target=prod

# React + Vite

Adicionar depois a variável de ambiente no docker-compose.yml

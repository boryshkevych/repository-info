## Prerequisites
1. java 11
2. docker

### Run unit tests
- `./gradlew test`

### Build and start application locally
- `./gradlew bootRun`

### Build docker image
- `./gradlew jibDockerBuild`

### Run docker container
- `docker run -it -p 8080:8080 repository-info:0.0.1-SNAPSHOT`

### Possible enhancements
- Add pagination

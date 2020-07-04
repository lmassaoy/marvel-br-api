# Marvel BR API

[![Screen-Shot-2020-07-02-at-02-45-24.png](https://i.postimg.cc/9FgyF1qR/Screen-Shot-2020-07-02-at-02-45-24.png)]()

This API was developed to serve data from the Global Marvel API's characters endpoint, translating the texts to Portuguese to better suit Brazilian Users.
Trying to make the user's experience better than using the official Marvel's API translating data, persisting in a MariaDB to decrease the calls to Marvel's API.

# Architecture

[![Screen-Shot-2020-07-04-at-03-38-02.png](https://i.postimg.cc/tCJs7hrv/Screen-Shot-2020-07-04-at-03-38-02.png)]()

## Tech stack


| Technology | Status |
| ------ | ------ |
| Java 11 OpenJDK | Done |
| Java 11 GraalVM | Done |
| Quarkus 1.5.2 (jvm mode) | Done |
| Quarkus 1.5.2 (native mode) | Working on it (currently facing issues with google's api vs native image) |
| Smallrye (reactive) | Done |
| GCP - Google Translation API | Done |
| MariaDB (latest image) | Done |
| Docker Compose | Done |
| Kubernetes | Planned (waiting for conclusion of the native mode issue) |

## Swagger
You're gonna be able to see the Swagger by accessing: http://localhost:8081/swagger-ui/ (once the application is running)
[![Screen-Shot-2020-07-04-at-04-01-42.png](https://i.postimg.cc/XJ08bVbW/Screen-Shot-2020-07-04-at-04-01-42.png)]()

## Running all services

### Docker
You should have a docker environment good to go (there are plenty of guides on the internet if you're not familiar with it).
As soon as you're ready, click in the logo below and follow the instructions
[![docker-logo.png](https://i.postimg.cc/mg0jmZzR/docker-logo.png)](https://github.com/lmassaoy/marvel-br-api/tree/master/marvel-br-api/src/main/devops/Instructions.md)

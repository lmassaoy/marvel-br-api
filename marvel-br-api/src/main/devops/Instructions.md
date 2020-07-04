# Docker-Compose
[![docker-logo.png](https://i.postimg.cc/mg0jmZzR/docker-logo.png)](https://postimg.cc/LJ3jXp30)

## 1st thing to do: Package your code
```sh
$ mvn package
```

## Build your image
```sh
$ docker build -f src/main/docker/Dockerfile.jvm -t lyamadadocker/marvel-br-api-jvm
```

## Run your docker compose
```sh
$ docker-compose up -d
```
## Now you are able to access locally:
| Port | Service |
| ------ | ------ |
| 3306 | mariaDB |
| 8082 | adminer |
| 8081 | marvel-br-api |

## and to close all services
```sh
$ docker-compose down
```
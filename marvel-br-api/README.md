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

# Swagger
You're gonna be able to see the Swagger by accessing: http://localhost:8081/swagger-ui/ (once the application is running)
##
[![Screen-Shot-2020-07-05-at-22-35-04.png](https://i.postimg.cc/J4QxbvrM/Screen-Shot-2020-07-05-at-22-35-04.png)]()
### You can check the status of your services by using the endpoint ***health***
[![Screen-Shot-2020-07-06-at-11-58-00.png](https://i.postimg.cc/Y2VncJvp/Screen-Shot-2020-07-06-at-11-58-00.png)]()

# Data Sample
### From the DB
[![Screen-Shot-2020-07-05-at-22-10-45.png](https://i.postimg.cc/Yq54CfLg/Screen-Shot-2020-07-05-at-22-10-45.png)]()
### From the Service
[![Screen-Shot-2020-07-05-at-23-06-09.png](https://i.postimg.cc/4yCdxXmG/Screen-Shot-2020-07-05-at-23-06-09.png)]()

# Running all services
## Pre-reqs
To use all services you'll need:
- A [Developer Marvel account](https://developer.marvel.com/account) (*Q_PARAM_APIKEY & Q_PARAM_HASH*);
- A [Google Platform account](https://console.cloud.google.com/) (*GOOGLE_API_KEY*);
- A Docker or Kubernetes cluster (you may use [Docker Hub](https://hub.docker.com/) or [Minikube](https://kubernetes.io/docs/setup/learning-environment/minikube/) too). 

### Docker
You should have a docker environment good to go (there are plenty of guides on the internet if you're not familiar with it).
As soon as you're ready, click in the logo below and follow the instructions
###
[![docker-logo.png](https://i.postimg.cc/mg0jmZzR/docker-logo.png)](https://github.com/lmassaoy/marvel-br-api/tree/master/marvel-br-api/src/main/devops/Instructions.md)
### Kubernetes
Soon :)

# All set! Getting started

### Option #1 - Load data into DB on-demand
If you want to persist the data as soon as you search for characters using the service, then you just need to start using the HTTP GET requests against the endpoints (you can find details in **Swagger**):
- **/character/{marvelId}** - if you already know the Marvel ID of the character (probably you won't know this as you start using the service).
- **/character** - For this option, you gonna perform some query params, such as:
-- **name**: the exact name you're looking for
-- **nameStartsWith**: the beginning the of the name you're looking for

#### PROS:
- You will only spend money by using Google Translation API as you use the application;
- If you're interested only testing the functionalities of the application, this mode is made for you :)
#### CONS:
- The first time a hero is requested it will take more time because it'll download the data from Marvel's API and persist it into the DB. The next searchs will bring directly from the DB.

### Option #2 - Load the data as soon you start
If you wish you can download all Marvel Characters from Marvel's API to fill your data table.
You just need to send a HTTP GET request to /api/download to load your database. Be aware this operation may take several minutes (11m average time) because will **truncate the table before load more than one thousand rows**.

#### PROS:
- After the inclusion, the consults will be directly made to the DB, granting a quicker response (8-20ms in tests);
- If you're interested in use this DB for BI/Analytics purposes, this mode is made for you :)
#### CONS:
- This will cost you around R$1-2 to translate the data using Google Translation API
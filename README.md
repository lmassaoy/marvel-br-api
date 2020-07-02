# marvel-br-api

[![Screen-Shot-2020-07-02-at-02-45-24.png](https://i.postimg.cc/9FgyF1qR/Screen-Shot-2020-07-02-at-02-45-24.png)](https://postimg.cc/fJXV8cCD)

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)]()

This API was developed to serve data from the Global Marvel API's characters endpoint, translating the texts to Portuguese to better suit Brazilian Users.
Trying to make the user's experience better than using the official Marvel's API translating data, persisting in a MariaDB to decrease the calls to Marvel's API.

# Architecture

[![Screen-Shot-2020-07-02-at-02-53-01.png](https://i.postimg.cc/bvVxVhQv/Screen-Shot-2020-07-02-at-02-53-01.png)](https://postimg.cc/WDrqtxYR)

### Tech stack


| Technology | Status |
| ------ | ------ |
| Java 11 OpenJDK | In use |
| Java 11 GraalVM | Planned |
| Quarkus 1.5.2 | In use |
| Quarkus 1.5.2 (native mode) | Planned |
| Smallrye (reactive) | In use |
| GCP - Google Translation API | In use |
| MariaDB (latest image) | In use |
| Docker | Planned |
| Kubernetes | Planned |

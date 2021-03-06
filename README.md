# Social data analytics service

[![Build Status](https://travis-ci.org/AceOfClub/social-data-analytics-service.svg?branch=master)](https://travis-ci.org/AceOfClub/social-data-analytics-service)
[![Latest release](https://img.shields.io/github/release/AceOfClub/social-data-analytics-service.svg)](https://github.com/AceOfClub/social-data-analytics-service/releases)


## Start Server

```bash
docker pull aceofclub/social-data-analytics-service:<<latest release>>
docker run -p 8080:8080 -p 8081:8081 aceofclub/social-data-analytics-service:<<latest release>>
```

## Social data by location

```bash
curl 'http://localhost:8080/data/location/latitude/13.0478221/longitude/80.0685838/radius/100'
```

## Social data by hashtag

```bash
curl 'http://localhost:8080/data/hashtag/Kalaignar'
```

## Dropwizard version

1.3.5

## Build

Run `./gradlew clean build`. The resulting JAR will be saved as `./build/libs/social-data-analytics-service-<<version>>-all.jar`.


## Run

```bash
java -jar build/libs/social-data-analytics-service-all.jar server
```
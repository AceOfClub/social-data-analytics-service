FROM openjdk:8 as source
WORKDIR /app
# build.gradle lists project dependencies
# These layers are only re-built when gradle files are updated
COPY *.gradle gradlew* ./
COPY gradle/ ./gradle
RUN ./gradlew build
# Copy all project and build it
# This layer is rebuilt when ever a file has changed in the project directory
COPY src/ ./src
RUN ./gradlew build jacocoTestReport --stacktrace
RUN bash -c "test \"$build_env\" == \"ci\" && eval 'bash <(curl -s https://codecov.io/bash)' || echo 'Skipping codecov update'"

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=source /app/build/libs/social-data-analytics-service-all.jar .
ENTRYPOINT java -jar social-data-analytics-service-all.jar server
EXPOSE 8080 8081
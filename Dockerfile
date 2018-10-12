FROM openjdk:8 as source
WORKDIR /app
COPY . .
RUN ./gradlew clean build

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=source /app/build/libs/social-data-analytics-service-2.0.0.jar .
ENTRYPOINT java -jar social-data-analytics-service-2.0.0.jar
EXPOSE 8080
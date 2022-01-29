FROM reg.edgeclusters.com/docker-hub-proxy/library/openjdk:17-jdk-slim-buster
#FROM gcr.io/distroless/java17-debian11

ARG APP_USER=spring
ARG APP_USER_UID=1000
ARG APP_USER_GID=1000
ARG JAR_FILE=target/*.jar

RUN addgroup --gid ${APP_USER_GID} ${APP_USER} \
    && adduser \
    --quiet \
    --disabled-password \
    --no-create-home \
    --uid ${APP_USER_UID} \
    --ingroup ${APP_USER} \
    ${APP_USER}

USER ${APP_USER}

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

FROM reg.edgeclusters.com/docker-hub-proxy/library/alpine as builder

ARG OTEL_AGENT_VERSION=1.12.0

RUN apk --quiet update \
    && apk --quiet add wget \
    && rm -rf /var/cache/apt/*

WORKDIR /

RUN wget --no-verbose https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_AGENT_VERSION}/opentelemetry-javaagent.jar


# FROM reg.edgeclusters.com/docker-hub-proxy/library/openjdk:17-jdk-slim-buster
FROM gcr.io/distroless/java:11

ARG APP_USER=spring
ARG APP_USER_UID=1000
ARG APP_USER_GID=1000
ARG JAR_FILE=target/*.jar

# RUN addgroup --gid ${APP_USER_GID} ${APP_USER} \
#     && adduser \
#     --gecos "First Last,RoomNumber,WorkPhone,HomePhone" \
#     --quiet \
#     --disabled-password \
#     --no-create-home \
#     --uid ${APP_USER_UID} \
#     --ingroup ${APP_USER} \
#     ${APP_USER}

# USER ${APP_USER}

COPY ${JAR_FILE} /app.jar
COPY --from=builder /opentelemetry-javaagent.jar /

ENTRYPOINT ["java", "-javaagent:/opentelemetry-javaagent.jar", "-jar", "/app.jar"]

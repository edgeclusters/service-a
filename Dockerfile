#FROM reg.edgeclusters.com/docker-hub-proxy/library/openjdk:17-jdk-slim-buster
FROM gcr.io/distroless/java17-debian11

# ARG APP_USER=spring
# ARG APP_USER_HOME=/home/${APP_USER}
# ARG APP_USER_UID=1000
# ARG APP_USER_GID=1000
# ARG JAR_FILE=target/*.jar

# RUN mkdir -p ${APP_USER_HOME} \
#     && addgroup --gid "${APP_USER_GID}" "${APP_USER}" \
#     && adduser --disabled-password --home "${APP_USER_HOME}" --ingroup "${APP_USER}"  --uid "${APP_USER_UID}" ${APP_USER} \
#     && chown -R ${APP_USER}:${APP_USER} "${APP_USER_HOME}"

# USER ${APP_USER}

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

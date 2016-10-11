#!/usr/bin/env bash
SOURCE_JAR="imagine*.jar"
TARGET_JAR="app.jar"
SOURCE_PORT=8080
TARGET_PORT=1988
IMAGE_NAME="yyqian/imagine"
CONTAINER_NAME="imagine"
# stop old build
docker stop ${CONTAINER_NAME} && docker rm ${CONTAINER_NAME}
# maven build
cd ..
mvn package
cp target/${SOURCE_JAR} docker/${TARGET_JAR}
# docker build
cd docker
docker build -t ${IMAGE_NAME} .
rm ${TARGET_JAR}
# docker run
docker run -d \
-p ${TARGET_PORT}:${SOURCE_PORT} \
--name ${CONTAINER_NAME} \
--link redis:redis \
-e spring.profiles.active="prod" \
-e spring.redis.host="redis" \
${IMAGE_NAME}
# clean up dangling images
docker rmi $(docker images -f "dangling=true" -q)

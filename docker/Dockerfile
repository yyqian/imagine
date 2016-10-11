FROM java:8
ENV DIR /usr/src/app

# Create app directory
RUN mkdir -p ${DIR}
WORKDIR ${DIR}

# Copy jar file
COPY app.jar ${DIR}/app.jar

# Run jar
EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]

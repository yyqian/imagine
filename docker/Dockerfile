FROM java:8

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Install and Run Redis
RUN apt-get update
RUN apt-get -y install redis-server
RUN redis-server --daemonize yes

# Copy jar file
COPY app.jar /usr/src/app/app.jar

# Run jar
EXPOSE 8080
CMD ["redis-server","--daemonize","yes","&&","java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]

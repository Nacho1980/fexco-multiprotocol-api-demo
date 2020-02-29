FROM java:8-jdk-alpine
RUN mkdir -p /usr/app/multiprotocolapi/server
COPY ./target/server-0.0.1-SNAPSHOT.jar /usr/app/multiprotocolapi/server
WORKDIR /usr/app/multiprotocolapi/server
# Execute a shell command that updates the “modified” date of the file
RUN sh -c 'touch server-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","server-0.0.1-SNAPSHOT.jar"]
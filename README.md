# fexco-multiprotocol-api-demo
Tech demo for Fexco with a multi protocol API which makes use of Java REST services and AMQP messaging. The application is a simple online car dealer with a frontend developed with React + Bootstrap + NodeJS and a backend developed with Java and Springboot. Every time a new car is added to the virtual dealer, the application sends a message from the Java backend to the RabbitMQ broker which is listened by a script running in NodeJS.

# Structure:
**/client**
contains the source code for a client implemented in React with Webpack + Babel + Bootstrap + a NodeJS backend. It includes the UI and also a NodeJS script to listen for the AMQP messages in the backend.

**/server**
the Java server contains the REST services and the AMQP message publisher. It's implemented using Springboot.

**/load_tests**
tests performed with jmeter and k6 to measure the performance of the application (there  are additional unit and integration tests in the src/test part of the java server)

>This application is intended to work with the RabbitMQ AMQP broker. The database can be configured in the server from application.properties, at the moment for demo purposes it's set to H2 in-memory DB.

>**QUICKSTART**

    • The RabbitMQ message broker is running as a service, the messages being sent can be monitored from http://localhost:15672/#/  (default credentials: guest/guest)
    • The server that produces the messages and publishes the REST CRUD services can be run as a java application from Eclipse (ServerApplication.java → run as → Java Application) or generate a jar with maven mvnw clean install which will be created in the /target directory and then run it from command prompt with: java -jar server-0.0.1-SNAPSHOT.jar      
    • The Node React client/frontend application can be run from the root directory of the client application with: npm start.
    • The message receiver can be run in another instance of node.js command line introducing the command: node amqp_receiver.js

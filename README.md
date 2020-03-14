# fexco-multiprotocol-api-demo
Tech demo for Fexco with a multi protocol API which makes use of Java REST services and AMQP messaging. The application is a simple online car dealer with a frontend developed with React + Bootstrap + NodeJS and a backend developed with Java and Springboot. Every time a new car is added to the virtual dealer, the application sends a message from the Java backend to the RabbitMQ broker which is listened by a script running in NodeJS.

# Structure:
**/client **
contains the source code for a client implemented in React with Webpack + Babel + Bootstrap + a NodeJS backend. It includes the UI and also a NodeJS script to listen for the AMQP messages in the backend.

**/server**
the Java server contains the REST services and the AMQP message publisher. It's implemented using Springboot.

**/load_tests**
tests performed with jmeter and k6 to measure the performance of the application (there  are additional unit and integration tests in the src/test part of the java server)

>This application is intended to work with the RabbitMQ AMQP broker. The database can be configured in the server from application.properties, at the moment for demo purposes it's set to H2 in-memory DB.

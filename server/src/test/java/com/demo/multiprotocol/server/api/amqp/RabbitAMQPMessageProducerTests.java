package com.demo.multiprotocol.server.api.amqp;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitAMQPMessageProducerTests {

	Logger logger = LoggerFactory.getLogger(RabbitAMQPMessageProducerTests.class);

	@Autowired
	private Publisher publisher;

	@Autowired
	private Receiver receiver;

	@Test
	public void test() throws Exception {
		try {
			publisher.sendMessage("Test message " + new Date().toString());
			receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		} catch (AmqpConnectException e) {
			logger.error("Error running tests", e);
		}
	}

}

package com.demo.multiprotocol.server.api.amqp;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Receiver for the RabbitMQ messages
 * 
 * @author Ignacio Santos
 *
 */
@Component
public class Receiver {

	Logger logger = LoggerFactory.getLogger(Receiver.class);

	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {
		logger.info("Received the message: <" + message + ">");
		System.out.println("Received <" + message + ">");
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}

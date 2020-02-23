package com.demo.multiprotocol.server.api.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Runner for the RabbitMQ AMQP server which sends the message
 * 
 * @author Ignacio Santos
 *
 */
@Component
public class Runner implements CommandLineRunner {

	// TODO delete this class
	@Autowired
	Publisher producer;

	Logger logger = LoggerFactory.getLogger(Runner.class);

	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
//		logger.info("Sending message...");
//		System.out.println("Sending message...");
//		rabbitTemplate.convertAndSend(producer.getTopicExchangeName(), producer.getRoutingKey(),
//				"Hello from RabbitMQ!");
//		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
	}

}

package com.demo.multiprotocol.server.api.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * This publisher will use the default exchange to write the messages to the
 * specified queues
 * 
 * @author Ignacio Santos
 *
 */
@Service
public class Publisher {
	Logger logger = LoggerFactory.getLogger(Publisher.class);

	@Value("${amqp.queue.new.car}")
	private String queue;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Bean
	public Queue myQueue() {
		boolean durable = true;
		return new Queue(queue, durable);
	}

	public void sendMessage(String message) {
		logger.info("Sending message '" + message + "' to queue " + queue);
		try {
			rabbitTemplate.convertAndSend(queue, message);
		} catch (AmqpException e) {
			logger.error("Error sending message '" + message + "' to queue " + queue, e);
		}
	}

//	public void sendMessage(String exchange, String routingKey, String message) {
//		rabbitTemplate.convertAndSend(exchange, routingKey, message);
//		logger.info("Sending message: " + message);
//	}

}

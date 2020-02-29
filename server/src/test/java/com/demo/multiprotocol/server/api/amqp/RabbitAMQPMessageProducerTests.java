package com.demo.multiprotocol.server.api.amqp;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

/**
 * AMQP messaging tests (using a test queue)
 * 
 * @author Ignacio Santos
 *
 */
@SpringBootTest
public class RabbitAMQPMessageProducerTests {

	@Value("${amqp.queue.test}")
	private String queue;

	@Autowired
	private TestListener listener;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Bean
	public Queue myQueue() {
		boolean durable = true;
		return new Queue(queue, durable);
	}

	@Test
	public void test() throws Exception {
		try {
			String message = "test";
			rabbitTemplate.convertAndSend(queue, message);
			Thread.sleep(5000); // wait 5 seconds
			Assertions.assertEquals(message, listener.getMessageReceived());
		} catch (Exception e) {
			fail("AMQP test failed", e);
		}
	}
}

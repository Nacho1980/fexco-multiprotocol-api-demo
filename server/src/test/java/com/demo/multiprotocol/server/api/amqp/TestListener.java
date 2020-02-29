package com.demo.multiprotocol.server.api.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * A very simple listener that can be used for testing purposes since in the
 * application the listener will be running in Node
 * 
 * @author Ignacio Santos
 *
 */
@Component
public class TestListener {
	private String messageReceived = "";

	@RabbitListener(queues = "${amqp.queue.test}")
	public void listen(String message) {
		System.out.println("Received a new message: " + message);
		messageReceived = message;
	}

	/**
	 * @return the messageReceived
	 */
	public String getMessageReceived() {
		return messageReceived;
	}

	/**
	 * @param messageReceived the messageReceived to set
	 */
	public void setMessageReceived(String messageReceived) {
		this.messageReceived = messageReceived;
	}
}

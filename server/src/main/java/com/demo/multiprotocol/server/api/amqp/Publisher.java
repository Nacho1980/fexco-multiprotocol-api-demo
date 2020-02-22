package com.demo.multiprotocol.server.api.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
	Logger logger = LoggerFactory.getLogger(Publisher.class);

	private final RabbitTemplate rabbitTemplate;

	@Value("${amqp.routing.key}")
	private String routingKey;

	@Value("${amqp.topic.exchange.name}")
	private String topicExchangeName;

	@Value("${amqp.queue.name}")
	private String queueName;

	/**
	 * Create AMQP queue
	 * 
	 * @return
	 */
	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	/**
	 * Create topic exchange
	 * 
	 * @return
	 */
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	/**
	 * Bind the queue and the exchange using the routing key
	 * 
	 * @param queue
	 * @param exchange
	 * @return
	 */
	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	public Publisher(// Receiver receiver,
			RabbitTemplate rabbitTemplate) {
		// this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	/**
	 * Send the message using the provided topic exchange and routing key
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		logger.info("Sending message: " + message);
		rabbitTemplate.convertAndSend(topicExchangeName, routingKey, message);
	}

	/**
	 * @return the routingKey
	 */
	public String getRoutingKey() {
		return routingKey;
	}

	/**
	 * @return the topicExchangeName
	 */
	public String getTopicExchangeName() {
		return topicExchangeName;
	}

	/**
	 * @return the queueName
	 */
	public String getQueueName() {
		return queueName;
	}
}

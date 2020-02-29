package com.demo.multiprotocol.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Server for RabbitMQ AMQP messages
 * 
 * @author Ignacio Santos
 *
 */
@SpringBootApplication
public class ServerApplication {
	private static Logger logger = LoggerFactory.getLogger(ServerApplication.class);

	public static void main(String[] args) {
		try {
			SpringApplication.run(ServerApplication.class, args);
		} catch (Exception e) {
			logger.error("Unexpected error when running the application", e);
		}
	}

}
package com.demo.multiprotocol.server;

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

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ServerApplication.class, args).close();
	}

}
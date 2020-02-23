package com.demo.multiprotocol.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Server for RabbitMQ AMQP messages
 * 
 * @author Ignacio Santos
 *
 */
@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ServerApplication.class, args);
	}

	/**
	 * Dealing with CORS issues when receiving calls from same localhost and
	 * different port (3000)
	 * 
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/cars").allowedOrigins("http://localhost:3000");
				registry.addMapping("/new").allowedOrigins("http://localhost:3000");
			}
		};
	}

}
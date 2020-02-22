package com.demo.multiprotocol.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Resource not found exception.
 *
 * @author Ignacio Santos
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8384436692720924177L;

	/**
	 * Instantiates a new Resource not found exception.
	 *
	 * @param message the message
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}

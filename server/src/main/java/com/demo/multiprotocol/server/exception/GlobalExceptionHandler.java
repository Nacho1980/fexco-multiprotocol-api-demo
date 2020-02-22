package com.demo.multiprotocol.server.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Exception handler to intercept and map to appropiate HTTP codes the custom
 * exceptions thrown in the controller
 *
 * @author Ignacio Santos
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Resource not found exception response entity.
	 *
	 * @param ex      the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorResponse errorDetails = new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * GlobalException handler response entity.
	 *
	 * @param ex      the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorResponse errorDetails = new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
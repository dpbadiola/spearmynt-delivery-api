package com.dpbapps.spearmynt.deliveryapi.endpoint.exception;

import com.dpbapps.spearmynt.deliveryapi.endpoint.exception.dto.RuleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<RuleErrorResponse> handleDataNotFoundException(DataNotFoundException ex) {
		RuleErrorResponse errorResponse = new RuleErrorResponse();
		errorResponse.setMessage(ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

}

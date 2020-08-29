package com.yamaniha.cep.api.advice;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yamaniha.cep.dto.ErrorDto;
import com.yamaniha.cep.exception.CepNotFoundException;

@ControllerAdvice
public class ApiControllerAdvice {
	
	@ExceptionHandler(CepNotFoundException.class)
	public ResponseEntity<ErrorDto> handleCepNotFoundException(final CepNotFoundException ex) {

		return buildResponseEntity(HttpStatus.NOT_FOUND, ex);
		
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDto> handleRuntimeException(final RuntimeException ex) {
		
		return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
		
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDto> handleConstraintViolationException(final ConstraintViolationException ex) {
		
		return buildResponseEntity(HttpStatus.BAD_REQUEST, ex);
		
	}
	
	private ResponseEntity<ErrorDto> buildResponseEntity(final HttpStatus httpStatus, final RuntimeException ex) {
		return ResponseEntity
				.status(httpStatus)
				.body(new ErrorDto(ex.getMessage()));
	}

}

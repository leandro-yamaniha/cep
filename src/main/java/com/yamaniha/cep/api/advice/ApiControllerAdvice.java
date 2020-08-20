package com.yamaniha.cep.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yamaniha.cep.dto.ErrorDto;
import com.yamaniha.cep.exception.CepNotFoundException;

@ControllerAdvice
public class ApiControllerAdvice {
	
	@ExceptionHandler(CepNotFoundException.class)
	public ResponseEntity<ErrorDto> handleCepNotFoundException(CepNotFoundException ex){

		return buildResponseEntity(HttpStatus.NOT_FOUND, ex);
		
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException ex){
		
		return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
		
	}


	private ResponseEntity<ErrorDto> buildResponseEntity(HttpStatus httpStatus, RuntimeException ex) {
		return ResponseEntity
				.status(httpStatus)
				.body(new ErrorDto(ex.getMessage()));
	}

}

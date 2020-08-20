package com.yamaniha.cep.exception;

public class CepNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7185475937372070194L;
	
	public CepNotFoundException(String cep){
		super("cep " + cep + " not found");
	}

}

package com.yamaniha.cep.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.yamaniha.cep.exception.CepNotFoundException;
import com.yamaniha.cep.service.impl.CepServiceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("testing CepService")
public class CepServiceTest {

	@InjectMocks
	private CepServiceImpl service;
	
	@Test
	@DisplayName("identical cep found")
	void identicalCodeFound() {
		
		String id = "14403500";
		var result =service.findCep(id);
		//Assertions.assertEquals(id, result.getCep());
				
	}
	
	@Test
	@DisplayName("similar cep found")
	void similarCodeFound() {
		
//		String id = "14403500";
//		var result =service.findCep(id);
//		Assertions.assertNotEquals(id, result.getCep());
//		Assertions.assertEquals(id.substring(0, 1), result.getCep().substring(0,1));
				
	}
	
	@Test
	@DisplayName("cep not found")
	void notFound() {
		
//		Assertions.assertThrows(CepNotFoundException.class, 
//				()->service.findCep("x"));
		
	}
		
	@Test
	@DisplayName("cep failed")
	void failed() {
		
//		Assertions.assertThrows(RuntimeException.class, 
//						()->service.findCep("x"));
		
	}
	
}

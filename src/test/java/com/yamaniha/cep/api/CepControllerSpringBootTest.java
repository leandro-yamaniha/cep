package com.yamaniha.cep.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yamaniha.cep.api.advice.ApiControllerAdvice;
import com.yamaniha.cep.integration.annotation.IntegrationTest;

@DisplayName("testando CepController com contexto do spring ...")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@IntegrationTest
class CepControllerSpringBootTest {

	private final static String ENDPOINT_GET = CepController.BASE_ENDPOINT.concat(CepController.GET_ENDPOINT);
	
	private MockMvc mockMvc;
	
	@Autowired
	private CepController controller;	
	
	@BeforeEach
	void setup() {
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ApiControllerAdvice())
				.build();
		
	}
	
	@Test()
	@Order(0)
	@DisplayName("verificando as inicialização das instancias ...")
	void initializationCheck(){
		
		Assertions.assertNotNull(mockMvc);		
		Assertions.assertNotNull(controller);
		
	}
	
	
	@ParameterizedTest
	@DisplayName("chamando endpoint com id inválido ...")
	@ValueSource(strings= {"1","12","123","1234","12345","123456","1234567"})
	void idInvalid(String id) throws Exception {
		
		mockMvc.perform(get(ENDPOINT_GET.replace("{id}", id))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())	
				.andExpect(MockMvcResultMatchers.jsonPath("$.erro", Matchers.containsString(CepController.MESSAGE_ID_INVALID)))
				;
	}
	
	
}

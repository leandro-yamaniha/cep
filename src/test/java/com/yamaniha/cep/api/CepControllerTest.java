package com.yamaniha.cep.api;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yamaniha.cep.api.advice.ApiControllerAdvice;
import com.yamaniha.cep.dto.CepDto;
import com.yamaniha.cep.exception.CepNotFoundException;
import com.yamaniha.cep.service.CepService;

@ExtendWith(MockitoExtension.class)
@DisplayName("testing CepController with mockito ...")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CepControllerTest {

	private final String ENDPOINT_GET = CepController.BASE_ENDPOINT.concat(CepController.GET_ENDPOINT);
	
	private MockMvc mockMvc;
	
	@Mock
	private CepService service;
	
	@InjectMocks
	private CepController controller;
	
	@BeforeEach
	void setup() {
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ApiControllerAdvice()).build();
		
	}
	
	@Test()
	@Order(0)
	@DisplayName("checking the startup of mocks instances")
	void initializationCheck(){
		
		Assertions.assertNotNull(mockMvc);
		Assertions.assertNotNull(service);
		Assertions.assertNotNull(controller);
		
	}
		
	@Test()
	@Order(2)
	@DisplayName("call endpoint expect ok")
	void ok() throws Exception {
		
		String id = "14403500";
		
		var dtoMock = buildCepDto();
		
		doReturn(dtoMock)
			.when(service).findCep(Mockito.anyString());
				
		mockMvc.perform(get(ENDPOINT_GET.replace("{id}", id))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("cep").value(dtoMock.getCep()))
				.andExpect(MockMvcResultMatchers.jsonPath("rua").value(dtoMock.getRua()))
				.andExpect(MockMvcResultMatchers.jsonPath("bairro").value(dtoMock.getBairro()))
				.andExpect(MockMvcResultMatchers.jsonPath("cidade").value(dtoMock.getCidade()))
				.andExpect(MockMvcResultMatchers.jsonPath("estado").value(dtoMock.getEstado()))
				;
				
	}
	
	

	@Test()
	@Order(3)
	@DisplayName("call endpoint expect not found")
	void notFound() throws Exception {
		
		String id = "12345678";
				
		doThrow(new CepNotFoundException(id))
			.when(service).findCep(Mockito.anyString());
				
		mockMvc.perform(get(ENDPOINT_GET.replace("{id}", id))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("cep").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("rua").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("bairro").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("cidade").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("estado").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("erro").value("cep " + id + " not found"))
				;
		
	}
	
	@Test()
	@Order(4)
	@DisplayName("call endpoint expect internal server error")
	void internalServerError() throws Exception {
		
		String id = "14403500";
		
		String message = "test exception";
		doThrow(new RuntimeException(message))
			.when(service).findCep(Mockito.anyString());
				
		mockMvc.perform(get(ENDPOINT_GET.replace("{id}", id))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.jsonPath("cep").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("rua").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("bairro").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("cidade").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("estado").doesNotExist())
				.andExpect(MockMvcResultMatchers.jsonPath("erro").value(message))
				;
	}

	private CepDto buildCepDto() {
		
		return CepDto.builder()
					.cep("14403500")
					.rua("Avenida Santa Cruz, 3255")
					.bairro("Vila Santa Cruz")
					.cidade("Franca")
					.estado("SP")							
					.build();
		
	}
}

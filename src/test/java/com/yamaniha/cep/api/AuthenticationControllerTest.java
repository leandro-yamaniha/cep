package com.yamaniha.cep.api;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties= {"security.jwt.prefix=Bearer","security.jwt.secret=test"})
@DisplayName("testando autenticação do token .. ")
class AuthenticationControllerTest {
	
	private final static String ENDPOINT_GET = CepController.BASE_ENDPOINT
											.concat(CepController.GET_ENDPOINT)
											.replace("{id}", "14403500");
		
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	AuthenticationControllerTest(WebApplicationContext context){
		this.context = context;
	}
	
	@BeforeEach
    void setup() {
		 mockMvc = MockMvcBuilders
         .webAppContextSetup(context)
         .apply(springSecurity())
         .build();
    }
	
	@Test
	@DisplayName("acesso não autorizado pois está sem header de autenticação")
	void isUnauthorized() throws Exception {
		 mockMvc.perform(get(ENDPOINT_GET)
					.contentType(MediaType.APPLICATION_JSON))				
					.andDo(print())
					.andExpect(status().isUnauthorized());
	}
	
	 @Test
	 @DisplayName("acesso não autorizado pois está header inválido")
	 void invalidHeader() throws Exception {
		 mockMvc.perform(get(ENDPOINT_GET)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", "test"))
					.andDo(print())
					.andExpect(status().isUnauthorized());
	 }
	
	 
	 @Test
	 @DisplayName("acesso não autorizado quando token é inválido")
	 void invalidToken() throws Exception {
		 mockMvc.perform(get(ENDPOINT_GET)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjZXAtbWFuYWdlciIsIm5hbWUiOiJsZWFuZHJvIiwiaWF0IjoxNjAxMjU5MDAwfQ.ItrgBVjFVOruze1KrsqXpP2dTWeqCW_sntui4SDXHGE"))
					.andDo(print())
					.andExpect(status().isUnauthorized());
	 }
	 
	 @WithMockUser("admin")
	 @Test
	 @DisplayName("acesso não autorizado quando usuário invalido")
	 void invalidUsername() throws Exception {
		 mockMvc.perform(get(ENDPOINT_GET)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsIm5hbWUiOiJsZWFuZHJvIiwiaWF0IjoxNjAxMjU5MDAwfQ.l8jioyej-gViKmVCHy75trYoC3ZBJlt2AdKMfZqdJkw"))
					.andDo(print())
					.andExpect(status().isUnauthorized());
	 }
	
	 @WithMockUser("admin")
	 @Test
	 @DisplayName("acesso autorizado")
	 void isOk() throws Exception {
		 mockMvc.perform(get(ENDPOINT_GET)
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjZXAtbWFuYWdlciIsIm5hbWUiOiJsZWFuZHJvIiwiaWF0IjoxNjAxMjU5MDAwfQ.fb3cm1mfceZV5RCpx1MQm-TiGhDgEEujt_xwx8iLMzY"))
					.andDo(print())
					.andExpect(status().isNotFound());
	 }
	 	 

	 
	
}

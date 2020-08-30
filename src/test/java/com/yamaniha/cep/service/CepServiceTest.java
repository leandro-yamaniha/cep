package com.yamaniha.cep.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Optional;

import org.hibernate.HibernateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.yamaniha.cep.exception.CepNotFoundException;
import com.yamaniha.cep.model.CepAddress;
import com.yamaniha.cep.repository.CepAddressRepository;
import com.yamaniha.cep.service.impl.CepServiceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("testando CepService")
class CepServiceTest {

	private static final String CEP = "14403500";
	private static final String CEP_ALTERNATIVE1 = "14806000";
	private static final String CEP_ALTERNATIVE2 = "04626970";
	
	
	@Mock
	private CepAddressRepository repository;
	
	@InjectMocks
	private CepServiceImpl service;
	
	@Test
	@DisplayName("quando o cep identico for encontrado")
	void whenCepIsFound() {
		
		doReturn(buildCepAddress()).when(repository).findById(Mockito.anyString());
		var result =service.findCep(CEP);
		Assertions.assertEquals(CEP, result.getCep());
				
	}
	
	@Test
	@DisplayName("quando encontrado o cep sugerido")
	void whenSimilarCepIsFound() {
		
		String cep = "14806434";
		doReturn(buildCepAddressAlternative1()).when(repository).getFirstSimilar(Mockito.anySet());
		var result =service.findCep(cep);
		Assertions.assertNotEquals(cep, result.getCep());
		Assertions.assertEquals(cep.substring(0, 1), result.getCep().substring(0,1));
		
		cep = "04626979";
		doReturn(buildCepAddressAlternative2()).when(repository).getFirstSimilar(Mockito.anySet());
		result =service.findCep(cep);
		Assertions.assertNotEquals(cep, result.getCep());
		Assertions.assertEquals(cep.substring(0, 1), result.getCep().substring(0,1));
		Assertions.assertEquals(cep.substring(1, 1), result.getCep().substring(1,1));
				
	}
	
	@Test
	@DisplayName("quando o cep não for encontrado")
	void whenCepIsNotFound() {
		
		Assertions.assertThrows(CepNotFoundException.class, 
				()->service.findCep("10000000"));

		Assertions.assertThrows(CepNotFoundException.class, 
				()->service.findCep("14806434"));
		
	}
		
	@Test
	@DisplayName("quando o cep falhar")
	void whenCepFailed() {
		
		doThrow(new HibernateException("test")).when(repository).findById(Mockito.anyString());		
		Assertions.assertThrows(HibernateException.class, 
						()->service.findCep(CEP));
		
	}
	
	@ParameterizedTest
	@DisplayName("generate ceps of ")
	@ValueSource(strings= {"12345678","12345670","12345600","12345000","12340000","12300000","12000000","10000000",
							"01234567","01234560","01234500","01234000","01230000","01200000","01000000",
							"01000007","01000067","01000067","01000567","01004567","01034567",
							"10045678","10305678"})
	void cepGenerate(String id) {

		var ids = service.generateIds(id);

		int total = id.length()-1;		
		total -= id.length() - id.replaceAll("0", "").length();

		Assertions.assertEquals(total, ids.size());

	}
	
	private Optional<CepAddress> buildCepAddress() {

		CepAddress address = new CepAddress();
		address.setCep(CEP);
		address.setRua("Avenida Santa Cruz");
		address.setBairro("Vila Santa Cruz");
		address.setCidade("Franca");
		address.setEstado("SP");
		return Optional.of(address);

	}

	private Optional<CepAddress> buildCepAddressAlternative1() {

		CepAddress address = new CepAddress();
		address.setCep(CEP_ALTERNATIVE1);
		address.setRua("Avenida José Parisi ");
		address.setBairro("Vila Velosa ");
		address.setCidade("Araraquara");
		address.setEstado("SP");
		return Optional.of(address);

	}
	
	private Optional<CepAddress> buildCepAddressAlternative2() {

		CepAddress address = new CepAddress();
		address.setCep(CEP_ALTERNATIVE2);
		address.setRua("Avenida José Parisi ");
		address.setBairro("Vila Velosa ");
		address.setCidade("São Paulo");
		address.setEstado("SP");
		return Optional.of(address);

	}
	
}

package com.yamaniha.cep.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yamaniha.cep.dto.CepDto;
import com.yamaniha.cep.service.CepService;

@RestController
@RequestMapping(CepController.BASE_ENDPOINT)
public class CepController {
		
	public static final String BASE_ENDPOINT = "/v1/cep";
	public static final String GET_ENDPOINT = "/{cep}";
	
	@Autowired
	private CepService service;
	
	@GetMapping(GET_ENDPOINT)
	public ResponseEntity<CepDto> getCep(@PathVariable("cep") String cep) {
		
		return ResponseEntity.ok(service.findCep(cep));
		
	}
	
}

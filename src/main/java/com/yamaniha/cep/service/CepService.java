package com.yamaniha.cep.service;

import java.util.Set;

import com.yamaniha.cep.dto.CepDto;

public interface CepService {

	CepDto findCep(String cep);
	
	Set<String> generateIds(String id);

	CepDto findSimilarCep(String id);
	
}

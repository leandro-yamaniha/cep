package com.yamaniha.cep.service.impl;

import org.springframework.stereotype.Service;

import com.yamaniha.cep.dto.CepDto;
import com.yamaniha.cep.service.CepService;

@Service
public class CepServiceImpl implements CepService {

	@Override
	public CepDto findCep(String cep) {

		return new CepDto();
		
	}

}

package com.yamaniha.cep.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.yamaniha.cep.dto.CepDto;
import com.yamaniha.cep.exception.CepNotFoundException;
import com.yamaniha.cep.model.CepAddress;
import com.yamaniha.cep.repository.CepAddressRepository;
import com.yamaniha.cep.service.CepService;

@Service
public class CepServiceImpl implements CepService {

	CepAddressRepository repository;
	
	public CepServiceImpl(final CepAddressRepository repository) {
		this.repository = repository;
	}
	
	@Cacheable(value="findCep")
	@Override
	public CepDto findCep(final String id) {

		final var optionalAddress = repository.findById(id);
		if (optionalAddress.isPresent()) {
			return toDto(optionalAddress.get());
		}

		return findSimilarCep(id);
		
	}

	@Override
	public Set<String> generateIds(final String id) {

		final Set<String> ids = new HashSet<String>();		
		int initialPosition = 0;		
		if (id.substring(0, 1).equals("0")) {
			initialPosition = 1;
		}
		final int lastPosition = id.length() - 1;		
		for (int position = lastPosition; position > initialPosition; position--) {
			final var idGenerate = id.substring(0, position).concat("0".repeat(8 - position));
			if (!id.equals(idGenerate)) {
				ids.add(idGenerate);		
			}
		}
		return ids;
		
	}

	@Override
	public CepDto findSimilarCep(final String id) {
		
		final Set<String> ids = generateIds(id);
		if (!ids.isEmpty()) {
			final var address = repository.getFirstSimilar(ids);				
			if (address.isPresent()) {		
				return toDto(address.get());
			}
		}
		throw new CepNotFoundException(id);
	}

	
	private CepDto toDto(final CepAddress cepAddress) {

		return CepDto.builder()
				.cep(cepAddress.getCep())
				.rua(cepAddress.getRua())
				.bairro(cepAddress.getBairro())
				.cidade(cepAddress.getCidade())
				.estado(cepAddress.getEstado())
				.build();

	}
}

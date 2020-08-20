package com.yamaniha.cep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CepDto {
	
	private String cep;
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;

}

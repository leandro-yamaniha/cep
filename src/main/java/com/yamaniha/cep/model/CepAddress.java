package com.yamaniha.cep.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Cep_Address")
@Data
public class CepAddress {

	@Id
	@Column(length = 8)
	private String cep;

	@Column(length = 200)
	private String rua;

	@Column(length = 200)
	private String bairro;

	@Column(length = 200)
	private String cidade;

	@Column(length = 2)
	private String estado;

}
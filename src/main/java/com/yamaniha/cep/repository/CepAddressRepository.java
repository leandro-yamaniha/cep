package com.yamaniha.cep.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yamaniha.cep.model.CepAddress;

@Repository
public interface CepAddressRepository extends JpaRepository<CepAddress, String> {

	@Query(value = "select c from CepAddress c where c.id in (?1)", 
			nativeQuery = false)
	Set<CepAddress> getAll(Set<String> ids);

	@Query(value = "select c.* from Cep_Address c where c.cep in (:ids) order by c.cep desc limit 1", 
			nativeQuery = true)
	Optional<CepAddress> getFirstSimilar(@Param("ids") Set<String> ids);

}
package com.yamaniha.cep.api;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yamaniha.cep.dto.CepDto;
import com.yamaniha.cep.service.CepService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(CepController.BASE_ENDPOINT)
@Validated
public class CepController {
		
	public static final String MESSAGE_ID_INVALID = "inválido, pois não tem 8 caracteres";
	public static final String BASE_ENDPOINT = "/v1/cep";
	public static final String GET_ENDPOINT = "/{id}";
	
	@Autowired
	private CepService service;
	
	@ApiOperation("Get address by cep")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "address found"),
            @ApiResponse(code = 400, message = "cep invalid"),
            @ApiResponse(code = 401, message = "unathorized"),
            @ApiResponse(code = 404, message = "address not found for this cep"),
            @ApiResponse(code = 500, message = "internal server error")
    })
	@GetMapping(value=GET_ENDPOINT,produces="application/json")
	public ResponseEntity<CepDto> getCep(@PathVariable("id") @Size(min=8 ,max = 8, message=MESSAGE_ID_INVALID) String id) {
		
		return ResponseEntity.ok(service.findCep(id));
		
	}
	
}

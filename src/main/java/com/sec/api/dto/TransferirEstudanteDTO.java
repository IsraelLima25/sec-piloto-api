package com.sec.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class TransferirEstudanteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String rgEstudante;
	
	@NotBlank
	private String cnpjEscolaDestino;

	public TransferirEstudanteDTO() {

	}

	public TransferirEstudanteDTO(String cnpjEscolaDestino, String rgEstudante) {
		this.cnpjEscolaDestino = cnpjEscolaDestino;
		this.rgEstudante = rgEstudante;		
	}
	
	public String getRgEstudante() {
		return rgEstudante;
	}
	
	public void setRgEstudante(String rgEstudante) {
		this.rgEstudante = rgEstudante;
	}

	public String getCnpjEscolaDestino() {
		return cnpjEscolaDestino;
	}

	public void setCnpjEscolaDestino(String cnpjEscolaDestino) {
		this.cnpjEscolaDestino = cnpjEscolaDestino;
	}
}

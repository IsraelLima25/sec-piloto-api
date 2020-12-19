package com.sec.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.lang.NonNull;

public class EstudanteTurmaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NonNull
	private Long codigoTurma;
	
	@NotBlank
	private String matriculaEstudante;

	public EstudanteTurmaDTO() {

	}

	public EstudanteTurmaDTO(Long codigoTurma, String matriculaEstudante) {
		this.codigoTurma = codigoTurma;
		this.matriculaEstudante = matriculaEstudante;
	}

	public Long getCodigoTurma() {
		return codigoTurma;
	}

	public void setCodigoTurma(Long codigoTurma) {
		this.codigoTurma = codigoTurma;
	}

	public String getMatriculaEstudante() {
		return matriculaEstudante;
	}

	public void setMatriculaEstudante(String matriculaEstudante) {
		this.matriculaEstudante = matriculaEstudante;
	}

}

package com.sec.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Escola implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(readOnly = true)
	private Long codigo;

	@CNPJ
	@NotBlank
	@Column(unique = true)
	private String cnpj;

	@NotBlank
	private String nome;

	@OneToMany(mappedBy = "escolaTurma", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JsonManagedReference
	@ApiModelProperty(readOnly = true)
	private Set<Turma> turmas = new HashSet<>();

	@OneToMany(mappedBy = "escolaEstudante", cascade = CascadeType.REMOVE)
	@JsonManagedReference
	@ApiModelProperty(readOnly = true)
	private Set<Estudante> estudantes = new HashSet<>();

	public Escola() {
	}

	public Escola(@CNPJ @NotBlank String cnpj, @NotBlank String nome, Set<Turma> turmas) {
		super();
		this.cnpj = cnpj;
		this.nome = nome;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@ApiModelProperty(readOnly = true)
	public Integer getQuantidadeTotalEstudantesPorEscola() {

		return this.estudantes.size();
	}

	public Set<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(Set<Turma> turmas) {
		this.turmas = turmas;
	}

	public Set<Estudante> getEstudantes() {
		return estudantes;
	}

	public void setEstudantes(Set<Estudante> estudantes) {
		this.estudantes = estudantes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Escola other = (Escola) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}

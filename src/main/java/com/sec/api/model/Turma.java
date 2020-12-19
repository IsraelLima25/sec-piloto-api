package com.sec.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Turma implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(readOnly = true)
	private Long codigo;	
	
	@NotBlank
	private String descricao;
	
	@ManyToOne
	@JsonBackReference
	@ApiModelProperty(hidden = true)
	private Escola escolaTurma;
	
	@OneToMany	
	@JoinColumn(name = "turma_id")
	@ApiModelProperty(readOnly = true)
	private Set<Estudante> estudantesTurma = new HashSet<>();

	public Turma() {
	}

	public Turma(String descricao) {
		super();
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Escola getEscolaTurma() {
		return escolaTurma;
	}

	public void setEscolaTurma(Escola escolaTurma) {
		this.escolaTurma = escolaTurma;
	}

	
	public Set<Estudante> getEstudantesTurma() {
		return estudantesTurma;
	}

	public void setEstudantesTurma(Set<Estudante> estudantesTurma) {
		this.estudantesTurma = estudantesTurma;
	}
	
	@ApiModelProperty(readOnly = true)
	public Integer getQuantidadeEstudantePorTurma() {
		return this.estudantesTurma.size();
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
		Turma other = (Turma) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}

package com.sec.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Estudante implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(readOnly = true)
	private Long codigo;
	
	@NotBlank
	@Column(unique = true)
	private String rg;
	
	@Column(unique = true)
	@ApiModelProperty(readOnly = true)
	private String matricula;

	@NotBlank
	private String nome;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;

	@NotBlank
	private String serie;

	@ManyToOne
	@JoinColumn(name = "escola_id")
	@JsonBackReference
	@ApiModelProperty(readOnly = true, hidden = true)
	private Escola escolaEstudante;

	private BigDecimal valorMensalidade;

	public Estudante() {
	}

	public Estudante(String nome, LocalDate dataNascimento, BigDecimal valorMensalidade, BigDecimal desconto,
			BigDecimal valorTotal, String serie, String rg) {
		super();
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.serie = serie;
		this.rg = rg;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Escola getEscolaEstudante() {
		return escolaEstudante;
	}

	public void setEscolaEstudante(Escola escolaEstudante) {
		this.escolaEstudante = escolaEstudante;
	}

	@ApiModelProperty(readOnly = true)
	public BigDecimal getValorMensalidade() {
		return valorMensalidade;
	}

	public void setValorMensalidade(BigDecimal valorMensalidade) {
		this.valorMensalidade = valorMensalidade;
	}	

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
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
		Estudante other = (Estudante) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}

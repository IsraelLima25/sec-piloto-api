package com.sec.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sec.api.exception.CNPJDuplicateException;
import com.sec.api.exception.RecursoNaoEncontradoException;
import com.sec.api.model.Escola;
import com.sec.api.repository.EscolaRepository;

@Service
public class EscolaService {

	@Autowired
	private EscolaRepository repositoryEscola;

	public Escola atualizarEscola(Escola escola, String cnpj) {

		Escola escolaSalva = buscarEscolaPorCnpj(cnpj);
		BeanUtils.copyProperties(escola, escolaSalva, "codigo");
		repositoryEscola.save(escolaSalva);

		return escolaSalva;
	}

	public Escola buscarEscolaPorCnpj(String cnpj) {

		Optional<Escola> optionalEscola = repositoryEscola.findByCnpj(cnpj);
		if (optionalEscola.isPresent()) {
			return optionalEscola.get();
		}

		throw new RecursoNaoEncontradoException("Nenhuma escola encontrada com este cnpj.");
	}

	public void apagarEscola(String cnpj) {
		
		Escola escola = buscarEscolaPorCnpj(cnpj);		
		repositoryEscola.delete(escola);
	}

	public Escola cadastrarEscola(Escola escola) {
		
		Optional<Escola> optionalEscola = repositoryEscola.findByCnpj(escola.getCnpj());
		
		if(optionalEscola.isPresent()) {
			throw new CNPJDuplicateException("Cnpj duplicado.");
		}
		
		Escola escolaSalva = repositoryEscola.save(escola);
		
		return escolaSalva;
	}
}

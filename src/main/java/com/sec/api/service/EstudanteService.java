package com.sec.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sec.api.business.IdadeMaiorIgualDoze;
import com.sec.api.business.IdadeMenorDoze;
import com.sec.api.dto.TransferirEstudanteDTO;
import com.sec.api.exception.EstudanteDuplicateException;
import com.sec.api.exception.RecursoNaoEncontradoException;
import com.sec.api.model.Escola;
import com.sec.api.model.Estudante;
import com.sec.api.repository.EstudanteRepository;
import com.sec.api.service.util.GeradorMatricula;

@Service
public class EstudanteService {
	
	@Autowired
	private EstudanteRepository repositoryEstudante;
	
	@Autowired
	private EscolaService serviceEscola;
	
	@Autowired
	private CalculadorMensalidade calculoMensalidade;
	
	@Autowired
	private IdadeMaiorIgualDoze idadeMaiorIgualDoze;
	
	@Autowired
	private IdadeMenorDoze idadeMenorDoze;
	
	public Estudante atualizarEstudante(Estudante estudante, String rg) {
		
		try {
			Estudante estudanteSalvo = buscarEstudantePorRg(rg);
			
			BeanUtils.copyProperties(estudante, estudanteSalvo, "codigo", "matricula" , "escolaEstudante");
			
			calcularMensalidade(estudanteSalvo);	
			
			repositoryEstudante.save(estudanteSalvo);
			
			return estudanteSalvo;		
			
		}catch (Exception e) {
			throw new EstudanteDuplicateException("Tentativa de alterar um rg para um"
					+ " já existente bloqueada.");
		}
		
	}

	public Estudante buscarEstudantePorMatricula(String matricula) {
		
		Optional<Estudante> optionalEstudante = repositoryEstudante.findByMatricula(matricula);
		
		if(optionalEstudante.isPresent()) {
			Estudante estudante = optionalEstudante.get();
			
			return estudante;
		}
		
		throw new RecursoNaoEncontradoException("Nenhum estudante encontrado com esta matrícula.");		
	}
	
	public Estudante buscarEstudantePorRg(String rg) {
		
		Optional<Estudante> optionalEstudante = repositoryEstudante.findByRg(rg);
		
		if(optionalEstudante.isPresent()) {
			Estudante estudante = optionalEstudante.get();
			
			return estudante;
		}
		
		throw new RecursoNaoEncontradoException("Nenhum estudante encontrado com este rg.");		
	}

	public void apagarEstudante(String matricula) {
		Estudante estudante = buscarEstudantePorMatricula(matricula);
		repositoryEstudante.delete(estudante);
	}

	public Estudante matricular(Estudante estudante, String cnpjEscola) {
		
		Escola escolaBusca = serviceEscola.buscarEscolaPorCnpj(cnpjEscola);
		
		Estudante estudanteMatriculaGerada = gerarMatricula(estudante, escolaBusca);		
			
		estudanteMatriculaGerada.setEscolaEstudante(escolaBusca);
		
		calcularMensalidade(estudanteMatriculaGerada);
		
		Estudante estudanteMatriculado = repositoryEstudante.save(estudanteMatriculaGerada);
		
		serviceEscola.atualizarEscola(escolaBusca, escolaBusca.getCnpj());
		
		return estudanteMatriculado;									
	}
	
	public void transferir(TransferirEstudanteDTO transferencia) {
		
		Escola escolaDestino = serviceEscola.buscarEscolaPorCnpj(transferencia.getCnpjEscolaDestino());
		
		Estudante estudante = buscarEstudantePorRg(transferencia.getRgEstudante());
		
		apagarEstudante(estudante.getMatricula());
		
		estudante.setCodigo(null);
		estudante.setMatricula(null);
		estudante.setEscolaEstudante(null);
		
		matricular(estudante, escolaDestino.getCnpj());		
	}
	
	private Estudante gerarMatricula(Estudante estudante, Escola escola) {
		
		estudanteDuplicadoEscola(estudante, escola);		
		
		estudanteMatriculadoOutraEscola(estudante); 
		
		String matriculaGerada = "";
		
		do {
			matriculaGerada = GeradorMatricula.gerarMatricula();			
		}while(matriculaDuplicadaEscola(escola, matriculaGerada));
		
		estudante.setMatricula(GeradorMatricula.gerarMatricula());
		return estudante;
	}

	private void estudanteDuplicadoEscola(Estudante estudante, Escola escola) {
		
		if(escola.getEstudantes().contains(estudante)) {
			throw new EstudanteDuplicateException("Este estudante já está "
					+ " matriculado no " + escola.getNome() + ".");
		}
	}

	private void calcularMensalidade(Estudante estudante) {
		int idadeEstudante = LocalDate.now().getYear() - estudante.getDataNascimento().getYear();
		
		if(idadeEstudante >= 12) {
			estudante.setValorMensalidade(calculoMensalidade.calcular(idadeMaiorIgualDoze));
		}else {
			estudante.setValorMensalidade(calculoMensalidade.calcular(idadeMenorDoze));
		}
	}
	
	private void estudanteMatriculadoOutraEscola(Estudante estudante) {
		
		Optional<Estudante> optionalEstudante = repositoryEstudante.findByRg(estudante.getRg());
		if(optionalEstudante.isPresent()) {
			throw new EstudanteDuplicateException("Não é permitido um estudante está "
					+ "matriculado em 2 escolas.");
		}
	}		
	
	private boolean matriculaDuplicadaEscola(Escola escola, String matricula) {
		
		List<Estudante> estudanteList = escola.getEstudantes().stream()
				.filter(estudante -> estudante.getMatricula() == matricula)
				.collect(Collectors.toList());
		
		return estudanteList.isEmpty() ? false : true;
	}
}

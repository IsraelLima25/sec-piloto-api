package com.sec.api.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sec.api.dto.EstudanteTurmaDTO;
import com.sec.api.exception.EstudanteDuplicateException;
import com.sec.api.exception.RecursoNaoEncontradoException;
import com.sec.api.model.Escola;
import com.sec.api.model.Estudante;
import com.sec.api.model.Turma;
import com.sec.api.repository.TurmaRepository;

@Service
public class TurmaService {
	
	@Autowired
	private TurmaRepository repositoryTurma;
	
	@Autowired
	private EscolaService serviceEscola;
	
	@Autowired
	private EstudanteService serviceEstudante;
	
	public Turma atualizarTurma(Turma turma, Long codigo) {
		
		Turma turmaSalva = buscarTurmaPorCodigo(codigo);	
		BeanUtils.copyProperties(turma, turmaSalva, "codigo");
		repositoryTurma.save(turmaSalva);
		
		return turmaSalva;		
	}

	public Turma buscarTurmaPorCodigo(Long codigo) {
		Optional<Turma> optionalTurma = repositoryTurma.findById(codigo);
		
		if(optionalTurma.isPresent()) {
			Turma turma = optionalTurma.get();
			return turma;
		}
		
		throw new RecursoNaoEncontradoException("Nenhuma turma encontrada com este código.");
	}
	
	public Turma cadastrarTurma(Turma turma, String cnpjEscola) {
		
		Escola escola = serviceEscola.buscarEscolaPorCnpj(cnpjEscola);
		turma.setEscolaTurma(escola);
		Turma turmaSalva = repositoryTurma.save(turma);
		
		escola.getTurmas().add(turmaSalva);
		serviceEscola.atualizarEscola(escola, escola.getCnpj());
		
		serviceEscola.atualizarEscola(escola, escola.getCnpj());
		
		return turmaSalva;		
	}

	public void apagarTurma(Long codigo) {
		
		Turma turma = buscarTurmaPorCodigo(codigo);		
		repositoryTurma.delete(turma);
	}

	public Turma adicionarEstudante(EstudanteTurmaDTO adicionarEstudanteTurmaDTO) {
				
		
		Turma turmaBusca = buscarTurmaPorCodigo(adicionarEstudanteTurmaDTO.getCodigoTurma());
		Estudante estudanteBusca = serviceEstudante
				.buscarEstudantePorMatricula(adicionarEstudanteTurmaDTO.getMatriculaEstudante());
		
		if(!estudanteMatriculadoNaEscolaPertencenteTurma(turmaBusca, estudanteBusca)) {
			throw new RecursoNaoEncontradoException("O aluno só pode ser adicionado á uma turma quando"
					+ " ele estiver matriculado na escola que possuí a turma.");
		}
		
		if(estudanteInclusoTurma(turmaBusca, estudanteBusca)) {
			throw new EstudanteDuplicateException("Conflit-Estudante");
		}
						
		turmaBusca.getEstudantesTurma().add(estudanteBusca);
		
		Turma turmaAtualizada = atualizarTurma(turmaBusca, turmaBusca.getCodigo());
		
		return turmaAtualizada;		
	}	

	public Turma removerEstudante(EstudanteTurmaDTO adicionarEstudanteTurmaDTO) {
		
		Turma turmaBusca = buscarTurmaPorCodigo(adicionarEstudanteTurmaDTO.getCodigoTurma());
		Estudante estudanteBusca = serviceEstudante
				.buscarEstudantePorMatricula(adicionarEstudanteTurmaDTO.getMatriculaEstudante());
		
		if(estudanteInclusoTurma(turmaBusca, estudanteBusca)) {
			Set<Estudante> listaEstudantesAtualizada = removerEstudanteTurma
					(turmaBusca.getEstudantesTurma(), estudanteBusca.getMatricula());
			
			turmaBusca.setEstudantesTurma(listaEstudantesAtualizada);
			
			Turma turmaAtualizada = atualizarTurma(turmaBusca, turmaBusca.getCodigo());
			
			return turmaAtualizada;
		}
		
		throw new RecursoNaoEncontradoException("Nenhum estudante econtrado nessa turma para remoção.");
	}
	
	private boolean estudanteInclusoTurma(Turma turma, Estudante estudante) {
		for(Estudante estudanteRow: turma.getEstudantesTurma()) {
			if(estudanteRow.getMatricula() == estudante.getMatricula()) {
				return true;
			}
		}		
		return false;
	}
	
	private Set<Estudante> removerEstudanteTurma(Set<Estudante> estudantes, String matriculaEstudante) {
		for(Estudante estudante : estudantes) {
			if(estudante.getMatricula() == matriculaEstudante) {
				estudantes.remove(estudante);
			}
		}		
		return estudantes;
	}
	
	private boolean estudanteMatriculadoNaEscolaPertencenteTurma(Turma turma, Estudante estudante) {
		Escola escolaTurma = turma.getEscolaTurma();
		
		if(estudante.getEscolaEstudante().getCnpj() == escolaTurma.getCnpj()) {
			return true;
		}else {
			return false;
		}
		
	}
}

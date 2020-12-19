package com.sec.api.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sec.api.dto.EstudanteTurmaDTO;
import com.sec.api.model.Turma;
import com.sec.api.repository.TurmaRepository;
import com.sec.api.service.TurmaService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/turmas")
public class TurmaResource {
	
	@Autowired
	private TurmaRepository repositoryTurma;
	
	@Autowired
	private TurmaService serviceTurma;
	
	@ApiOperation("Listar todas as turmas cadastradas")
	@GetMapping	
	public ResponseEntity<List<Turma>> listarTurmas(){
		
		List<Turma> listaTurmas = repositoryTurma.findAll();		
		return ResponseEntity.ok(listaTurmas);
	}
	
	@ApiOperation("Buscar turma cadastrada por código")
	@GetMapping("/{codigo}")
	public ResponseEntity<Turma> buscarTurmaPorCodigo(@PathVariable Long codigo){
		
		Turma turma = serviceTurma.buscarTurmaPorCodigo(codigo);	
		return ResponseEntity.ok(turma);		
	}
	
	@ApiOperation("Cadastrar turma")
	@PostMapping("/cadastrar/{cnpjEscola}")	
	public ResponseEntity<Turma> cadastrarTurma(@Valid @RequestBody Turma turma,
			@PathVariable String cnpjEscola){
		
		Turma turmaSalva = serviceTurma.cadastrarTurma(turma, cnpjEscola);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(turmaSalva.getCodigo()).toUri();
		return ResponseEntity.created(uri).body(turmaSalva);		
	}
	
	@ApiOperation("Atualizar turma")
	@PutMapping("/{codigo}")
	public ResponseEntity<Turma> atualizarTurma(@Valid @RequestBody Turma turma, @PathVariable Long codigo){
		
		Turma turmaAtualizada = serviceTurma.atualizarTurma(turma, codigo);	
		return ResponseEntity.ok(turmaAtualizada);
	}
	
	@ApiOperation("Apagar turma")
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> apagarTurma(@PathVariable Long codigo){
		serviceTurma.apagarTurma(codigo);		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation("Adicionar estudante matriculado na turma")
	@PostMapping("/adicionarEstudante")
	public ResponseEntity<Turma> adicionarEstudanteTurma(
			@Valid @RequestBody EstudanteTurmaDTO estudanteTurmaDTO){
		
		Turma turmaAtualizada = serviceTurma.adicionarEstudante(estudanteTurmaDTO);		
		
		return ResponseEntity.ok().body(turmaAtualizada);
	}
	
	@ApiOperation("Remover estudante matriculado da turma")
	@PostMapping("/removerEstudante")
	public ResponseEntity<Turma> removerEstudanteTurma(
			@Valid @RequestBody EstudanteTurmaDTO estudanteTurmaDTO){
		
		Turma turmaAtualizada = serviceTurma.removerEstudante(estudanteTurmaDTO);	
		return ResponseEntity.ok(turmaAtualizada);
	}
	
	

}

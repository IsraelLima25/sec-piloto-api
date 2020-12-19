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

import com.sec.api.dto.TransferirEstudanteDTO;
import com.sec.api.model.Estudante;
import com.sec.api.repository.EstudanteRepository;
import com.sec.api.service.EstudanteService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/estudantes")
public class EstudanteResource {
	
	@Autowired
	private EstudanteRepository repositoryEstudante;
	
	@Autowired
	private EstudanteService serviceEstudante;
	
	@ApiOperation("Listar todos estudantes cadastrados")
	@GetMapping	
	public ResponseEntity<List<Estudante>> listarEstudantes(){
		
		List<Estudante> listaEstudantes = repositoryEstudante.findAll();		
		return ResponseEntity.ok(listaEstudantes);
	}
	
	@ApiOperation("Buscar estudante cadastrado por matricula")
	@GetMapping("/buscarPorMatricula/{matricula}")
	public ResponseEntity<Estudante> buscarEstudantePorMatricula(@PathVariable String matricula){
		
		Estudante estudante = serviceEstudante.buscarEstudantePorMatricula(matricula);
		
		return ResponseEntity.ok(estudante);
	}
	
	@ApiOperation("Buscar estudante cadastrado por rg")
	@GetMapping("/buscarPorRg/{rg}")
	public ResponseEntity<Estudante> buscarEstudantePorRg(@PathVariable String rg){
		
		Estudante estudante = serviceEstudante.buscarEstudantePorRg(rg);
		
		return ResponseEntity.ok(estudante);
	}
	
	@ApiOperation("Matricular estudante")
	@PostMapping("/matricular/escola/{cnpjEscola}")
	public ResponseEntity<Estudante> matricularEstudante(@Valid @RequestBody Estudante estudante,
			@PathVariable String cnpjEscola){
		
		Estudante estudanteSalvo = serviceEstudante.matricular(estudante, cnpjEscola);		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(estudanteSalvo.getMatricula()).toUri();
		return ResponseEntity.created(uri).body(estudanteSalvo);		
	}
	
	@ApiOperation("Atualizar matricula do estudante")
	@PutMapping("/{rg}")
	public ResponseEntity<Estudante> atualizarEstudante(@Valid @RequestBody Estudante estudante,
			@PathVariable String rg){
		
		Estudante estudanteAtualizado = serviceEstudante.atualizarEstudante(estudante, rg);
		return ResponseEntity.ok(estudanteAtualizado);
	}
	
	@ApiOperation("Desmatricular estudante")
	@DeleteMapping("/{matricula}")
	public ResponseEntity<Void> apagarEstudante(@PathVariable String matricula){
		serviceEstudante.apagarEstudante(matricula);		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation("Transferir estudante de uma escola para outra")
	@PostMapping("/transferir")
	public ResponseEntity<Void> transferirEstudante(@Valid @RequestBody TransferirEstudanteDTO transferencia){
		serviceEstudante.transferir(transferencia);
		return ResponseEntity.ok().build();
	}

}

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

import com.sec.api.model.Escola;
import com.sec.api.repository.EscolaRepository;
import com.sec.api.service.EscolaService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/escolas")
public class EscolaResource {
	
	@Autowired
	private EscolaRepository repositoryEscola;
	
	@Autowired
	private EscolaService serviceEscola;
	
	@ApiOperation("Listar todas as escolas cadastradas")
	@GetMapping	
	public ResponseEntity<List<Escola>> listarEscolas(){
		
		List<Escola> listaEscolas = repositoryEscola.findAll();		
		return ResponseEntity.ok(listaEscolas);
	}
	
	@ApiOperation("Buscar escola cadastrada por cnpj")
	@GetMapping("/{cnpj}")
	public ResponseEntity<Escola> buscarEscolaPorCnpj(@PathVariable String cnpj){
		
		Escola escola = serviceEscola.buscarEscolaPorCnpj(cnpj);
		
		return ResponseEntity.ok().body(escola);
	}
	
	@ApiOperation("Cadastrar escola")
	@PostMapping
	public ResponseEntity<Escola> cadastrarEscola(@Valid @RequestBody Escola escola){
				
		Escola escolaSalva = serviceEscola.cadastrarEscola(escola);		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cnpj}")
				.buildAndExpand(escolaSalva.getCnpj()).toUri();
		return ResponseEntity.created(uri).body(escolaSalva);
	}
	
	@ApiOperation("Atualizar escola")
	@PutMapping("/{cnpj}")
	public ResponseEntity<Escola> atualizarEscola(@Valid @RequestBody Escola escola, @PathVariable String cnpj){
		
		Escola escolaAtualizada = serviceEscola.atualizarEscola(escola, cnpj);		
		return ResponseEntity.ok(escolaAtualizada);
	}
	
	@ApiOperation("Apagar escola")
	@DeleteMapping("/{cnpj}")
	public ResponseEntity<Void> apagarEscola(@PathVariable String cnpj){
		
		serviceEscola.apagarEscola(cnpj);			
		return ResponseEntity.noContent().build();
	}
}

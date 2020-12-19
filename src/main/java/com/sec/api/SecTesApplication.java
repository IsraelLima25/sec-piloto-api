package com.sec.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sec.api.model.Perfil;
import com.sec.api.model.Usuario;
import com.sec.api.repository.UsuarioRepository;

@SpringBootApplication
public class SecTesApplication implements CommandLineRunner {
	
	@Autowired
	private UsuarioRepository repositoryUsuario;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SecTesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		long quantidadeUsuarios = repositoryUsuario.count();
		
		if(quantidadeUsuarios == 0) {			
			criarUsuario();		
		}		
	}

	private void criarUsuario() {
		
		Perfil perfil = criarPerfil();
		Usuario usuario = new Usuario();
		usuario.setEmail("sec@gmail.com");
		usuario.setSenha(bCryptPasswordEncoder.encode("helloword"));
		usuario.getPerfis().add(perfil);
		
		repositoryUsuario.save(usuario);
	}
	
	private Perfil criarPerfil() {
		Perfil perfil = new Perfil();
		perfil.setDescricao("ROLE_ADMIN");
		
		
		return perfil;
	}

}

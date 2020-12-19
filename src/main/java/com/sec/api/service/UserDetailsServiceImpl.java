package com.sec.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sec.api.model.Usuario;
import com.sec.api.repository.UsuarioRepository;
import com.sec.api.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByEmail(email);

		if (usuario == null) {
			throw new UsernameNotFoundException("Email ou senha imválidos" + email);
		}

		return new UserSS(usuario.getCodigo(), usuario.getEmail(), usuario.getSenha(), usuario.getPerfis());

	}
}

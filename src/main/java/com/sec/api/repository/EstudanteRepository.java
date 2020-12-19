package com.sec.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sec.api.model.Estudante;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {
	
	Optional<Estudante> findByMatricula(String matricula);
	
	Optional<Estudante> findByRg(String rgString);	

}

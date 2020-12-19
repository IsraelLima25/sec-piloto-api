package com.sec.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sec.api.model.Escola;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, String> {
	
	Optional<Escola> findByCnpj(String cnpj);

}

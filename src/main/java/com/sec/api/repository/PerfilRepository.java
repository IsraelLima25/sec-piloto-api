package com.sec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sec.api.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}

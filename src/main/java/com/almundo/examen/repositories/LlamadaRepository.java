package com.almundo.examen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.almundo.examen.model.Llamada;

@Repository
public interface LlamadaRepository extends JpaRepository<Llamada, Long> {


}

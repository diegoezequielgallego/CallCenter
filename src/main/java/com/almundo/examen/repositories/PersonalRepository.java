package com.almundo.examen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.almundo.examen.model.Personal;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long>{


}

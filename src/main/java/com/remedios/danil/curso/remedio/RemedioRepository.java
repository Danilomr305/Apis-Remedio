package com.remedios.danil.curso.remedio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RemedioRepository extends JpaRepository<Remedio, Long>{

    List<Remedio> findAllByAtivoTrue();
}

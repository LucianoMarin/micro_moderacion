package com.microservicio.moderacionforo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.moderacionforo.models.SancionContenido;

public interface SancionContenidoRepository extends JpaRepository<SancionContenido, Long> {

    List<SancionContenido> findByTipoObjetivoAndObjetivoId(String tipoObjetivo, Long objetivoId);

    List<SancionContenido> findByActivaTrue();
}
package com.microservicio.moderacionforo.services;

import java.util.List;

import com.microservicio.moderacionforo.models.SancionContenido;

public interface ModeracionService {

    SancionContenido crearSancion(SancionContenido sancion);

    void eliminarSancion(Long id);

    SancionContenido editarSancion(Long id, SancionContenido sancion);

    List<SancionContenido> mostrarSanciones();

    SancionContenido obtenerSancion(Long id);
}
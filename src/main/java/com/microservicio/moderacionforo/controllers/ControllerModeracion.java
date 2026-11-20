package com.microservicio.moderacionforo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.moderacionforo.models.SancionContenido;
import com.microservicio.moderacionforo.services.ModeracionServiceImp;

@RestController
@RequestMapping("/moderacion")
@CrossOrigin("*")
public class ControllerModeracion {

    @Autowired
    private ModeracionServiceImp moderacionServiceImp;

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarSancion(@PathVariable Long id) {
        moderacionServiceImp.eliminarSancion(id);
        return ResponseEntity.ok().body("Id Eliminado correctamente!");
    }

    @PostMapping
    public SancionContenido crearSancion(@RequestBody SancionContenido sancion) {
        return moderacionServiceImp.crearSancion(sancion);
    }

    @GetMapping
    public List<SancionContenido> mostrarSanciones() {
        return moderacionServiceImp.mostrarSanciones();
    }

    @PutMapping("{id}")
    public SancionContenido editarSancion(@PathVariable Long id, @RequestBody SancionContenido sancion) {
        return moderacionServiceImp.editarSancion(id, sancion);
    }

    @GetMapping("{id}")
    public SancionContenido mostrarSancion(@PathVariable Long id) {
        return moderacionServiceImp.obtenerSancion(id);
    }
}
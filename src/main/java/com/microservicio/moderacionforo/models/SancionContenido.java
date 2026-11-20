package com.microservicio.moderacionforo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sancion_contenido")
@Getter
@Setter
public class SancionContenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_objetivo", nullable = false)
    @NotBlank (message = "El tipo de objetivo es obligatorio")
    private String tipoObjetivo;

    @Column(name = "objetivo_id", nullable = false)
    @NotNull(message = "El ID del objetivo es obligatorio")
    private Long objetivoId;

    @Column(name = "admin_id", nullable = false)
    @NotNull(message = "El ID del administrador es obligatorio")
    private Long adminId;

    @Column(name = "motivo", nullable = false)
    @NotBlank(message = "El motivo del baneo es obligatorio")
    private String motivo;

    @Column(name = "activa", nullable = false)
    private Boolean activa = true;

    @Column(name = "fecha_sancion", nullable = false)
    private LocalDateTime fechaSancion;

    public SancionContenido() {
    }

    public SancionContenido(Long id, String tipoObjetivo, Long objetivoId, Long adminId, String motivo, Boolean activa, LocalDateTime fechaSancion) {
        this.id = id;
        this.tipoObjetivo = tipoObjetivo;
        this.objetivoId = objetivoId;
        this.adminId = adminId;
        this.motivo = motivo;
        this.activa = activa;
        this.fechaSancion = fechaSancion;
    }

    public SancionContenido(String tipoObjetivo, Long objetivoId, Long adminId, String motivo) {
        this.tipoObjetivo = tipoObjetivo;
        this.objetivoId = objetivoId;
        this.adminId = adminId;
        this.motivo = motivo;
        this.activa = true;
        this.fechaSancion = LocalDateTime.now();
    }
}
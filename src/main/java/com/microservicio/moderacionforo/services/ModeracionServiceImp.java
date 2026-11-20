package com.microservicio.moderacionforo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.microservicio.moderacionforo.models.SancionContenido;
import com.microservicio.moderacionforo.repository.SancionContenidoRepository;

@Service
public class ModeracionServiceImp implements ModeracionService {

    @Autowired
    private SancionContenidoRepository sancionRepository;
    

    @Value("${temasforo.url:http://localhost:8082}")
    private String temasForoUrl;

    private final RestClient restClient = RestClient.create();

    @Override
    public SancionContenido crearSancion(SancionContenido sancion) {
        if (sancion.getTipoObjetivo() != null) {
            sancion.setTipoObjetivo(sancion.getTipoObjetivo().trim().toUpperCase());
        }
        if (sancion.getFechaSancion() == null) {
            sancion.setFechaSancion(LocalDateTime.now());
        }
        if (sancion.getActiva() == null) {
            sancion.setActiva(true);
        }

        SancionContenido guardada = sancionRepository.save(sancion);
        notificarEstadoEnForo(guardada.getTipoObjetivo(), guardada.getObjetivoId(), !Boolean.TRUE.equals(guardada.getActiva()));
        return guardada;
    }

    @Override
    public SancionContenido editarSancion(Long id, SancionContenido sancion) {
        SancionContenido encontrada = sancionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la sanción con ID: " + id));

        if (sancion.getTipoObjetivo() != null && !sancion.getTipoObjetivo().isBlank()) {
            encontrada.setTipoObjetivo(sancion.getTipoObjetivo().trim().toUpperCase());
        }
        if (sancion.getObjetivoId() != null) {
            encontrada.setObjetivoId(sancion.getObjetivoId());
        }
        if (sancion.getAdminId() != null) {
            encontrada.setAdminId(sancion.getAdminId());
        }
        if (sancion.getMotivo() != null && !sancion.getMotivo().isBlank()) {
            encontrada.setMotivo(sancion.getMotivo());
        }
        if (sancion.getActiva() != null) {
            encontrada.setActiva(sancion.getActiva());
            notificarEstadoEnForo(encontrada.getTipoObjetivo(), encontrada.getObjetivoId(), !encontrada.getActiva());
        }

        return sancionRepository.save(encontrada);
    }

    @Override
    public void eliminarSancion(Long id) {
        sancionRepository.deleteById(id);
    }

    @Override
    public List<SancionContenido> mostrarSanciones() {
        return sancionRepository.findAll();
    }

    @Override
    public SancionContenido obtenerSancion(Long id) {
        return sancionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la sanción con ID: " + id));
    }

    private void notificarEstadoEnForo(String tipoObjetivo, Long objetivoId, boolean estadoActivo) {
        if (tipoObjetivo == null || objetivoId == null) {
            return;
        }

        String ruta = "COMENTARIO".equals(tipoObjetivo)
                ? "/api/comentarios/" + objetivoId + "/estado"
                : "/api/publicaciones/" + objetivoId + "/estado";

        try {
            restClient.patch()
                    .uri(temasForoUrl + ruta + "?activo=" + estadoActivo)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception ex) {
            // Permite mantener el registro de moderación aunque temasforo no responda en ese momento
        }
    }
}
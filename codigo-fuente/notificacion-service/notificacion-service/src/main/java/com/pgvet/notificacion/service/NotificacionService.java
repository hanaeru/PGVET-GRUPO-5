package com.pgvet.notificacion.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import feign.FeignException;

import com.pgvet.notificacion.client.UsuarioClient;
import com.pgvet.notificacion.dto.NotificacionCreateDTO;
import com.pgvet.notificacion.dto.NotificacionDTO;
import com.pgvet.notificacion.dto.UsuarioDTO;
import com.pgvet.notificacion.model.Notificacion;
import com.pgvet.notificacion.repository.NotificacionRepository;

// Marca esta clase como servicio
@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    private final NotificacionRepository notificacionRepository;
    private final UsuarioClient usuarioClient;

    public NotificacionService(NotificacionRepository notificacionRepository,
                               UsuarioClient usuarioClient) {
        this.notificacionRepository = notificacionRepository;
        this.usuarioClient = usuarioClient;
    }

    // Convierte entidad a DTO de salida
    private NotificacionDTO convertirADTO(Notificacion notificacion) {
        return new NotificacionDTO(
                notificacion.getId(),
                notificacion.getUsuarioId(),
                notificacion.getTitulo(),
                notificacion.getMensaje(),
                notificacion.getTipo(),
                notificacion.getFechaEnvio(),
                notificacion.getLeido()
        );
    }

    // Convierte DTO de entrada a entidad
    private Notificacion convertirAEntidad(NotificacionCreateDTO dto) {
        Notificacion notificacion = new Notificacion();

        notificacion.setUsuarioId(dto.getUsuarioId());
        notificacion.setTitulo(dto.getTitulo());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setTipo(dto.getTipo());
        notificacion.setFechaEnvio(dto.getFechaEnvio());
        notificacion.setLeido(dto.getLeido());

        return notificacion;
    }

    // Valida que el usuario exista en Usuario-Service (Feign)
    private void validarUsuario(Long usuarioId) {
        log.info("Consultando usuario {} con Feign", usuarioId);
        try {
            UsuarioDTO usuario = usuarioClient.buscarPorId(usuarioId);
            if (usuario == null || usuario.getId() == null) {
                throw new RuntimeException("Usuario no encontrado");
            }
        } catch (FeignException.NotFound error) {
            log.warn("Usuario no encontrado, id={}", usuarioId);
            throw new RuntimeException("Usuario no encontrado");
        } catch (Exception error) {
            log.warn("No se pudo conectar con Usuario-Service");
            throw new RuntimeException("No se pudo conectar con Usuario-Service");
        }
    }

    // Listar todas las notificaciones
    public List<NotificacionDTO> listar() {
        return notificacionRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar notificación por ID
    public Optional<NotificacionDTO> buscarPorId(Long id) {
        return notificacionRepository.findById(id)
                .map(this::convertirADTO);
    }

    // Guardar notificación nueva
    public NotificacionDTO guardar(NotificacionCreateDTO dto) {
        log.info("Guardando registro");
        validarUsuario(dto.getUsuarioId());

        Notificacion notificacion = convertirAEntidad(dto);
        Notificacion notificacionGuardada = notificacionRepository.save(notificacion);
        return convertirADTO(notificacionGuardada);
    }

    // Actualizar notificación
    public NotificacionDTO actualizar(Long id, NotificacionCreateDTO dto) {
        log.info("Actualizando registro");

        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        validarUsuario(dto.getUsuarioId());

        notificacion.setUsuarioId(dto.getUsuarioId());
        notificacion.setTitulo(dto.getTitulo());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setTipo(dto.getTipo());
        notificacion.setFechaEnvio(dto.getFechaEnvio());
        notificacion.setLeido(dto.getLeido());

        return convertirADTO(notificacionRepository.save(notificacion));
    }

    // Eliminar notificación
    public void eliminar(Long id) {
        log.info("Eliminando registro id={}", id);

        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        notificacionRepository.delete(notificacion);
    }
}

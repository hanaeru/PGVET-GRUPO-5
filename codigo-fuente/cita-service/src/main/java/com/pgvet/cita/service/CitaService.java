package com.pgvet.cita.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import feign.FeignException;

import com.pgvet.cita.client.MascotaClient;
import com.pgvet.cita.client.UsuarioClient;
import com.pgvet.cita.dto.CitaCreateDTO;
import com.pgvet.cita.dto.CitaDTO;
import com.pgvet.cita.dto.MascotaDTO;
import com.pgvet.cita.dto.UsuarioDTO;
import com.pgvet.cita.model.Cita;
import com.pgvet.cita.repository.CitaRepository;

// Service contiene la lógica del microservicio
@Service
public class CitaService {

    private static final Logger log = LoggerFactory.getLogger(CitaService.class);

    private final CitaRepository repository;
    private final MascotaClient mascotaClient;
    private final UsuarioClient usuarioClient;

    public CitaService(CitaRepository repository, MascotaClient mascotaClient,
                       UsuarioClient usuarioClient) {
        this.repository = repository;
        this.mascotaClient = mascotaClient;
        this.usuarioClient = usuarioClient;
    }

    // Convierte entidad a DTO de salida
    private CitaDTO convertirADTO(Cita cita) {
        return new CitaDTO(
                cita.getId(),
                cita.getMascotaId(),
                cita.getTutorId(),
                cita.getVeterinarioId(),
                cita.getFecha(),
                cita.getHora(),
                cita.getMotivo(),
                cita.getEstado(),
                cita.getObservacion()
        );
    }

    // Convierte DTO de entrada a entidad
    private Cita convertirAEntidad(CitaCreateDTO dto) {
        Cita cita = new Cita();

        cita.setMascotaId(dto.getMascotaId());
        cita.setTutorId(dto.getTutorId());
        cita.setVeterinarioId(dto.getVeterinarioId());
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setMotivo(dto.getMotivo());
        cita.setObservacion(dto.getObservacion());

        if (dto.getEstado() == null || dto.getEstado().trim().isEmpty()) {
            cita.setEstado("AGENDADA");
        } else {
            String estado = dto.getEstado().toUpperCase();
            validarEstado(estado);
            cita.setEstado(estado);
        }

        return cita;
    }

    // Valida mascota, tutor y veterinario en otros microservicios
    // No permitir dos citas iguales para el mismo veterinario en misma fecha y hora
    private void validarCitaDuplicada(CitaCreateDTO dto, Long idExcluir) {
        boolean duplicada;

        if (idExcluir == null) {
            duplicada = repository.existsByVeterinarioIdAndFechaAndHora(
                    dto.getVeterinarioId(), dto.getFecha(), dto.getHora());
        } else {
            duplicada = repository.existsByVeterinarioIdAndFechaAndHoraAndIdNot(
                    dto.getVeterinarioId(), dto.getFecha(), dto.getHora(), idExcluir);
        }

        if (duplicada) {
            throw new RuntimeException("Ya existe una cita para ese veterinario en la misma fecha y hora");
        }
    }

    private void validarReferenciasExternas(CitaCreateDTO dto) {
        log.info("Validando referencias externas con Feign");
        validarMascota(dto.getMascotaId());
        validarUsuario(dto.getTutorId(), "Usuario no encontrado");
        validarUsuario(dto.getVeterinarioId(), "Veterinario no encontrado");
    }

    private void validarMascota(Long mascotaId) {
        log.info("Consultando mascota {} con Feign", mascotaId);
        try {
            MascotaDTO mascota = mascotaClient.buscarPorId(mascotaId);
            if (mascota == null || mascota.getId() == null) {
                throw new RuntimeException("Mascota no encontrada");
            }
        } catch (FeignException.NotFound error) {
            log.warn("Mascota no encontrada, id={}", mascotaId);
            throw new RuntimeException("Mascota no encontrada");
        } catch (Exception error) {
            log.warn("No se pudo conectar con Mascota-Service");
            throw new RuntimeException("No se pudo conectar con Mascota-Service");
        }
    }

    private void validarUsuario(Long usuarioId, String mensajeNoEncontrado) {
        log.info("Consultando usuario {} con Feign", usuarioId);
        try {
            UsuarioDTO usuario = usuarioClient.buscarPorId(usuarioId);
            if (usuario == null || usuario.getId() == null) {
                throw new RuntimeException(mensajeNoEncontrado);
            }
        } catch (FeignException.NotFound error) {
            log.warn("Usuario no encontrado, id={}", usuarioId);
            throw new RuntimeException(mensajeNoEncontrado);
        } catch (Exception error) {
            log.warn("No se pudo conectar con Usuario-Service");
            throw new RuntimeException("No se pudo conectar con Usuario-Service");
        }
    }

    // Guardar cita nueva
    public CitaDTO guardar(CitaCreateDTO dto) {
        log.info("Guardando cita para veterinario id={}", dto.getVeterinarioId());
        validarReferenciasExternas(dto);
        validarCitaDuplicada(dto, null);

        Cita cita = convertirAEntidad(dto);
        Cita citaGuardada = repository.save(cita);
        return convertirADTO(citaGuardada);
    }

    // Listar todas las citas
    public List<CitaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar cita por ID
    public Optional<CitaDTO> buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID debe ser válido");
        }

        return repository.findById(id)
                .map(this::convertirADTO);
    }

    // Buscar citas por mascota
    public List<CitaDTO> buscarPorMascota(Long mascotaId) {
        if (mascotaId == null || mascotaId <= 0) {
            throw new RuntimeException("El ID de la mascota debe ser válido");
        }

        return repository.findByMascotaId(mascotaId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar citas por tutor
    public List<CitaDTO> buscarPorTutor(Long tutorId) {
        if (tutorId == null || tutorId <= 0) {
            throw new RuntimeException("El ID del tutor debe ser válido");
        }

        return repository.findByTutorId(tutorId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar citas por veterinario
    public List<CitaDTO> buscarPorVeterinario(Long veterinarioId) {
        if (veterinarioId == null || veterinarioId <= 0) {
            throw new RuntimeException("El ID del veterinario debe ser válido");
        }

        return repository.findByVeterinarioId(veterinarioId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar citas por fecha
    public List<CitaDTO> buscarPorFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new RuntimeException("La fecha es obligatoria");
        }

        return repository.findByFecha(fecha)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar citas por estado
    public List<CitaDTO> buscarPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new RuntimeException("El estado es obligatorio");
        }

        String estadoMayuscula = estado.toUpperCase();
        validarEstado(estadoMayuscula);

        return repository.findByEstado(estadoMayuscula)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Actualizar cita
    public CitaDTO actualizar(Long id, CitaCreateDTO dto) {
        log.info("Actualizando cita id={}", id);

        Cita citaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        validarReferenciasExternas(dto);
        validarCitaDuplicada(dto, id);

        Cita citaNueva = convertirAEntidad(dto);

        citaExistente.setMascotaId(citaNueva.getMascotaId());
        citaExistente.setTutorId(citaNueva.getTutorId());
        citaExistente.setVeterinarioId(citaNueva.getVeterinarioId());
        citaExistente.setFecha(citaNueva.getFecha());
        citaExistente.setHora(citaNueva.getHora());
        citaExistente.setMotivo(citaNueva.getMotivo());
        citaExistente.setEstado(citaNueva.getEstado());
        citaExistente.setObservacion(citaNueva.getObservacion());

        return convertirADTO(repository.save(citaExistente));
    }

    // Eliminar cita
    public void eliminar(Long id) {
        log.info("Eliminando cita id={}", id);
        Cita cita = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        repository.delete(cita);
    }

    // Valida que el estado sea uno de los definidos
    private void validarEstado(String estado) {
        if (!estado.equals("AGENDADA")
                && !estado.equals("CONFIRMADA")
                && !estado.equals("CANCELADA")
                && !estado.equals("REALIZADA")) {
            throw new RuntimeException("Estado no válido. Use AGENDADA, CONFIRMADA, CANCELADA o REALIZADA");
        }
    }
}

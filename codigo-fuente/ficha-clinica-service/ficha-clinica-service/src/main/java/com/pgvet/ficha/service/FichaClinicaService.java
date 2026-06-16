package com.pgvet.ficha.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import feign.FeignException;

import com.pgvet.ficha.client.CitaClient;
import com.pgvet.ficha.client.MascotaClient;
import com.pgvet.ficha.client.UsuarioClient;
import com.pgvet.ficha.dto.CitaDTO;
import com.pgvet.ficha.dto.FichaClinicaCreateDTO;
import com.pgvet.ficha.dto.FichaClinicaDTO;
import com.pgvet.ficha.dto.MascotaDTO;
import com.pgvet.ficha.dto.UsuarioDTO;
import com.pgvet.ficha.model.FichaClinica;
import com.pgvet.ficha.repository.FichaClinicaRepository;

// Service contiene la lógica del microservicio
@Service
public class FichaClinicaService {

    private static final Logger log = LoggerFactory.getLogger(FichaClinicaService.class);

    private final FichaClinicaRepository repository;
    private final MascotaClient mascotaClient;
    private final UsuarioClient usuarioClient;
    private final CitaClient citaClient;

    public FichaClinicaService(FichaClinicaRepository repository, MascotaClient mascotaClient,
                               UsuarioClient usuarioClient, CitaClient citaClient) {
        this.repository = repository;
        this.mascotaClient = mascotaClient;
        this.usuarioClient = usuarioClient;
        this.citaClient = citaClient;
    }

    // Convierte entidad a DTO de salida
    private FichaClinicaDTO convertirADTO(FichaClinica ficha) {
        return new FichaClinicaDTO(
                ficha.getId(),
                ficha.getMascotaId(),
                ficha.getVeterinarioId(),
                ficha.getCitaId(),
                ficha.getFechaAtencion(),
                ficha.getSintomas(),
                ficha.getDiagnostico(),
                ficha.getTratamiento(),
                ficha.getObservaciones(),
                ficha.getPeso(),
                ficha.getTemperatura()
        );
    }

    // Convierte DTO de entrada a entidad
    private FichaClinica convertirAEntidad(FichaClinicaCreateDTO dto) {
        FichaClinica ficha = new FichaClinica();

        ficha.setMascotaId(dto.getMascotaId());
        ficha.setVeterinarioId(dto.getVeterinarioId());
        ficha.setCitaId(dto.getCitaId());
        ficha.setFechaAtencion(dto.getFechaAtencion());
        ficha.setSintomas(dto.getSintomas());
        ficha.setDiagnostico(dto.getDiagnostico());
        ficha.setTratamiento(dto.getTratamiento());
        ficha.setObservaciones(dto.getObservaciones());
        ficha.setPeso(dto.getPeso());
        ficha.setTemperatura(dto.getTemperatura());

        return ficha;
    }

    // Valida IDs externos antes de guardar o actualizar (Feign)
    private void validarReferenciasExternas(FichaClinicaCreateDTO dto) {
        log.info("Validando referencias externas con Feign");

        log.info("Consultando mascota {} con Feign", dto.getMascotaId());
        try {
            MascotaDTO mascota = mascotaClient.buscarPorId(dto.getMascotaId());
            if (mascota == null || mascota.getId() == null) {
                throw new RuntimeException("Mascota no encontrada");
            }
        } catch (FeignException.NotFound error) {
            log.warn("Mascota no encontrada");
            throw new RuntimeException("Mascota no encontrada");
        } catch (Exception error) {
            log.warn("No se pudo conectar con Mascota-Service");
            throw new RuntimeException("No se pudo conectar con Mascota-Service");
        }

        log.info("Consultando veterinario {} con Feign", dto.getVeterinarioId());
        try {
            UsuarioDTO veterinario = usuarioClient.buscarPorId(dto.getVeterinarioId());
            if (veterinario == null || veterinario.getId() == null) {
                throw new RuntimeException("Veterinario no encontrado");
            }
        } catch (FeignException.NotFound error) {
            log.warn("Veterinario no encontrado");
            throw new RuntimeException("Veterinario no encontrado");
        } catch (Exception error) {
            log.warn("No se pudo conectar con Usuario-Service");
            throw new RuntimeException("No se pudo conectar con Usuario-Service");
        }

        log.info("Consultando cita {} con Feign", dto.getCitaId());
        try {
            CitaDTO cita = citaClient.buscarPorId(dto.getCitaId());
            if (cita == null || cita.getId() == null) {
                throw new RuntimeException("Cita no encontrada");
            }
        } catch (FeignException.NotFound error) {
            log.warn("Cita no encontrada");
            throw new RuntimeException("Cita no encontrada");
        } catch (Exception error) {
            log.warn("No se pudo conectar con Cita-Service");
            throw new RuntimeException("No se pudo conectar con Cita-Service");
        }
    }

    // Guardar ficha clínica nueva
    public FichaClinicaDTO guardar(FichaClinicaCreateDTO dto) {
        log.info("Guardando registro");
        validarReferenciasExternas(dto);

        FichaClinica ficha = convertirAEntidad(dto);
        FichaClinica fichaGuardada = repository.save(ficha);
        return convertirADTO(fichaGuardada);
    }

    // Listar todas las fichas clínicas
    public List<FichaClinicaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar ficha clínica por ID
    public Optional<FichaClinicaDTO> buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID debe ser válido");
        }

        return repository.findById(id)
                .map(this::convertirADTO);
    }

    // Buscar fichas clínicas por mascota
    public List<FichaClinicaDTO> buscarPorMascota(Long mascotaId) {
        if (mascotaId == null || mascotaId <= 0) {
            throw new RuntimeException("El ID de la mascota debe ser válido");
        }

        return repository.findByMascotaId(mascotaId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar fichas clínicas por veterinario
    public List<FichaClinicaDTO> buscarPorVeterinario(Long veterinarioId) {
        if (veterinarioId == null || veterinarioId <= 0) {
            throw new RuntimeException("El ID del veterinario debe ser válido");
        }

        return repository.findByVeterinarioId(veterinarioId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar fichas clínicas por cita
    public List<FichaClinicaDTO> buscarPorCita(Long citaId) {
        if (citaId == null || citaId <= 0) {
            throw new RuntimeException("El ID de la cita debe ser válido");
        }

        return repository.findByCitaId(citaId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Actualizar ficha clínica
    public FichaClinicaDTO actualizar(Long id, FichaClinicaCreateDTO dto) {
        log.info("Actualizando registro");

        FichaClinica fichaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficha clínica no encontrada"));

        validarReferenciasExternas(dto);

        FichaClinica fichaNueva = convertirAEntidad(dto);

        fichaExistente.setMascotaId(fichaNueva.getMascotaId());
        fichaExistente.setVeterinarioId(fichaNueva.getVeterinarioId());
        fichaExistente.setCitaId(fichaNueva.getCitaId());
        fichaExistente.setFechaAtencion(fichaNueva.getFechaAtencion());
        fichaExistente.setSintomas(fichaNueva.getSintomas());
        fichaExistente.setDiagnostico(fichaNueva.getDiagnostico());
        fichaExistente.setTratamiento(fichaNueva.getTratamiento());
        fichaExistente.setObservaciones(fichaNueva.getObservaciones());
        fichaExistente.setPeso(fichaNueva.getPeso());
        fichaExistente.setTemperatura(fichaNueva.getTemperatura());

        return convertirADTO(repository.save(fichaExistente));
    }

    // Eliminar ficha clínica
    public void eliminar(Long id) {
        log.info("Eliminando registro id={}", id);
        FichaClinica ficha = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficha clínica no encontrada"));

        repository.delete(ficha);
    }
}

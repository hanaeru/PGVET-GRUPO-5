package com.pgvet.vacuna.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import feign.FeignException;

import com.pgvet.vacuna.client.MascotaClient;
import com.pgvet.vacuna.client.UsuarioClient;
import com.pgvet.vacuna.dto.MascotaDTO;
import com.pgvet.vacuna.dto.UsuarioDTO;
import com.pgvet.vacuna.dto.VacunaCreateDTO;
import com.pgvet.vacuna.dto.VacunaDTO;
import com.pgvet.vacuna.model.Vacuna;
import com.pgvet.vacuna.repository.VacunaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Marca esta clase como lógica del servicio
@Service
public class VacunaService {

    private static final Logger log = LoggerFactory.getLogger(VacunaService.class);

    private final VacunaRepository vacunaRepository;
    private final MascotaClient mascotaClient;
    private final UsuarioClient usuarioClient;

    public VacunaService(VacunaRepository vacunaRepository, MascotaClient mascotaClient,
                         UsuarioClient usuarioClient) {
        this.vacunaRepository = vacunaRepository;
        this.mascotaClient = mascotaClient;
        this.usuarioClient = usuarioClient;
    }

    // Convertir entidad a DTO de salida
    private VacunaDTO convertirADTO(Vacuna vacuna) {
        return new VacunaDTO(
                vacuna.getId(),
                vacuna.getMascotaId(),
                vacuna.getVeterinarioId(),
                vacuna.getNombreVacuna(),
                vacuna.getFechaAplicacion(),
                vacuna.getProximaDosis(),
                vacuna.getLote(),
                vacuna.getObservacion()
        );
    }

    // Convertir DTO de entrada a entidad
    private Vacuna convertirAEntidad(VacunaCreateDTO dto) {
        Vacuna vacuna = new Vacuna();

        vacuna.setMascotaId(dto.getMascotaId());
        vacuna.setVeterinarioId(dto.getVeterinarioId());
        vacuna.setNombreVacuna(dto.getNombreVacuna());
        vacuna.setFechaAplicacion(dto.getFechaAplicacion());
        vacuna.setProximaDosis(dto.getProximaDosis());
        vacuna.setLote(dto.getLote());
        vacuna.setObservacion(dto.getObservacion());

        return vacuna;
    }

    // Valida mascota y veterinario en otros microservicios (Feign)
    private void validarReferenciasExternas(VacunaCreateDTO dto) {
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
    }

    // Listar vacunas como DTO
    public List<VacunaDTO> listar() {
        return vacunaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar vacuna por ID
    public Optional<VacunaDTO> buscarPorId(Long id) {
        return vacunaRepository.findById(id)
                .map(this::convertirADTO);
    }

    // Guardar nueva vacuna
    public VacunaDTO guardar(VacunaCreateDTO dto) {
        log.info("Guardando registro");
        validarReferenciasExternas(dto);

        Vacuna vacuna = convertirAEntidad(dto);
        Vacuna vacunaGuardada = vacunaRepository.save(vacuna);

        return convertirADTO(vacunaGuardada);
    }

    // Actualizar vacuna existente
    public VacunaDTO actualizar(Long id, VacunaCreateDTO dto) {
        log.info("Actualizando registro");

        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacuna no encontrada"));

        validarReferenciasExternas(dto);

        vacuna.setMascotaId(dto.getMascotaId());
        vacuna.setVeterinarioId(dto.getVeterinarioId());
        vacuna.setNombreVacuna(dto.getNombreVacuna());
        vacuna.setFechaAplicacion(dto.getFechaAplicacion());
        vacuna.setProximaDosis(dto.getProximaDosis());
        vacuna.setLote(dto.getLote());
        vacuna.setObservacion(dto.getObservacion());

        Vacuna vacunaActualizada = vacunaRepository.save(vacuna);

        return convertirADTO(vacunaActualizada);
    }

    // Eliminar vacuna
    public void eliminar(Long id) {
        log.info("Eliminando registro id={}", id);

        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacuna no encontrada"));

        vacunaRepository.delete(vacuna);
    }
}
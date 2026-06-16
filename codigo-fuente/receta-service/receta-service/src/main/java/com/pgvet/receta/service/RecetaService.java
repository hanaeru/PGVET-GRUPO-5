package com.pgvet.receta.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import feign.FeignException;

import com.pgvet.receta.client.FichaClinicaClient;
import com.pgvet.receta.client.MascotaClient;
import com.pgvet.receta.client.UsuarioClient;
import com.pgvet.receta.dto.FichaClinicaDTO;
import com.pgvet.receta.dto.MascotaDTO;
import com.pgvet.receta.dto.RecetaCreateDTO;
import com.pgvet.receta.dto.RecetaDTO;
import com.pgvet.receta.dto.UsuarioDTO;
import com.pgvet.receta.model.Receta;
import com.pgvet.receta.repository.RecetaRepository;

// Marca esta clase como servicio
@Service
public class RecetaService {

    private static final Logger log = LoggerFactory.getLogger(RecetaService.class);

    private final RecetaRepository recetaRepository;
    private final MascotaClient mascotaClient;
    private final UsuarioClient usuarioClient;
    private final FichaClinicaClient fichaClinicaClient;

    public RecetaService(RecetaRepository recetaRepository, MascotaClient mascotaClient,
                        UsuarioClient usuarioClient, FichaClinicaClient fichaClinicaClient) {
        this.recetaRepository = recetaRepository;
        this.mascotaClient = mascotaClient;
        this.usuarioClient = usuarioClient;
        this.fichaClinicaClient = fichaClinicaClient;
    }

    // Convierte entidad a DTO de salida
    private RecetaDTO convertirADTO(Receta receta) {
        return new RecetaDTO(
                receta.getId(),
                receta.getMascotaId(),
                receta.getVeterinarioId(),
                receta.getFichaClinicaId(),
                receta.getMedicamento(),
                receta.getDosis(),
                receta.getFrecuencia(),
                receta.getDuracion(),
                receta.getIndicaciones(),
                receta.getFechaEmision()
        );
    }

    // Convierte DTO de entrada a entidad
    private Receta convertirAEntidad(RecetaCreateDTO dto) {
        Receta receta = new Receta();

        receta.setMascotaId(dto.getMascotaId());
        receta.setVeterinarioId(dto.getVeterinarioId());
        receta.setFichaClinicaId(dto.getFichaClinicaId());
        receta.setMedicamento(dto.getMedicamento());
        receta.setDosis(dto.getDosis());
        receta.setFrecuencia(dto.getFrecuencia());
        receta.setDuracion(dto.getDuracion());
        receta.setIndicaciones(dto.getIndicaciones());
        receta.setFechaEmision(dto.getFechaEmision());

        return receta;
    }

    // Valida IDs externos antes de guardar o actualizar (Feign)
    private void validarReferenciasExternas(RecetaCreateDTO dto) {
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

        log.info("Consultando ficha clínica {} con Feign", dto.getFichaClinicaId());
        try {
            FichaClinicaDTO ficha = fichaClinicaClient.buscarPorId(dto.getFichaClinicaId());
            if (ficha == null || ficha.getId() == null) {
                throw new RuntimeException("Ficha clínica no encontrada");
            }
        } catch (FeignException.NotFound error) {
            log.warn("Ficha clínica no encontrada");
            throw new RuntimeException("Ficha clínica no encontrada");
        } catch (Exception error) {
            log.warn("No se pudo conectar con Ficha-Clinica-Service");
            throw new RuntimeException("No se pudo conectar con Ficha-Clinica-Service");
        }
    }

    // Listar todas las recetas
    public List<RecetaDTO> listar() {
        return recetaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar receta por ID
    public Optional<RecetaDTO> buscarPorId(Long id) {
        return recetaRepository.findById(id)
                .map(this::convertirADTO);
    }

    // Guardar una receta nueva
    public RecetaDTO guardar(RecetaCreateDTO dto) {
        log.info("Guardando registro");
        validarReferenciasExternas(dto);

        Receta receta = convertirAEntidad(dto);
        Receta recetaGuardada = recetaRepository.save(receta);
        return convertirADTO(recetaGuardada);
    }

    // Actualizar una receta existente
    public RecetaDTO actualizar(Long id, RecetaCreateDTO dto) {
        log.info("Actualizando registro");

        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        validarReferenciasExternas(dto);

        receta.setMascotaId(dto.getMascotaId());
        receta.setVeterinarioId(dto.getVeterinarioId());
        receta.setFichaClinicaId(dto.getFichaClinicaId());
        receta.setMedicamento(dto.getMedicamento());
        receta.setDosis(dto.getDosis());
        receta.setFrecuencia(dto.getFrecuencia());
        receta.setDuracion(dto.getDuracion());
        receta.setIndicaciones(dto.getIndicaciones());
        receta.setFechaEmision(dto.getFechaEmision());

        return convertirADTO(recetaRepository.save(receta));
    }

    // Eliminar receta
    public void eliminar(Long id) {
        log.info("Eliminando registro id={}", id);

        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        recetaRepository.delete(receta);
    }
}

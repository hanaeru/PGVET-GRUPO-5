package com.pgvet.mascota.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import feign.FeignException;

import com.pgvet.mascota.client.UsuarioClient;
import com.pgvet.mascota.dto.MascotaCreateDTO;
import com.pgvet.mascota.dto.MascotaDTO;
import com.pgvet.mascota.dto.UsuarioDTO;
import com.pgvet.mascota.model.Mascota;
import com.pgvet.mascota.repository.MascotaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Lógica del Mascota-Service
@Service
public class MascotaService {

    private static final Logger log = LoggerFactory.getLogger(MascotaService.class);

    private final MascotaRepository mascotaRepository;
    private final UsuarioClient usuarioClient;

    public MascotaService(MascotaRepository mascotaRepository, UsuarioClient usuarioClient) {
        this.mascotaRepository = mascotaRepository;
        this.usuarioClient = usuarioClient;
    }

    // Convierte entidad a DTO de salida
    private MascotaDTO convertirADTO(Mascota mascota) {
        return new MascotaDTO(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getSexo(),
                mascota.getEdad(),
                mascota.getPeso(),
                mascota.getColor(),
                mascota.getMicrochip(),
                mascota.getTutorId(),
                mascota.getActivo()
        );
    }

    // Convierte DTO de entrada a entidad
    private Mascota convertirAEntidad(MascotaCreateDTO dto) {
        Mascota mascota = new Mascota();

        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        mascota.setRaza(dto.getRaza());
        mascota.setSexo(dto.getSexo());
        mascota.setEdad(dto.getEdad());
        mascota.setPeso(dto.getPeso());
        mascota.setColor(dto.getColor());
        mascota.setMicrochip(dto.getMicrochip());
        mascota.setTutorId(dto.getTutorId());
        mascota.setActivo(true);

        return mascota;
    }

    // Valida que el tutor exista en Usuario-Service antes de guardar (Feign)
    private void validarTutor(Long tutorId) {
        log.info("Consultando usuario {} con Feign", tutorId);
        try {
            UsuarioDTO usuario = usuarioClient.buscarPorId(tutorId);

            if (usuario == null || usuario.getId() == null) {
                throw new RuntimeException("Usuario no encontrado");
            }
        } catch (FeignException.NotFound error) {
            // El usuario no existe en Usuario-Service
            log.warn("Usuario no encontrado, id={}", tutorId);
            throw new RuntimeException("Usuario no encontrado");
        } catch (Exception error) {
            // Usuario-Service caído o sin red (docker stop usuario-service)
            log.warn("No se pudo conectar con Usuario-Service");
            throw new RuntimeException("No se pudo conectar con Usuario-Service");
        }
    }

    // Listar mascotas como DTO
    public List<MascotaDTO> listar() {
        return mascotaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar mascota por ID
    public Optional<MascotaDTO> buscarPorId(Long id) {
        return mascotaRepository.findById(id)
                .map(this::convertirADTO);
    }

    // Guardar mascota
    public MascotaDTO guardar(MascotaCreateDTO dto) {
        log.info("Guardando mascota {}", dto.getNombre());

        // Validar que el tutor exista en otro microservicio
        validarTutor(dto.getTutorId());

        Mascota mascota = convertirAEntidad(dto);
        Mascota mascotaGuardada = mascotaRepository.save(mascota);

        return convertirADTO(mascotaGuardada);
    }

    // Actualizar mascota
    public MascotaDTO actualizar(Long id, MascotaCreateDTO dto) {
        log.info("Actualizando mascota id={}", id);

        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        // Validar que el tutor exista en otro microservicio
        validarTutor(dto.getTutorId());

        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        mascota.setRaza(dto.getRaza());
        mascota.setSexo(dto.getSexo());
        mascota.setEdad(dto.getEdad());
        mascota.setPeso(dto.getPeso());
        mascota.setColor(dto.getColor());
        mascota.setMicrochip(dto.getMicrochip());
        mascota.setTutorId(dto.getTutorId());

        Mascota mascotaActualizada = mascotaRepository.save(mascota);

        return convertirADTO(mascotaActualizada);
    }

    // Eliminar mascota
    public void eliminar(Long id) {
        log.info("Eliminando mascota id={}", id);

        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        mascotaRepository.delete(mascota);
    }
}

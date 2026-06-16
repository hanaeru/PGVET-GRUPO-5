package com.pgvet.usuario.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pgvet.usuario.dto.UsuarioCreateDTO;
import com.pgvet.usuario.dto.UsuarioDTO;
import com.pgvet.usuario.model.Usuario;
import com.pgvet.usuario.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Lógica del Usuario-Service
@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Convierte entidad a DTO de salida
    private UsuarioDTO convertirADTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getRut(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getComuna(),
                usuario.getRegion(),
                usuario.getRol(),
                usuario.getEspecialidad(),
                usuario.getActivo()
        );
    }

    // Convierte DTO de entrada a entidad
    private Usuario convertirAEntidad(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setRut(dto.getRut());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuario.setComuna(dto.getComuna());
        usuario.setRegion(dto.getRegion());
        usuario.setRol(dto.getRol());
        usuario.setEspecialidad(dto.getEspecialidad());
        usuario.setActivo(true);

        return usuario;
    }

    // Validación simple de formato RUT chileno (ejemplo: 12345678-9)
    private void validarRut(String rut) {
        if (rut == null || !rut.matches("^[0-9]{7,8}-[0-9Kk]$")) {
            throw new RuntimeException("RUT con formato inválido. Use 12345678-9");
        }
    }

    // Listar usuarios como DTO
    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar usuario por ID
    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertirADTO);
    }

    // Guardar usuario
    public UsuarioDTO guardar(UsuarioCreateDTO dto) {
        log.info("Guardando usuario {}", dto.getCorreo());
        validarRut(dto.getRut());

        Usuario usuario = convertirAEntidad(dto);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return convertirADTO(usuarioGuardado);
    }

    // Actualizar usuario
    public UsuarioDTO actualizar(Long id, UsuarioCreateDTO dto) {
        log.info("Actualizando usuario id={}", id);
        validarRut(dto.getRut());

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setRut(dto.getRut());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuario.setComuna(dto.getComuna());
        usuario.setRegion(dto.getRegion());
        usuario.setRol(dto.getRol());
        usuario.setEspecialidad(dto.getEspecialidad());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return convertirADTO(usuarioActualizado);
    }

    // Eliminar usuario
    public void eliminar(Long id) {
        log.info("Eliminando usuario id={}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioRepository.delete(usuario);
    }
}
package com.pgvet.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.pgvet.auth.dto.UsuarioAuthCreateDTO;
import com.pgvet.auth.dto.UsuarioAuthDTO;
import com.pgvet.auth.model.UsuarioAuth;
import com.pgvet.auth.repository.UsuarioAuthRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Lógica del Auth-Service
@Service
public class UsuarioAuthService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioAuthService.class);

    private final UsuarioAuthRepository usuarioAuthRepository;

    public UsuarioAuthService(UsuarioAuthRepository usuarioAuthRepository) {
        this.usuarioAuthRepository = usuarioAuthRepository;
    }

    // Convierte entidad a DTO de salida
    private UsuarioAuthDTO convertirADTO(UsuarioAuth usuario) {
        return new UsuarioAuthDTO(
                usuario.getId(),
                usuario.getCorreo(),
                usuario.getRol(),
                usuario.getActivo(),
                usuario.getFechaCreacion()
        );
    }

    // Convierte DTO de entrada a entidad
    private UsuarioAuth convertirAEntidad(UsuarioAuthCreateDTO dto) {

        UsuarioAuth usuario = new UsuarioAuth();

        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        usuario.setActivo(true);
        usuario.setFechaCreacion(LocalDateTime.now());

        return usuario;
    }

    // Listar usuarios auth como DTO
    public List<UsuarioAuthDTO> listar() {
        return usuarioAuthRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID como DTO
    public Optional<UsuarioAuthDTO> buscarPorId(Long id) {
        return usuarioAuthRepository.findById(id)
                .map(this::convertirADTO);
    }

    // Crear usuario auth
    public UsuarioAuthDTO guardar(UsuarioAuthCreateDTO dto) {
        log.info("Guardando registro");

        UsuarioAuth usuario = convertirAEntidad(dto);

        UsuarioAuth usuarioGuardado = usuarioAuthRepository.save(usuario);

        return convertirADTO(usuarioGuardado);
    }

    // Actualizar usuario auth
    public UsuarioAuthDTO actualizar(Long id, UsuarioAuthCreateDTO dto) {
        log.info("Actualizando registro");

        UsuarioAuth usuario = usuarioAuthRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario auth no encontrado"));

        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());

        UsuarioAuth usuarioActualizado = usuarioAuthRepository.save(usuario);

        return convertirADTO(usuarioActualizado);
    }

    // Eliminar usuario auth
    public void eliminar(Long id) {
        log.info("Eliminando registro id={}", id);

        UsuarioAuth usuario = usuarioAuthRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario auth no encontrado"));

        usuarioAuthRepository.delete(usuario);
    }
}
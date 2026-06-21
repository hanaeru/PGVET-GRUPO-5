package com.pgvet.mascota.service;

import com.pgvet.mascota.client.UsuarioClient;
import com.pgvet.mascota.dto.MascotaCreateDTO;
import com.pgvet.mascota.dto.MascotaDTO;
import com.pgvet.mascota.dto.UsuarioDTO;
import com.pgvet.mascota.model.Mascota;
import com.pgvet.mascota.repository.MascotaRepository;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private MascotaService mascotaService;

    private MascotaCreateDTO crearDtoValido() {
        MascotaCreateDTO dto = new MascotaCreateDTO();
        dto.setNombre("Luna");
        dto.setEspecie("Perro");
        dto.setRaza("Labrador");
        dto.setSexo("Hembra");
        dto.setEdad(3);
        dto.setPeso(12.5);
        dto.setColor("Negro");
        dto.setMicrochip("985112004567890");
        dto.setTutorId(1L);
        return dto;
    }

    private Mascota crearMascotaEntidad() {
        Mascota mascota = new Mascota();
        mascota.setId(1L);
        mascota.setNombre("Luna");
        mascota.setEspecie("Perro");
        mascota.setRaza("Labrador");
        mascota.setSexo("Hembra");
        mascota.setEdad(3);
        mascota.setPeso(12.5);
        mascota.setColor("Negro");
        mascota.setMicrochip("985112004567890");
        mascota.setTutorId(1L);
        mascota.setActivo(true);
        return mascota;
    }

    private UsuarioDTO crearTutorValido() {
        UsuarioDTO tutor = new UsuarioDTO();
        tutor.setId(1L);
        tutor.setNombre("María");
        return tutor;
    }

    // Simula una petición Feign mínima para construir FeignException.NotFound.
    private Request peticionFeign() {
        return Request.create(
                Request.HttpMethod.GET,
                "http://usuario-service/api/v1/usuarios/1",
                Map.of(),
                null,
                new RequestTemplate());
    }

    @Test
    @DisplayName("Debe crear mascota cuando el tutor existe en Usuario-Service")
    void debeCrearMascotaCuandoTutorExiste() {
        // Given: Feign confirma que el tutor existe y el repositorio guarda la mascota.
        MascotaCreateDTO dto = crearDtoValido();
        when(usuarioClient.buscarPorId(1L)).thenReturn(crearTutorValido());
        when(mascotaRepository.save(any(Mascota.class))).thenReturn(crearMascotaEntidad());

        // When: registramos la mascota en el servicio.
        MascotaDTO resultado = mascotaService.guardar(dto);

        // Then: la mascota queda asociada al tutor correcto.
        assertNotNull(resultado);
        assertEquals("Luna", resultado.getNombre());
        assertEquals(1L, resultado.getTutorId());
        verify(usuarioClient).buscarPorId(1L);
        verify(mascotaRepository).save(any(Mascota.class));
    }

    @Test
    @DisplayName("Debe rechazar mascota cuando el tutor no existe")
    void debeRechazarMascotaCuandoTutorNoExiste() {
        // Given: Usuario-Service responde 404 para el tutor solicitado.
        MascotaCreateDTO dto = crearDtoValido();
        when(usuarioClient.buscarPorId(1L))
                .thenThrow(new FeignException.NotFound("Not Found", peticionFeign(), null, Map.of()));

        // When / Then: el servicio debe detener el guardado con mensaje claro.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> mascotaService.guardar(dto));

        assertEquals("Usuario no encontrado", error.getMessage());
        verify(mascotaRepository, never()).save(any(Mascota.class));
    }

    @Test
    @DisplayName("Debe retornar mascota cuando el ID existe")
    void debeRetornarMascotaCuandoIdExiste() {
        // Given: el repositorio encuentra la mascota.
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(crearMascotaEntidad()));

        // When: buscamos por ID.
        Optional<MascotaDTO> resultado = mascotaService.buscarPorId(1L);

        // Then: debe existir con el nombre esperado.
        assertTrue(resultado.isPresent());
        assertEquals("Luna", resultado.get().getNombre());
    }

    @Test
    @DisplayName("Debe retornar vacío cuando la mascota no existe")
    void debeRetornarVacioCuandoMascotaNoExiste() {
        // Given: no hay mascota con ese ID.
        when(mascotaRepository.findById(99L)).thenReturn(Optional.empty());

        // When: buscamos un ID inexistente.
        Optional<MascotaDTO> resultado = mascotaService.buscarPorId(99L);

        // Then: el Optional debe venir vacío.
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe listar todas las mascotas")
    void debeListarMascotas() {
        // Given: el repositorio devuelve una mascota de prueba.
        when(mascotaRepository.findAll()).thenReturn(List.of(crearMascotaEntidad()));

        // When: pedimos el listado.
        List<MascotaDTO> resultado = mascotaService.listar();

        // Then: debe haber un elemento con el nombre Luna.
        assertEquals(1, resultado.size());
        assertEquals("Luna", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debe lanzar error al eliminar mascota inexistente")
    void debeLanzarErrorAlEliminarMascotaInexistente() {
        // Given: el ID a eliminar no existe.
        when(mascotaRepository.findById(50L)).thenReturn(Optional.empty());

        // When / Then: eliminar debe fallar con el mensaje del servicio.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> mascotaService.eliminar(50L));

        assertEquals("Mascota no encontrada", error.getMessage());
    }
}

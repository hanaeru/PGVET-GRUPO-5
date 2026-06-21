package com.pgvet.cita.service;

import com.pgvet.cita.client.MascotaClient;
import com.pgvet.cita.client.UsuarioClient;
import com.pgvet.cita.dto.CitaCreateDTO;
import com.pgvet.cita.dto.CitaDTO;
import com.pgvet.cita.dto.MascotaDTO;
import com.pgvet.cita.dto.UsuarioDTO;
import com.pgvet.cita.model.Cita;
import com.pgvet.cita.repository.CitaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock
    private CitaRepository repository;

    @Mock
    private MascotaClient mascotaClient;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private CitaService citaService;

    private CitaCreateDTO crearDtoValido() {
        CitaCreateDTO dto = new CitaCreateDTO();
        dto.setMascotaId(1L);
        dto.setTutorId(2L);
        dto.setVeterinarioId(3L);
        dto.setFecha(LocalDate.of(2026, 6, 20));
        dto.setHora(LocalTime.of(10, 30));
        dto.setMotivo("Control anual");
        dto.setObservacion("Traer cartilla");
        return dto;
    }

    private Cita crearCitaEntidad() {
        Cita cita = new Cita();
        cita.setId(1L);
        cita.setMascotaId(1L);
        cita.setTutorId(2L);
        cita.setVeterinarioId(3L);
        cita.setFecha(LocalDate.of(2026, 6, 20));
        cita.setHora(LocalTime.of(10, 30));
        cita.setMotivo("Control anual");
        cita.setEstado("AGENDADA");
        cita.setObservacion("Traer cartilla");
        return cita;
    }

    private void simularReferenciasExternasValidas() {
        MascotaDTO mascota = new MascotaDTO();
        mascota.setId(1L);

        UsuarioDTO tutor = new UsuarioDTO();
        tutor.setId(2L);

        UsuarioDTO veterinario = new UsuarioDTO();
        veterinario.setId(3L);

        when(mascotaClient.buscarPorId(1L)).thenReturn(mascota);
        when(usuarioClient.buscarPorId(2L)).thenReturn(tutor);
        when(usuarioClient.buscarPorId(3L)).thenReturn(veterinario);
    }

    @Test
    @DisplayName("Debe crear cita cuando las referencias externas son válidas")
    void debeCrearCitaExitosamente() {
        // Given: mascota, tutor y veterinario existen y no hay cita duplicada.
        CitaCreateDTO dto = crearDtoValido();
        simularReferenciasExternasValidas();
        when(repository.existsByVeterinarioIdAndFechaAndHora(3L, dto.getFecha(), dto.getHora()))
                .thenReturn(false);
        when(repository.save(any(Cita.class))).thenReturn(crearCitaEntidad());

        // When: guardamos la cita.
        CitaDTO resultado = citaService.guardar(dto);

        // Then: la cita queda en estado AGENDADA por defecto.
        assertNotNull(resultado);
        assertEquals("AGENDADA", resultado.getEstado());
        assertEquals("Control anual", resultado.getMotivo());
        verify(repository).save(any(Cita.class));
    }

    @Test
    @DisplayName("Debe rechazar cita duplicada para mismo veterinario, fecha y hora")
    void debeRechazarCitaDuplicada() {
        // Given: las referencias externas son válidas pero ya existe otra cita en ese horario.
        CitaCreateDTO dto = crearDtoValido();
        simularReferenciasExternasValidas();
        when(repository.existsByVeterinarioIdAndFechaAndHora(3L, dto.getFecha(), dto.getHora()))
                .thenReturn(true);

        // When / Then: el servicio debe bloquear el guardado.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> citaService.guardar(dto));

        assertEquals("Ya existe una cita para ese veterinario en la misma fecha y hora",
                error.getMessage());
        verify(repository, never()).save(any(Cita.class));
    }

    @Test
    @DisplayName("Debe rechazar ID inválido al buscar cita")
    void debeRechazarIdInvalidoAlBuscar() {
        // Given: un ID nulo o no positivo no es aceptado por el servicio.

        // When / Then: buscarPorId debe lanzar error de validación.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> citaService.buscarPorId(0L));

        assertEquals("El ID debe ser válido", error.getMessage());
        verify(repository, never()).findById(any());
    }

    @Test
    @DisplayName("Debe retornar cita cuando el ID existe")
    void debeRetornarCitaCuandoIdExiste() {
        // Given: el repositorio encuentra la cita.
        when(repository.findById(1L)).thenReturn(Optional.of(crearCitaEntidad()));

        // When: buscamos con un ID válido.
        Optional<CitaDTO> resultado = citaService.buscarPorId(1L);

        // Then: debe existir con el motivo esperado.
        assertTrue(resultado.isPresent());
        assertEquals("Control anual", resultado.get().getMotivo());
    }

    @Test
    @DisplayName("Debe rechazar estado no permitido")
    void debeRechazarEstadoInvalido() {
        // Given: se pide filtrar por un estado que no está en la lista permitida.

        // When / Then: buscarPorEstado debe lanzar error.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> citaService.buscarPorEstado("PENDIENTE"));

        assertEquals("Estado no válido. Use AGENDADA, CONFIRMADA, CANCELADA o REALIZADA",
                error.getMessage());
    }

    @Test
    @DisplayName("Debe listar citas por estado válido")
    void debeListarCitasPorEstadoValido() {
        // Given: el repositorio devuelve citas en estado AGENDADA.
        when(repository.findByEstado("AGENDADA")).thenReturn(List.of(crearCitaEntidad()));

        // When: filtramos por estado AGENDADA.
        List<CitaDTO> resultado = citaService.buscarPorEstado("agendada");

        // Then: debe normalizar a mayúsculas y devolver resultados.
        assertEquals(1, resultado.size());
        assertEquals("AGENDADA", resultado.get(0).getEstado());
        verify(repository).findByEstado(eq("AGENDADA"));
    }

    @Test
    @DisplayName("Debe lanzar error al eliminar cita inexistente")
    void debeLanzarErrorAlEliminarCitaInexistente() {
        // Given: no existe cita con ID 50.
        when(repository.findById(50L)).thenReturn(Optional.empty());

        // When / Then: eliminar debe fallar.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> citaService.eliminar(50L));

        assertEquals("Cita no encontrada", error.getMessage());
    }
}

package com.pgvet.ficha.service;

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
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
class FichaClinicaServiceTest {

    @Mock
    private FichaClinicaRepository repository;

    @Mock
    private MascotaClient mascotaClient;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private CitaClient citaClient;

    @InjectMocks
    private FichaClinicaService fichaClinicaService;

    private FichaClinicaCreateDTO crearDtoValido() {
        FichaClinicaCreateDTO dto = new FichaClinicaCreateDTO();
        dto.setMascotaId(1L);
        dto.setVeterinarioId(3L);
        dto.setCitaId(5L);
        dto.setFechaAtencion(LocalDate.of(2026, 6, 20));
        dto.setSintomas("Decaimiento y falta de apetito");
        dto.setDiagnostico("Gastroenteritis leve");
        dto.setTratamiento("Dieta blanda por 5 días");
        dto.setObservaciones("Control en 7 días");
        dto.setPeso(12.5);
        dto.setTemperatura(38.5);
        return dto;
    }

    private FichaClinica crearFichaEntidad() {
        FichaClinica ficha = new FichaClinica();
        ficha.setId(1L);
        ficha.setMascotaId(1L);
        ficha.setVeterinarioId(3L);
        ficha.setCitaId(5L);
        ficha.setFechaAtencion(LocalDate.of(2026, 6, 20));
        ficha.setSintomas("Decaimiento y falta de apetito");
        ficha.setDiagnostico("Gastroenteritis leve");
        ficha.setTratamiento("Dieta blanda por 5 días");
        ficha.setObservaciones("Control en 7 días");
        ficha.setPeso(12.5);
        ficha.setTemperatura(38.5);
        return ficha;
    }

    private void simularReferenciasExternasValidas() {
        MascotaDTO mascota = new MascotaDTO();
        mascota.setId(1L);

        UsuarioDTO veterinario = new UsuarioDTO();
        veterinario.setId(3L);

        CitaDTO cita = new CitaDTO();
        cita.setId(5L);

        when(mascotaClient.buscarPorId(1L)).thenReturn(mascota);
        when(usuarioClient.buscarPorId(3L)).thenReturn(veterinario);
        when(citaClient.buscarPorId(5L)).thenReturn(cita);
    }

    private Request peticionFeignMascota() {
        return Request.create(
                Request.HttpMethod.GET,
                "http://mascota-service/api/v1/mascotas/1",
                Map.of(),
                null,
                new RequestTemplate());
    }

    @Test
    @DisplayName("Debe crear ficha clínica cuando mascota, veterinario y cita existen")
    void debeCrearFichaClinicaExitosamente() {
        // Given: las tres referencias externas responden OK y el repositorio guarda.
        FichaClinicaCreateDTO dto = crearDtoValido();
        simularReferenciasExternasValidas();
        when(repository.save(any(FichaClinica.class))).thenReturn(crearFichaEntidad());

        // When: guardamos la ficha clínica.
        FichaClinicaDTO resultado = fichaClinicaService.guardar(dto);

        // Then: el diagnóstico y la mascota deben coincidir.
        assertNotNull(resultado);
        assertEquals("Gastroenteritis leve", resultado.getDiagnostico());
        assertEquals(1L, resultado.getMascotaId());
        verify(repository).save(any(FichaClinica.class));
    }

    @Test
    @DisplayName("Debe rechazar ficha cuando la mascota no existe")
    void debeRechazarFichaCuandoMascotaNoExiste() {
        // Given: Mascota-Service responde 404.
        FichaClinicaCreateDTO dto = crearDtoValido();
        when(mascotaClient.buscarPorId(1L))
                .thenThrow(new FeignException.NotFound("Not Found", peticionFeignMascota(), null, Map.of()));

        // When / Then: no se debe guardar la ficha.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> fichaClinicaService.guardar(dto));

        assertEquals("Mascota no encontrada", error.getMessage());
        verify(repository, never()).save(any(FichaClinica.class));
    }

    @Test
    @DisplayName("Debe rechazar ID inválido al buscar ficha clínica")
    void debeRechazarIdInvalidoAlBuscar() {
        // Given: un ID no positivo no es aceptado.

        // When / Then: buscarPorId debe lanzar error de validación.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> fichaClinicaService.buscarPorId(-1L));

        assertEquals("El ID debe ser válido", error.getMessage());
        verify(repository, never()).findById(any());
    }

    @Test
    @DisplayName("Debe retornar ficha clínica cuando el ID existe")
    void debeRetornarFichaCuandoIdExiste() {
        // Given: el repositorio encuentra la ficha.
        when(repository.findById(1L)).thenReturn(Optional.of(crearFichaEntidad()));

        // When: buscamos con ID válido.
        Optional<FichaClinicaDTO> resultado = fichaClinicaService.buscarPorId(1L);

        // Then: debe existir con el diagnóstico esperado.
        assertTrue(resultado.isPresent());
        assertEquals("Gastroenteritis leve", resultado.get().getDiagnostico());
    }

    @Test
    @DisplayName("Debe listar fichas por mascota")
    void debeListarFichasPorMascota() {
        // Given: hay fichas asociadas a la mascota 1.
        when(repository.findByMascotaId(1L)).thenReturn(List.of(crearFichaEntidad()));

        // When: buscamos por mascota.
        List<FichaClinicaDTO> resultado = fichaClinicaService.buscarPorMascota(1L);

        // Then: debe devolver al menos una ficha con esa mascota.
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getMascotaId());
    }

    @Test
    @DisplayName("Debe lanzar error al actualizar ficha clínica inexistente")
    void debeLanzarErrorAlActualizarFichaInexistente() {
        // Given: no existe ficha con ID 50.
        when(repository.findById(50L)).thenReturn(Optional.empty());
        FichaClinicaCreateDTO dto = crearDtoValido();

        // When / Then: actualizar debe fallar antes de validar Feign.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> fichaClinicaService.actualizar(50L, dto));

        assertEquals("Ficha clínica no encontrada", error.getMessage());
    }
}

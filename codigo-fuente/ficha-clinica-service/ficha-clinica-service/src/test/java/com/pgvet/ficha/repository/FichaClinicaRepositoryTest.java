package com.pgvet.ficha.repository;

import com.pgvet.ficha.model.FichaClinica;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class FichaClinicaRepositoryTest {

    @Autowired
    private FichaClinicaRepository repository;

    private FichaClinica crearFicha(Long mascotaId) {
        FichaClinica ficha = new FichaClinica();
        ficha.setMascotaId(mascotaId);
        ficha.setVeterinarioId(3L);
        ficha.setCitaId(5L);
        ficha.setFechaAtencion(LocalDate.of(2026, 6, 20));
        ficha.setSintomas("Decaimiento");
        ficha.setDiagnostico("Gastroenteritis leve");
        ficha.setTratamiento("Dieta blanda");
        ficha.setPeso(12.5);
        ficha.setTemperatura(38.5);
        return ficha;
    }

    @Test
    @DisplayName("save debe persistir una ficha clínica")
    void saveDebePersistirFicha() {
        // Given: ficha sin ID.
        FichaClinica ficha = crearFicha(1L);

        // When: guardamos en H2.
        FichaClinica guardada = repository.save(ficha);

        // Then: recibe ID y conserva el diagnóstico.
        assertTrue(guardada.getId() > 0);
        assertEquals("Gastroenteritis leve", guardada.getDiagnostico());
    }

    @Test
    @DisplayName("findById debe recuperar la ficha guardada")
    void findByIdDebeRecuperarFicha() {
        // Given: ficha persistida.
        FichaClinica guardada = repository.save(crearFicha(2L));

        // When: buscamos por ID.
        Optional<FichaClinica> resultado = repository.findById(guardada.getId());

        // Then: existe con la mascota esperada.
        assertTrue(resultado.isPresent());
        assertEquals(2L, resultado.get().getMascotaId());
    }

    @Test
    @DisplayName("findAll debe listar fichas clínicas")
    void findAllDebeListarFichas() {
        // Given: al menos una ficha en la base.
        repository.save(crearFicha(3L));

        // When: listamos.
        List<FichaClinica> resultado = repository.findAll();

        // Then: hay registros.
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("findByMascotaId debe filtrar fichas por mascota")
    void findByMascotaIdDebeFiltrarPorMascota() {
        // Given: dos fichas de la misma mascota.
        repository.save(crearFicha(10L));
        repository.save(crearFicha(10L));

        // When: buscamos por mascotaId 10.
        List<FichaClinica> resultado = repository.findByMascotaId(10L);

        // Then: ambas pertenecen a esa mascota.
        assertEquals(2, resultado.size());
        assertEquals(10L, resultado.get(0).getMascotaId());
    }
}

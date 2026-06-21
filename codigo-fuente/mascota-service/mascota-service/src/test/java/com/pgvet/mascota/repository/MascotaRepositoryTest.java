package com.pgvet.mascota.repository;

import com.pgvet.mascota.model.Mascota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class MascotaRepositoryTest {

    @Autowired
    private MascotaRepository repository;

    private Mascota crearMascota(String nombre, Long tutorId) {
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setEspecie("Perro");
        mascota.setRaza("Labrador");
        mascota.setSexo("Hembra");
        mascota.setEdad(2);
        mascota.setPeso(10.0);
        mascota.setColor("Negro");
        mascota.setTutorId(tutorId);
        mascota.setActivo(true);
        return mascota;
    }

    @Test
    @DisplayName("save debe persistir una mascota")
    void saveDebePersistirMascota() {
        // Given: una mascota sin ID.
        Mascota mascota = crearMascota("Luna", 1L);

        // When: guardamos en H2.
        Mascota guardada = repository.save(mascota);

        // Then: recibe ID y conserva el nombre.
        assertTrue(guardada.getId() > 0);
        assertEquals("Luna", guardada.getNombre());
    }

    @Test
    @DisplayName("findById debe recuperar la mascota guardada")
    void findByIdDebeRecuperarMascota() {
        // Given: mascota persistida.
        Mascota guardada = repository.save(crearMascota("Max", 2L));

        // When: buscamos por ID.
        Optional<Mascota> resultado = repository.findById(guardada.getId());

        // Then: existe con el nombre esperado.
        assertTrue(resultado.isPresent());
        assertEquals("Max", resultado.get().getNombre());
    }

    @Test
    @DisplayName("findAll debe listar mascotas")
    void findAllDebeListarMascotas() {
        // Given: al menos una mascota en la base.
        repository.save(crearMascota("Rocky", 3L));

        // When: listamos.
        List<Mascota> resultado = repository.findAll();

        // Then: hay al menos un registro.
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("findByTutorId debe filtrar mascotas del tutor")
    void findByTutorIdDebeFiltrarPorTutor() {
        // Given: dos mascotas del mismo tutor.
        repository.save(crearMascota("Luna", 5L));
        repository.save(crearMascota("Sol", 5L));

        // When: buscamos por tutorId 5.
        List<Mascota> resultado = repository.findByTutorId(5L);

        // Then: ambas pertenecen al tutor indicado.
        assertEquals(2, resultado.size());
        assertEquals(5L, resultado.get(0).getTutorId());
    }
}

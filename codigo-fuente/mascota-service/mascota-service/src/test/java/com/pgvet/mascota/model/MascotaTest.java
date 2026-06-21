package com.pgvet.mascota.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MascotaTest {

    @Test
    @DisplayName("Constructor vacío debe dejar activo en true")
    void constructorVacioDebeInicializarActivo() {
        // Given / When: creamos la mascota con constructor vacío.
        Mascota mascota = new Mascota();

        // Then: activo queda true por defecto en la entidad.
        assertTrue(mascota.getActivo());
    }

    @Test
    @DisplayName("Setters y getters deben guardar los datos de la mascota")
    void settersYGettersDebenFuncionar() {
        // Given: una mascota recién instanciada.
        Mascota mascota = new Mascota();

        // When: asignamos los campos principales.
        mascota.setId(1L);
        mascota.setNombre("Luna");
        mascota.setEspecie("Perro");
        mascota.setRaza("Labrador");
        mascota.setSexo("Hembra");
        mascota.setEdad(3);
        mascota.setPeso(12.5);
        mascota.setTutorId(10L);

        // Then: los getters devuelven los mismos valores.
        assertEquals("Luna", mascota.getNombre());
        assertEquals("Perro", mascota.getEspecie());
        assertEquals(10L, mascota.getTutorId());
        assertEquals(3, mascota.getEdad());
    }

    @Test
    @DisplayName("Debe permitir registrar microchip opcional")
    void debePermitirMicrochip() {
        // Given: una mascota sin microchip.
        Mascota mascota = new Mascota();

        // When: asignamos el número de microchip.
        mascota.setMicrochip("985112004567890");

        // Then: el getter devuelve el chip registrado.
        assertEquals("985112004567890", mascota.getMicrochip());
    }
}

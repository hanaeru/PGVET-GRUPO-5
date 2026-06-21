package com.pgvet.ficha.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FichaClinicaTest {

    @Test
    @DisplayName("Constructor vacío debe crear instancia editable")
    void constructorVacioDebeCrearInstancia() {
        // Given / When: instanciamos la ficha clínica sin argumentos.
        FichaClinica ficha = new FichaClinica();
        ficha.setDiagnostico("Control rutinario");

        // Then: el diagnóstico queda accesible.
        assertEquals("Control rutinario", ficha.getDiagnostico());
    }

    @Test
    @DisplayName("Setters y getters deben reflejar los datos clínicos")
    void settersYGettersDebenFuncionar() {
        // Given: ficha vacía.
        FichaClinica ficha = new FichaClinica();
        LocalDate fecha = LocalDate.of(2026, 6, 20);

        // When: cargamos campos principales.
        ficha.setId(1L);
        ficha.setMascotaId(1L);
        ficha.setVeterinarioId(3L);
        ficha.setCitaId(5L);
        ficha.setFechaAtencion(fecha);
        ficha.setSintomas("Decaimiento");
        ficha.setDiagnostico("Gastroenteritis");
        ficha.setTratamiento("Dieta blanda");
        ficha.setPeso(12.5);
        ficha.setTemperatura(38.5);

        // Then: los getters devuelven los valores asignados.
        assertEquals(1L, ficha.getMascotaId());
        assertEquals("Gastroenteritis", ficha.getDiagnostico());
        assertEquals(12.5, ficha.getPeso());
        assertEquals(fecha, ficha.getFechaAtencion());
    }

    @Test
    @DisplayName("Debe permitir observaciones opcionales")
    void debePermitirObservaciones() {
        // Given: ficha sin observaciones.
        FichaClinica ficha = new FichaClinica();

        // When: agregamos observaciones.
        ficha.setObservaciones("Control en 7 días");

        // Then: el getter devuelve el texto.
        assertEquals("Control en 7 días", ficha.getObservaciones());
    }
}

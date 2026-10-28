package dev.doris.sesion12_parseo_fechas;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static dev.doris.sesion12_parseo_fechas.Fechas.parsear;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FechasTest {
    // Entrada: El texto a parsear es null
    // Esperado: "El texto no puede ser nulo."
    // Predicción: verde
    @Test
    public void parsearFechaConTextoNull() {
        assertThatThrownBy(() -> parsear(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El texto no puede ser nulo.");
    }

    // Entrada: El texto es una fecha en formato flexible "5/6/2026"
    // Esperado: 2026-06-05
    // Predicción: verde
    @Test
    public void parsearFechaCorrectamente() {
        assertThat(parsear("5/6/2026")).isEqualTo(LocalDate.of(2026, 6, 5));
    }

    // Entrada: El texto es una fecha en formato flexible y es inválida "31/6/2026"
    // Esperado: "Fecha inválida: 31/6/2026" - por IllegalArgumentException
    // Predicción: verde
    @Test
    public void parsearFechaEstrictaInvalida() {
        assertThatThrownBy(() -> parsear("31/6/2026"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Fecha inválida: 31/6/2026");
    }
}

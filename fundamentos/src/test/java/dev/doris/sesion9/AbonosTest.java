package dev.doris.sesion9;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static dev.doris.sesion9.Abonos.crear;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AbonosTest {
    // Entrada: cuenta "0001" y monto de 150.5
    // Esperado: abono1 {0001, 150.5}
    // Predicción: verde
    @Test
    public void crearUnAbonoConDatosCorrectos() {

        Abono abono1 = crear("0001", new BigDecimal("150.5"));

        assertThat(abono1).isEqualTo(new Abono("0001", new BigDecimal("150.5")));
    }

    // Entrada: cuenta null y monto 10.10
    // Esperado: Mensaje de la excepción atrapada: "La cuenta no puede ser nula."
    // Predicción: verde
    @Test
    public void crearAbonoConCuentaNull() {

        assertThatThrownBy(() -> crear(null, new BigDecimal("10.10")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cuenta no puede ser nula.");
    }

    // Entrada: cuenta "0001" y monto null
    // Esperado: Mensaje de la excepción atrapada: "El monto no puede ser nulo."
    // Predicción: verde
    @Test
    public void crearAbonoConMontoNull() {

        assertThatThrownBy(() -> crear("0001", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El monto no puede ser nulo.");
    }

    // Entrada: cuenta "0001" y monto -10.10
    // Esperado: Mensaje de la excepción atrapada: "El monto ingresado: $-10.10, no puede ser cero o negativo."
    // Predicción: verde
    @Test
    public void crearAbonoConMontoMenorACero() {

        assertThatThrownBy(() -> crear("0001", new BigDecimal("-10.10")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El monto ingresado: $-10.10, no puede ser cero o negativo.");
    }

    // Entrada: cuenta "00002" y monto 0.250
    // Esperado: Mensaje de la excepción atrapada: "La cuenta debe ser exactamente 4 caracteres. La cuenta ingresada tiene: 5"
    // Predicción: verde
    @Test
    public void crearAbonoConCuentaMayorA4Caracteres() {

        assertThatThrownBy(() -> crear("00002", new BigDecimal("0.250")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cuenta debe ser exactamente 4 caracteres. La cuenta ingresada tiene: 5");
    }

    // Entrada: cuenta "0001" y monto 0
    // Esperado: Mensaje de la excepción atrapada: "El monto ingresado: $0, no puede ser cero o negativo."
    // Predicción: verde
    @Test
    public void crearAbonoConMontoIgualACero() {

        assertThatThrownBy(() -> crear("0001", new BigDecimal("0")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El monto ingresado: $0, no puede ser cero o negativo.");
    }
}

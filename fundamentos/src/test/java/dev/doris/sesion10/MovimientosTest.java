package dev.doris.sesion10;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static dev.doris.sesion10.Movimientos.crear;
import static dev.doris.sesion10.Movimientos.totalesPorTipo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MovimientosTest {
    // Entrada: cuenta "0001" TipoMovimiento null y monto de 200.00
    // Esperado: La cuenta, el tipo de movimiento y/o el monto no pueden ser nulos.
    // Predicción: verde
    @Test
    public void crearConTipoMovimientoNull() {

        assertThatThrownBy(() -> crear("0001", null, new BigDecimal("200.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cuenta, el tipo de movimiento y/o el monto no pueden ser nulos.");
    }

    // Entrada: cuenta "000010" TipoMovimiento DEPOSITO y monto de 200.00
    // Esperado: La cuenta 000010 es inválida. No tiene exactamente 4 caracteres.
    // Predicción: verde
    @Test
    public void crearConCuentaDeSeisCaracteres() {

        assertThatThrownBy(() -> crear("000010", TipoMovimiento.DEPOSITO, new BigDecimal("200.00")))
                .isInstanceOf(CuentaInvalidaException.class)
                .hasMessage("La cuenta 000010 es inválida. No tiene exactamente 4 caracteres.");
    }

    // Entrada: cuenta "0002" TipoMovimientoDEPOSITO y monto de 0
    // Esperado: El monto 0 es inválido. El monto debe ser mayor que cero.
    // Predicción: verde
    @Test
    public void crearConMontoCero() {

        assertThatThrownBy(() -> crear("0002", TipoMovimiento.DEPOSITO, BigDecimal.ZERO))
                .isInstanceOf(MontoInvalidoException.class)
                .hasMessage("El monto 0 es inválido. El monto debe ser mayor que cero.");
    }

    // Entrada: Una lista de movimientos entre DEPOSITO y RETIRO.
    // Esperado: Para el tipo de movimiento DEPOSITO, el total es 490.90. Se valida previamente que el Map solo contenga dos tipos de movimientos
    // Predicción: verde
    @Test
    public void totalesPorTipoConDatosCorrectos() {

        Movimiento mov1 = crear("0001", TipoMovimiento.DEPOSITO, new BigDecimal("100.00"));
        Movimiento mov2 = crear("0002", TipoMovimiento.DEPOSITO, new BigDecimal("80.00"));
        Movimiento mov3 = crear("0002", TipoMovimiento.RETIRO, new BigDecimal("112.00"));
        Movimiento mov4 = crear("0001", TipoMovimiento.RETIRO, new BigDecimal("1000.50"));
        Movimiento mov5 = crear("0001", TipoMovimiento.DEPOSITO, new BigDecimal("300.00"));
        Movimiento mov6 = crear("0002", TipoMovimiento.RETIRO, new BigDecimal("150.50"));
        Movimiento mov7 = crear("0002", TipoMovimiento.DEPOSITO, new BigDecimal("10.90"));

        Map<TipoMovimiento, BigDecimal> porTipoMov = totalesPorTipo(List.of(mov1, mov2, mov3, mov4, mov5, mov6, mov7));

        assertThat(porTipoMov).hasSize(2); // Corrección de validar que dentro del map solo existan dos tipos de movimientos
        assertThat(porTipoMov.get(TipoMovimiento.DEPOSITO)).isEqualByComparingTo(new BigDecimal("490.90"));
    }
}

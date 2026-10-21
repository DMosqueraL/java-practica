package dev.doris.sesion11_java_time;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static dev.doris.sesion11_java_time.Transacciones.filtrarPorRango;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TransaccionesTest {

    // Entrada: Una lista de transacciones con 8 elementos. List<Transaccion> transacciones = List.of(trx1, trx2, trx3, trx4, trx5, trx6, trx7, trx8).
    //          Entre el rango de fechas (2026-09-30, 2026-10-06)
    // Esperado: La lista resultante (listaFiltrada) posee tres elementos, que corresponden a las transacciones trx1, trx5 y trx7
    // Predicción: verde
    @Test
    public void filtrarPorRangoCorrecto() {
        Transaccion trx1 = new Transaccion("0001", new BigDecimal("100.50"), LocalDate.of(2026, 10, 5));
        Transaccion trx2 = new Transaccion("0002", new BigDecimal("40.00"), LocalDate.of(2026, 10, 12));
        Transaccion trx3 = new Transaccion("0001", new BigDecimal("250.00"), LocalDate.of(2026, 10, 12));
        Transaccion trx4 = new Transaccion("0003", new BigDecimal("15.75"), LocalDate.of(2026, 10, 19));
        Transaccion trx5 = new Transaccion("0002", new BigDecimal("600.00"), LocalDate.of(2026, 10, 1));
        Transaccion trx6 = new Transaccion("0001", new BigDecimal("80.25"), LocalDate.of(2026, 10, 23));
        Transaccion trx7 = new Transaccion("0003", new BigDecimal("120.00"), LocalDate.of(2026, 10, 5));
        Transaccion trx8 = new Transaccion("0002", new BigDecimal("33.10"), LocalDate.of(2026, 11, 2));

        List<Transaccion> transacciones = List.of(trx1, trx2, trx3, trx4, trx5, trx6, trx7, trx8);

        List<Transaccion> listaFiltrada = filtrarPorRango(transacciones, LocalDate.of(2026, 9, 30), LocalDate.of(2026, 10, 6));

        assertThat(listaFiltrada).hasSize(3);
        assertThat(listaFiltrada).containsExactly(trx1, trx5, trx7);
    }

    // Entrada: Una lista de transacciones con 4 elementos. List<Transaccion> transacciones = List.of(trx1, trx2, trx3, trx4).
    //          Entre el rango de fechas (2026-11-30, 2026-10-12)
    // Esperado: Se captura la IllegalArgumentException porque la fecha "desde" es superior a la fecha "hasta" (2026-11-30 > 2026-10-12)
    // Predicción: verde
    @Test
    public void filtrarPorRangoIncorrectoLanzandoExcepcion() {
        Transaccion trx1 = new Transaccion("0001", new BigDecimal("100.50"), LocalDate.of(2026, 10, 5));
        Transaccion trx2 = new Transaccion("0002", new BigDecimal("40.00"), LocalDate.of(2026, 10, 12));
        Transaccion trx3 = new Transaccion("0001", new BigDecimal("250.00"), LocalDate.of(2026, 10, 12));
        Transaccion trx4 = new Transaccion("0003", new BigDecimal("15.75"), LocalDate.of(2026, 10, 19));


        List<Transaccion> transacciones = List.of(trx1, trx2, trx3, trx4);

        assertThatThrownBy(() -> filtrarPorRango(transacciones, LocalDate.of(2026, 11, 30), LocalDate.of(2026, 10, 12)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El rango inferior de las fechas no puede ser mayor al rango superior.");

    }

    // Entrada: Una lista de transacciones con 4 elementos. List<Transaccion> transacciones = List.of(trx1, trx2, trx3, trx4).
    //          Con fecha "hasta" igual a una de las fechas de la transacción de la lista, la trx1, "hasta": 2026-10-05.
    //          Entre el rango de fechas (2026-09-30, 2026-10-05)
    // Esperado: La lista resultante (listaFiltrada) posee un solo elemento, que corresponden a las transacciones trx1, trx5 y trx7
    // Predicción: verde
    @Test
    public void filtrarPorRangoQueIncluyaUnoDeLosExtremos() {
        Transaccion trx1 = new Transaccion("0001", new BigDecimal("100.50"), LocalDate.of(2026, 10, 5));
        Transaccion trx2 = new Transaccion("0002", new BigDecimal("40.00"), LocalDate.of(2026, 10, 12));
        Transaccion trx3 = new Transaccion("0001", new BigDecimal("250.00"), LocalDate.of(2026, 10, 12));
        Transaccion trx4 = new Transaccion("0003", new BigDecimal("15.75"), LocalDate.of(2026, 10, 19));

        List<Transaccion> transacciones = List.of(trx1, trx2, trx3, trx4);

        List<Transaccion> listaFiltrada = filtrarPorRango(transacciones, LocalDate.of(2026, 9, 30), LocalDate.of(2026, 10, 5));

        assertThat(listaFiltrada).hasSize(1);
        assertThat(listaFiltrada).containsExactly(trx1);
    }
}

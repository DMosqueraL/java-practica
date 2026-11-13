package dev.doris.app_transacciones;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculoTransaccionesTest {
    // Entrada: List.of()
    // Esperado: 2 claves; DEBITO = 0 y CREDITO = 0
    // Predicción: verde
    @Test
    public void totalesPorTipoConListaVacia() {
        Map<TipoTransaccion, BigDecimal> totales = CalculoTransacciones.totalesPorTipo(List.of());

        assertThat(totales).hasSize(2);
        assertThat(totales.get(TipoTransaccion.DEBITO)).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(totales.get(TipoTransaccion.CREDITO)).isEqualByComparingTo(BigDecimal.ZERO);
    }

    // Entrada: CREDITO = 100.00 (Cuenta 0001), DEBITO = 20.50 (Cuenta 0002), DEBITO = 10.25 (Cuenta 0001) y CREDITO = 50.00 (Cuenta 0003)
    // Esperado: 2 claves; DEBITOo = 30.75 y CREDITO = 150.00
    // Predicción: verde
    @Test
    public void totalPorTipoConDebitosYCreditos() {
        Transaccion trx1 = new Transaccion(LocalDate.of(2026, 6, 1), "0001", TipoTransaccion.CREDITO, new BigDecimal("100.00"));
        Transaccion trx2 = new Transaccion(LocalDate.of(2026, 6, 2), "0002", TipoTransaccion.DEBITO, new BigDecimal("20.50"));
        Transaccion trx3 = new Transaccion(LocalDate.of(2026, 6, 3), "0001", TipoTransaccion.DEBITO, new BigDecimal("10.25"));
        Transaccion trx4 = new Transaccion(LocalDate.of(2026, 6, 4), "0003", TipoTransaccion.CREDITO, new BigDecimal("50.00"));

        Map<TipoTransaccion, BigDecimal> totalPorTipo = CalculoTransacciones.totalesPorTipo(List.of(trx1, trx2, trx3, trx4));

        assertThat(totalPorTipo).hasSize(2);
        assertThat(totalPorTipo.get(TipoTransaccion.DEBITO)).isEqualByComparingTo(new BigDecimal("30.75"));
        assertThat(totalPorTipo.get(TipoTransaccion.CREDITO)).isEqualByComparingTo(new BigDecimal("150.00"));
    }

    // Entrada: CREDITO = 100.00 (Cuenta 0001), DEBITO = 20.50 (Cuenta 0002), DEBITO = 10.25 (Cuenta 0001) y CREDITO = 50.00 (Cuenta 0003)
    // Esperado: 3 claves. 0001=89.75, 0002=-20.50, 0003=50.00
    // Predicción: verde
    @Test
    public void saldoPorCuentaConCreditosyDebitos() {
        List<Transaccion> transacciones = List.of(
                new Transaccion(LocalDate.of(2026, 6, 1), "0001", TipoTransaccion.CREDITO, new BigDecimal("100.00")),
                new Transaccion(LocalDate.of(2026, 6, 2), "0002", TipoTransaccion.DEBITO, new BigDecimal("20.50")),
                new Transaccion(LocalDate.of(2026, 6, 3), "0001", TipoTransaccion.DEBITO, new BigDecimal("10.25")),
                new Transaccion(LocalDate.of(2026, 6, 4), "0003", TipoTransaccion.CREDITO, new BigDecimal("50.00")));

        Map<String, BigDecimal> saldoPorCuenta = CalculoTransacciones.saldoPorCuenta(transacciones);

        assertThat(saldoPorCuenta).hasSize(3);
        assertThat(saldoPorCuenta.get("0001")).isEqualByComparingTo(new BigDecimal("89.75"));
        assertThat(saldoPorCuenta.get("0002")).isEqualByComparingTo(new BigDecimal("-20.50"));
        assertThat(saldoPorCuenta.get("0003")).isEqualByComparingTo(new BigDecimal("50.00"));
    }
}

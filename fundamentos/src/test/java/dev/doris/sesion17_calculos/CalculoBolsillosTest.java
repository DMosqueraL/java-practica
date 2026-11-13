package dev.doris.sesion17_calculos;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculoBolsillosTest {

    // Entrada: INGRESO 100.50 (Ahorro), INGRESO 50.25 (Viajes) y GASTO 30.25 (Mercado)
    // Esperado: 2 claves; INGRESO = 150.75 y GASTO = 30.25
    // Predicción: verde
    @Test
    public void totalesPorTipoConIngresosYGastos() {
        List<Transaccion> movimientos = List.of(
                new Transaccion("Ahorro", TipoMovimiento.INGRESO, new BigDecimal("100.50")),
                new Transaccion("Viajes", TipoMovimiento.INGRESO, new BigDecimal("50.25")),
                new Transaccion("Mercado", TipoMovimiento.GASTO, new BigDecimal("30.25")));

        Map<TipoMovimiento, BigDecimal> totales = CalculoBolsillos.totalesPorTipo(movimientos);

        assertThat(totales).hasSize(2);
        assertThat(totales.get(TipoMovimiento.INGRESO)).isEqualByComparingTo(new BigDecimal("150.75"));
        assertThat(totales.get(TipoMovimiento.GASTO)).isEqualByComparingTo(new BigDecimal("30.25"));
    }

    // Entrada: List.of()
    // Esperado: 2 claves; INGRESO = 0 y GASTO = 0
    // Predicción: verde
    @Test
    public void totalesPorTipoConListaVacia() {
        Map<TipoMovimiento, BigDecimal> totales = CalculoBolsillos.totalesPorTipo(List.of());

        assertThat(totales).hasSize(2);
        assertThat(totales.get(TipoMovimiento.INGRESO)).isEqualByComparingTo(new BigDecimal("0.00"));
        assertThat(totales.get(TipoMovimiento.GASTO)).isEqualByComparingTo(new BigDecimal("0.00"));
    }

    // Entrada: INGRESO 200.00 (Ahorro), GASTO 50.25 (Ahorro) y GASTO 30.25 (Mercado)
    // Esperado: 2 claves; Ahorro = 149.75 y Mercado = -30.25
    // Predicción: verde
    @Test
    public void saldoPorBolsilloSumaIngresosYRestaGastos() {
        List<Transaccion> movimientos = List.of(
                new Transaccion("Ahorro", TipoMovimiento.INGRESO, new BigDecimal("200.00")),
                new Transaccion("Ahorro", TipoMovimiento.GASTO, new BigDecimal("50.25")),
                new Transaccion("Mercado", TipoMovimiento.GASTO, new BigDecimal("30.25")));

        Map<String, BigDecimal> saldos = CalculoBolsillos.saldoPorBolsillo(movimientos);

        assertThat(saldos).hasSize(2);
        assertThat(saldos.get("Ahorro")).isEqualByComparingTo(new BigDecimal("149.75"));
        assertThat(saldos.get("Mercado")).isEqualByComparingTo(new BigDecimal("-30.25"));
    }
}

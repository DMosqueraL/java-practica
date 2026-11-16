package dev.doris.app_transacciones;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ReporteTest {
    // Entrada: resultado de tipo ResultadoCarga, formado por dos listas: List<Transaccion> validas = List.of(t1, t2) y List<String> reporte = List.of("Línea 3: Monto inválido: abc", "Línea 5: fecha inválida: 31/6/2026", "Línea 7: columnas: 3")
    // Esperado:
    // Válidas: 4
    // Inválidas: 2
    // Línea 3: Monto inválido: abc
    // Línea 5: fecha inválida: 31/6/2026
    // Totales por tipo:
    // DEBITO         30.75
    // CREDITO       150.00
    // Saldos por cuenta:
    // 0001           89.75
    // 0002          -20.50
    // 0003           50.000
    // Predicción: verde
    @Test
    public void reporteExitosoConValidasEInvalidas() {
        List<Transaccion> validas = List.of(
                new Transaccion(LocalDate.of(2026, 6, 1), "0001", TipoTransaccion.CREDITO, new BigDecimal("100.00")),
                new Transaccion(LocalDate.of(2026, 6, 2), "0002", TipoTransaccion.DEBITO, new BigDecimal("20.50")),
                new Transaccion(LocalDate.of(2026, 6, 3), "0001", TipoTransaccion.DEBITO, new BigDecimal("10.25")),
                new Transaccion(LocalDate.of(2026, 6, 4), "0003", TipoTransaccion.CREDITO, new BigDecimal("50.00")));
        List<String> reporte = List.of(
                "Línea 3: Monto inválido: abc",
                "Línea 5: fecha inválida: 31/6/2026");

        Map<TipoTransaccion, BigDecimal> totales = CalculoTransacciones.totalesPorTipo(validas);
        Map<String, BigDecimal> saldos = CalculoTransacciones.saldoPorCuenta(validas);
        ResultadoCarga resultado = new ResultadoCarga(validas, reporte);

        assertThat(Reporte.armar(resultado, totales, saldos)).isEqualTo(
                "Válidas: 4" + "\n" + "Inválidas: 2" + "\n" +
                        "Línea 3: Monto inválido: abc" + "\n" + "Línea 5: fecha inválida: 31/6/2026" + "\n" +
                        "Totales por tipo:" + "\n" + String.format("%-8s%12s", "DEBITO", new BigDecimal("30.75")) + "\n" +
                        String.format("%-8s%12s", "CREDITO", new BigDecimal("150.00")) + "\n" + "Saldos por cuenta:" + "\n" +
                        String.format("%-8s%12s", "0001", new BigDecimal("89.75")) + "\n" +
                        String.format("%-8s%12s", "0002", new BigDecimal("-20.50")) + "\n" +
                        String.format("%-8s%12s", "0003", new BigDecimal("50.00"))
        );
    }

    // Entrada: resultado de tipo ResultadoCarga, formado por dos listas: List<Transaccion> validas = List.of(t1, t2) y List<String> reporte = List.of()
    // Esperado:
    // Válidas: 2
    // Inválidas: 0
    // Totales por tipo:
    // DEBITO         20.50
    // CREDITO       100.00
    // Saldos por cuenta:
    // 0001          100.00
    // 0002          -20.50
    // Predicción: verde
    @Test
    public void reporteSinInvalidas() {
        Transaccion t1 = new Transaccion(LocalDate.of(2026, 6, 5), "0002", TipoTransaccion.DEBITO, new BigDecimal("20.50"));
        Transaccion t2 = new Transaccion(LocalDate.of(2026, 6, 6), "0001", TipoTransaccion.CREDITO, new BigDecimal("100.00"));
        List<Transaccion> validas = List.of(t1, t2);
        List<String> reporte = List.of();
        Map<TipoTransaccion, BigDecimal> totales = CalculoTransacciones.totalesPorTipo(validas);
        Map<String, BigDecimal> saldos = CalculoTransacciones.saldoPorCuenta(validas);
        ResultadoCarga resultado = new ResultadoCarga(validas, reporte);

        assertThat(Reporte.armar(resultado, totales, saldos)).isEqualTo("Válidas: 2" + "\n" + "Inválidas: 0" + "\n" +
                "Totales por tipo:" + "\n" + String.format("%-8s%12s", "DEBITO", new BigDecimal("20.50")) + "\n" +
                String.format("%-8s%12s", "CREDITO", new BigDecimal("100.00")) + "\n" + "Saldos por cuenta:" + "\n" +
                String.format("%-8s%12s", "0001", new BigDecimal("100.00")) + "\n" +
                String.format("%-8s%12s", "0002", new BigDecimal("-20.50")));
    }

    // Entrada: resultado de tipo ResultadoCarga, formado por dos listas vacias List<Transaccion> validas = List.of() y List<String> reporte = List.of()
    // Esperado: Válidas: 0
    // Inválidas: 0
    // Totales por tipo:
    // DEBITO          0.00
    // CREDITO         0.00
    // Saldos por cuenta:
    // Predicción: verde
    @Test
    public void reporteConDatosVacios() {
        List<Transaccion> validas = List.of();
        List<String> reporte = List.of();
        Map<TipoTransaccion, BigDecimal> totales = CalculoTransacciones.totalesPorTipo(validas);
        Map<String, BigDecimal> saldos = CalculoTransacciones.saldoPorCuenta(validas);
        ResultadoCarga resultado = new ResultadoCarga(validas, reporte);

        assertThat(Reporte.armar(resultado, totales, saldos)).isEqualTo("Válidas: 0" + "\n" + "Inválidas: 0" + "\n" +
                "Totales por tipo:" + "\n" + String.format("%-8s%12s", "DEBITO", new BigDecimal("0.00")) + "\n" +
                String.format("%-8s%12s", "CREDITO", new BigDecimal("0.00")) + "\n" + "Saldos por cuenta:");
    }


}

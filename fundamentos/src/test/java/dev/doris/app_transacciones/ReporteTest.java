package dev.doris.app_transacciones;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReporteTest {
    // Entrada: resultado de tipo ResultadoCarga, formado por dos listas: List<Transaccion> validas = List.of(t1, t2) y List<String> reporte = List.of("Línea 3: Monto inválido: abc", "Línea 5: fecha inválida: 31/6/2026", "Línea 7: columnas: 3")
    // Esperado: Válidas: 2
    //           Inválidas: 3
    //           Línea 3: Monto inválido: abc
    //           Línea 5: fecha inválida: 31/6/2026
    //           Línea 7: columnas: 3
    // Predicción: verde
    @Test
    public void reporteExitosoConValidasEInvalidas() {
        Transaccion t1 = new Transaccion(LocalDate.of(2026, 6, 5), "0001", TipoTransaccion.DEBITO, new BigDecimal("10.50"));
        Transaccion t2 = new Transaccion(LocalDate.of(2026, 6, 6), "0002", TipoTransaccion.CREDITO, new BigDecimal("80.00"));
        List<Transaccion> validas = List.of(t1, t2);
        List<String> reporte = List.of(
                "Línea 3: Monto inválido: abc",
                "Línea 5: fecha inválida: 31/6/2026",
                "Línea 7: columnas: 3");
        ResultadoCarga resultado = new ResultadoCarga(validas, reporte);

        assertThat(Reporte.armar(resultado)).isEqualTo("Válidas: 2" + "\n" + "Inválidas: 3" + "\n" +
                "Línea 3: Monto inválido: abc" + "\n" + "Línea 5: fecha inválida: 31/6/2026" + "\n" +
                "Línea 7: columnas: 3");
    }

    // Entrada: resultado de tipo ResultadoCarga, formado por dos listas: List<Transaccion> validas = List.of(t1, t2) y List<String> reporte = List.of()
    // Esperado: Válidas: 2
    //           Inválidas: 0
    // Predicción: verde
    @Test
    public void reporteSinInvalidas() {
        Transaccion t1 = new Transaccion(LocalDate.of(2026, 6, 5), "0001", TipoTransaccion.DEBITO, new BigDecimal("10.50"));
        Transaccion t2 = new Transaccion(LocalDate.of(2026, 6, 6), "0002", TipoTransaccion.CREDITO, new BigDecimal("80.00"));
        List<Transaccion> validas = List.of(t1, t2);
        List<String> reporte = List.of();
        ResultadoCarga resultado = new ResultadoCarga(validas, reporte);

        assertThat(Reporte.armar(resultado)).isEqualTo("Válidas: 2" + "\n" + "Inválidas: 0");
    }

    // Entrada: resultado de tipo ResultadoCarga, formado por dos listas vacias List<Transaccion> validas = List.of() y List<String> reporte = List.of()
    // Esperado: Válidas: 0
    //           Inválidas: 0
    // Predicción: verde
    @Test
    public void reporteConDatosVacios() {
        List<Transaccion> validas = List.of();
        List<String> reporte = List.of();

        ResultadoCarga resultado = new ResultadoCarga(validas, reporte);

        assertThat(Reporte.armar(resultado)).isEqualTo("Válidas: 0" + "\n" + "Inválidas: 0");
    }

}

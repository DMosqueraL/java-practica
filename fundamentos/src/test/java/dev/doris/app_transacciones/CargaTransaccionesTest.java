package dev.doris.app_transacciones;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;

import static dev.doris.app_transacciones.CargaTransacciones.cargar;
import static org.assertj.core.api.Assertions.assertThat;

public class CargaTransaccionesTest {
    // Entrada: La ruta del archivo todo-valido.csv "src/test/resources/sesion15/todo-valido.csv"
    // Esperado: 4 Transacciones que son las que se encuentran en el archivo y reporte vacío
    // Predicción: verde
    @Test
    public void cargarTransaccionesTodasValidas() throws IOException {
        Path ruta = Path.of("src/test/resources/sesion15/todo-valido.csv");
        ResultadoCarga resultado = cargar(ruta);
        assertThat(resultado.validas()).hasSize(4);
        assertThat(resultado.reporte()).isEmpty();
    }

    // Entrada: La ruta del archivo mezcladas.csv "src/test/resources/sesion15/mezcladas.csv"
    // Esperado:
    // [Transaccion[fecha=01/08/2026, cuenta=0001, tipo=DEBITO, monto=75.00],
    // Transaccion[fecha=03/08/2026, cuenta=0003, tipo=CREDITO, monto=300.26],
    // Transaccion[06/08/2026, 0002, CREDITO, 40.00]]
    // [Línea 3: Monto inválido: 1o.00, Línea 5: tipo inválido: TRANSFERENCIA, Línea 6: fecha inválida: 30/2/2026, Línea 7: columnas: 5]
    // Predicción: verde
    @Test
    public void cargarTransaccionesMezcladas() throws IOException {
        Path ruta = Path.of("src/test/resources/sesion15/mezcladas.csv");
        ResultadoCarga resultado = cargar(ruta);

        assertThat(resultado.validas()).containsExactly(
                new Transaccion(LocalDate.of(2026, 8, 1), "0001", TipoTransaccion.DEBITO, new BigDecimal("75.00")),
                new Transaccion(LocalDate.of(2026, 8, 3), "0003", TipoTransaccion.CREDITO, new BigDecimal("300.26")),
                new Transaccion(LocalDate.of(2026, 8, 6), "0002", TipoTransaccion.CREDITO, new BigDecimal("40.00"))
        );
        assertThat(resultado.reporte()).containsExactly(
                "Línea 3: Monto inválido: 1o.00",
                "Línea 5: tipo inválido: TRANSFERENCIA",
                "Línea 6: fecha inválida: 30/2/2026",
                "Línea 7: columnas: 5"
        );
    }

    // Entrada: La ruta del archivo solo-encabezado.csv "src/test/resources/sesion15/solo-encabezado.csv"
    // Esperado: [] y []
    // Predicción: verde
    @Test
    public void cargarTransaccionesSoloEncabezado() throws IOException {
        Path ruta = Path.of("src/test/resources/sesion15/solo-encabezado.csv");
        ResultadoCarga resultado = cargar(ruta);

        assertThat(resultado.validas()).isEmpty();
        assertThat(resultado.reporte()).isEmpty();

    }
}

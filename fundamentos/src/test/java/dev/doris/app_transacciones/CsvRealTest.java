package dev.doris.app_transacciones;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CsvRealTest {
    // Entrada: Ruta del archivo transacciones.csv: "src/main/resources/transacciones.csv"
    // Esperado:
    // Válidas: 10
    // Inválidas: 5
    // Línea 7: Monto inválido: abc
    // Línea 9: fecha inválida: 31/6/2026
    // Línea 10: tipo inválido: RETIRO
    // Línea 12: columnas: 3
    // Línea 14: columnas: 6
    // Totales por tipo:
    // DEBITO        337.70
    // CREDITO      2920.74
    // Saldos por cuenta:
    // 0001         1343.99
    // 0002          764.20
    // 0003          474.85
    // Predicción: verde
    @Test
    public void reporteConArchivoDeValidasEInvalidas() throws IOException {
        Path archivo = Path.of("src/main/resources/transacciones.csv");

        ResultadoCarga resultado = CargaTransacciones.cargar(archivo);
        Map<TipoTransaccion, BigDecimal> totales = CalculoTransacciones.totalesPorTipo(resultado.validas());
        Map<String, BigDecimal> saldos = CalculoTransacciones.saldoPorCuenta(resultado.validas());
        String reporte = Reporte.armar(resultado, totales, saldos);
        System.out.println(reporte);

        assertThat(reporte).isEqualTo("Válidas: 10\n" +
                "Inválidas: 5\n" +
                "Línea 7: Monto inválido: abc\n" +
                "Línea 9: fecha inválida: 31/6/2026\n" +
                "Línea 10: tipo inválido: RETIRO\n" +
                "Línea 12: columnas: 3\n" +
                "Línea 14: columnas: 6\n" +
                "Totales por tipo:\n" +
                "DEBITO        337.70\n" +
                "CREDITO      2920.74\n" +
                "Saldos por cuenta:\n" +
                "0001         1343.99\n" +
                "0002          764.20\n" +
                "0003          474.85");
    }

    // Entrada: Ruta de un archivo que no existe: "src/main/resources/no-existe.csv"
    // Esperado: NoSuchFileException
    // Predicción: verde
    @Test
    public void reporteConArchivoQueNoExiste() {
        Path archivo = Path.of("src/main/resources/no-existe.csv");

        assertThatThrownBy(() -> CargaTransacciones.cargar(archivo)).isInstanceOf(NoSuchFileException.class);

    }

    // Entrada: Ruta del archivo transacciones.csv: "src/main/resources/transacciones.csv"
    // Esperado: {0001=1343.99, 0002=764.20, 0003=474.85}
    // Predicción: verde
    @Test
    public void reporteDeSoloSaldoPorCuentaOrdenCorrecto() throws IOException {
        Path archivo = Path.of("src/main/resources/transacciones.csv");

        ResultadoCarga resultado = CargaTransacciones.cargar(archivo);
        Map<String, BigDecimal> saldos = CalculoTransacciones.saldoPorCuenta(resultado.validas());
        System.out.println(saldos);

        assertThat(saldos).hasSize(3);
        assertThat(saldos.keySet()).containsExactly("0001", "0002", "0003");
        assertThat(saldos.get("0001")).isEqualByComparingTo(new BigDecimal("1343.99"));
        assertThat(saldos.get("0002")).isEqualByComparingTo(new BigDecimal("764.20"));
        assertThat(saldos.get("0003")).isEqualByComparingTo(new BigDecimal("474.85"));
    }
}

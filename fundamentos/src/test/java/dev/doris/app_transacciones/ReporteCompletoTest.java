package dev.doris.app_transacciones;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ReporteCompletoTest {
    // Entrada: Un archivo con encabezado y seis lineas de transacciones entre válidas e inválidas: fecha,cuenta,tipo,monto, 5/6/2026,0002,CREDITO,200.00, 6/6/2026,0001,DEBITO,50.125, 7/6/2026,0003,RETIRO,10.00,
    //          8/6/2026,0001,CREDITO,80.50, 31/6/2026,0002,DEBITO,5.00, 9/6/2026,0002,DEBITO,30.00,,
    // Esperado:
    // Válidas: 3
    // Inválidas: 3
    // Línea 4: tipo inválido: RETIRO
    // Línea 6: fecha inválida: 31/6/2026
    // Línea 7: columnas: 6
    // Totales por tipo:
    // DEBITO         50.12
    // CREDITO       280.50
    // Saldos por cuenta:
    // 0001           30.38
    // 0002          200.00
    // Predicción: verde
    @Test
    public void reporteConValidasEInvalidas(@TempDir Path carpeta) throws IOException {
        Path archivo = carpeta.resolve("trx.csv");
        Files.writeString(archivo, "fecha,cuenta,tipo,monto\n" +
                "5/6/2026,0002,CREDITO,200.00\n" +
                "6/6/2026,0001,DEBITO,50.125\n" +
                "7/6/2026,0003,RETIRO,10.00\n" +
                "8/6/2026,0001,CREDITO,80.50\n" +
                "31/6/2026,0002,DEBITO,5.00\n" +
                "9/6/2026,0002,DEBITO,30.00,,");

        ResultadoCarga resultado = CargaTransacciones.cargar(archivo);
        Map<TipoTransaccion, BigDecimal> totales = CalculoTransacciones.totalesPorTipo(resultado.validas());
        Map<String, BigDecimal> saldos = CalculoTransacciones.saldoPorCuenta(resultado.validas());
        String reporte = Reporte.armar(resultado, totales, saldos);
        System.out.println(reporte);

        assertThat(reporte).isEqualTo("Válidas: 3\n" +
                "Inválidas: 3\n" +
                "Línea 4: tipo inválido: RETIRO\n" +
                "Línea 6: fecha inválida: 31/6/2026\n" +
                "Línea 7: columnas: 6\n" +
                "Totales por tipo:\n" +
                "DEBITO         50.12\n" +
                "CREDITO       280.50\n" +
                "Saldos por cuenta:\n" +
                "0001           30.38\n" +
                "0002          200.00");
    }

    // Entrada: Un archivo con solamente el encabezado: fecha,cuenta,tipo,monto
    // Esperado:
    // Válidas: 0
    // Inválidas: 0
    // Totales por tipo:
    // DEBITO          0.00
    // CREDITO         0.00
    // Saldos por cuenta:
    // Predicción: verde
    @Test
    public void reporteSoloConEncabezado(@TempDir Path carpeta) throws IOException {
        Path archivo = carpeta.resolve("trx.csv");
        Files.writeString(archivo, "fecha,cuenta,tipo,monto");

        ResultadoCarga resultado = CargaTransacciones.cargar(archivo);
        Map<TipoTransaccion, BigDecimal> totales = CalculoTransacciones.totalesPorTipo(resultado.validas());
        Map<String, BigDecimal> saldos = CalculoTransacciones.saldoPorCuenta(resultado.validas());
        String reporte = Reporte.armar(resultado, totales, saldos);
        System.out.println(reporte);

        assertThat(reporte).isEqualTo("Válidas: 0\n" +
                "Inválidas: 0\n" +
                "Totales por tipo:\n" +
                "DEBITO          0.00\n" +
                "CREDITO         0.00\n" +
                "Saldos por cuenta:");
    }

    // Entrada: Un archivo con encabezado y tres líneas de transacciones válidas: fecha,cuenta,tipo,monto, 1/7/2026,0003,CREDITO,40.00, 2/7/2026,0001,DEBITO,15.5 y 3/7/2026,0003,DEBITO,60.00
    // Esperado:
    // Válidas: 3
    // Inválidas: 0
    // Totales por tipo:
    // DEBITO         75.50
    // CREDITO        40.00
    // Saldos por cuenta:
    // 0001          -15.50
    // 0003          -20.00
    // Predicción: verde
    @Test
    public void reporteConTodoValido(@TempDir Path carpeta) throws IOException {
        Path archivo = carpeta.resolve("trx.csv");
        Files.writeString(archivo, "fecha,cuenta,tipo,monto\n" +
                "1/7/2026,0003,CREDITO,40.00\n" +
                "2/7/2026,0001,DEBITO,15.5\n" +
                "3/7/2026,0003,DEBITO,60.00");

        ResultadoCarga resultado = CargaTransacciones.cargar(archivo);
        Map<TipoTransaccion, BigDecimal> totales = CalculoTransacciones.totalesPorTipo(resultado.validas());
        Map<String, BigDecimal> saldos = CalculoTransacciones.saldoPorCuenta(resultado.validas());
        String reporte = Reporte.armar(resultado, totales, saldos);
        System.out.println(reporte);

        assertThat(reporte).isEqualTo("Válidas: 3\n" +
                "Inválidas: 0\n" +
                "Totales por tipo:\n" +
                "DEBITO         75.50\n" +
                "CREDITO        40.00\n" +
                "Saldos por cuenta:\n" +
                "0001          -15.50\n" +
                "0003          -20.00");
    }
}

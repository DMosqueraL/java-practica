package dev.doris.sesion19_tests_archivos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class VariacionTest {
    // Entrada: archivo con el encabezado y pagos de 5.10 y 4.90
    // Esperado: 10.00
    // Predicción: verde
    @Test
    public void totalConEncabezadoYDosPagos(@TempDir Path carpeta) throws IOException {
        Path archivo = carpeta.resolve("pagos.csv");
        Files.writeString(archivo, "cuenta,monto\n0001,5.10\n0002,4.90");

        BigDecimal total = ResumenPagos.total(archivo);
        System.out.println("Test 1: \n" + total);

        assertThat(total).isEqualByComparingTo(new BigDecimal("10.00"));
    }

    // Entrada: archivo con el encabezado y tres líneas 0001 (sin monto), 0002,7.25 y 0003,1.00,extra
    // Esperado: Total         7.25
    // Línea 2: columnas inválidas
    // Línea 4: columnas inválidas
    // Predicción: verde
    @Test
    public void armarReporteConEncabezadosYTresLineas(@TempDir Path carpeta) throws IOException {
        Path archivo = carpeta.resolve("pagos.csv");
        Files.writeString(archivo, "cuenta,monto\n0001\n0002,7.25\n0003,1.00,extra");

        String reporte = ReportePagos.armar(archivo);
        System.out.println("Test 2: \n" + reporte);

        assertThat(reporte).isEqualTo("Total         7.25\nLínea 2: columnas inválidas\nLínea 4: columnas inválidas");
    }
}

package dev.doris.sesion19_tests_archivos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class Parte3PuntaAPuntaTest {

    // Entrada: encabezado, 0001 con 10.00, la línea "0002" sin monto y 0003 con 25.50
    // Esperado: el total de las dos válidas y la línea 3 reportada como inválida
    @Test
    public void armarReportaTotalYLineasInvalidas(@TempDir Path carpeta) throws IOException {
        Path archivo = carpeta.resolve("pagos.csv");
        Files.writeString(archivo, "cuenta,monto\n0001,10.00\n0002\n0003,25.50");

        String reporte = ReportePagos.armar(archivo);
        System.out.println(reporte);

        assertThat(reporte).isEqualTo("Total        35.50\nLínea 3: columnas inválidas");
    }
}

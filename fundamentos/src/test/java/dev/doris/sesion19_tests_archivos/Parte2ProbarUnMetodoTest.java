package dev.doris.sesion19_tests_archivos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class Parte2ProbarUnMetodoTest {

    // Entrada: archivo con el encabezado y pagos de 10.00 y 25.50
    // Esperado: 35.50
    @Test
    public void totalSumaLosPagosDelArchivo(@TempDir Path carpeta) throws IOException {
        Path archivo = carpeta.resolve("pagos.csv");
        Files.writeString(archivo, "cuenta,monto\n0001,10.00\n0002,25.50");

        BigDecimal total = ResumenPagos.total(archivo);
        System.out.println("[1] " + total);

        assertThat(total).isEqualByComparingTo(new BigDecimal("35.50"));
    }

    // Entrada: archivo con solo el encabezado
    // Esperado: 0
    @Test
    public void totalConSoloEncabezadoEsCero(@TempDir Path carpeta) throws IOException {
        Path archivo = carpeta.resolve("pagos.csv");
        Files.writeString(archivo, "cuenta,monto");

        BigDecimal total = ResumenPagos.total(archivo);
        System.out.println("[2] " + total);

        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }
}

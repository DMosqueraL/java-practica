package dev.doris.sesion19_tests_archivos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Parte1EscribirArchivoTest {

    @Test
    public void escribeYLeeUnArchivoTemporal(@TempDir Path carpeta) throws IOException {
        Path archivo = carpeta.resolve("pagos.csv");
        System.out.println("[1] " + carpeta);
        System.out.println("[2] " + archivo);

        Files.writeString(archivo, "cuenta,monto\n0001,10.00\n0002,25.50");

        List<String> lineas = Files.readAllLines(archivo);
        System.out.println("[3] " + lineas);
        System.out.println("[4] " + lineas.size());

        assertThat(lineas).containsExactly("cuenta,monto", "0001,10.00", "0002,25.50");
    }
}

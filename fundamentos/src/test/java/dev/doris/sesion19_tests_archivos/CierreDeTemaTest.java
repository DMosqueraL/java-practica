package dev.doris.sesion19_tests_archivos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class CierreDeTemaTest {

    @Test
    public void cierre(@TempDir Path carpeta) throws IOException {
        Path archivo = carpeta.resolve("datos.csv");

        Files.writeString(archivo, "a\nb\nc\n");
        System.out.println("[1] " + Files.readAllLines(archivo).size());

        Files.writeString(archivo, "x");
        System.out.println("[2] " + Files.readAllLines(archivo));

        Files.writeString(archivo, "cuenta,monto\n0001,2.50\n0002,2.50");
        System.out.println("[3] " + ResumenPagos.total(archivo));

        Files.writeString(archivo, "cuenta,monto");
        System.out.println("[4] " + ResumenPagos.total(archivo));
        System.out.println("[5] [" + ReportePagos.armar(archivo) + "]");

        Files.writeString(archivo, "cuenta,monto\n0001,3.00\n0002,1.00,");
        System.out.println("[6] " + ReportePagos.armar(archivo));

        try {
            Files.writeString(carpeta.resolve("sub").resolve("x.csv"), "hola");
            System.out.println("[7] escrito");
        } catch (NoSuchFileException e) {
            System.out.println("[7] falló");
        }

        Path otro = carpeta.resolve("datos.csv");
        System.out.println("[8] " + Files.readAllLines(otro).size());
    }
}

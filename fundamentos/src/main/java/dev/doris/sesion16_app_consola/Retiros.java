package dev.doris.sesion16_app_consola;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class Retiros {
    public static Retiro convertir(String linea) {
        String[] partes = linea.split(",", -1);
        if (partes.length != 3) {
            throw new IllegalArgumentException("Columnas: " + partes.length);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(partes[0].trim(), formatter);
        } catch (DateTimeParseException error) {
            throw new IllegalArgumentException("Fecha inválida: " + partes[0].trim());
        }
        String cajero = partes[1].trim();
        BigDecimal monto;
        try {
            monto = new BigDecimal(partes[2].trim()).setScale(2, RoundingMode.HALF_EVEN);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("Monto inválido: " + partes[2].trim());
        }
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto no positivo: " + monto);
        }
        return new Retiro(fecha, cajero, monto);
    }

    public static ResumenRetiros resumir(Path ruta) throws IOException {
        BigDecimal total = BigDecimal.ZERO;
        List<String> reporte = new ArrayList<>();
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            lector.readLine();
            String linea = lector.readLine();
            int numero = 1;
            while (linea != null) {
                numero++;
                try {
                    total = total.add(convertir(linea).monto());
                } catch (IllegalArgumentException error) {
                    reporte.add("línea " + numero + ": " + error.getMessage());
                }
                linea = lector.readLine();
            }
        }
        return new ResumenRetiros(total, reporte);
    }

    public static List<Retiro> desde(List<Retiro> retiros, LocalDate fecha) {
        List<Retiro> resultado = new ArrayList<>();
        for (Retiro retiro : retiros) {
            if (!retiro.fecha().isBefore(fecha)) {
                resultado.add(retiro);
            }
        }
        return resultado;
    }

    public static void main(String[] args) throws IOException {
        Path ruta = Path.of("src/main/resources/sesion16/retiros.csv");
        ResumenRetiros resultado = resumir(ruta);

        // Salida: 80.62
        System.out.println(resultado.total());
        // Salida: [línea 3: monto inválido: abc, línea 4: fecha inválida: 31/11/2026, línea 5: monto no positivo: -10.00, línea 7: columnas: 4]
        System.out.println(resultado.reporte());

        List<Retiro> retiros = List.of(
                new Retiro(LocalDate.of(2026, 11, 3), "C01", new BigDecimal("50.12")),
                new Retiro(LocalDate.of(2026, 11, 6), "C02", new BigDecimal("30.50")));
        // Salida: [Retiro[fecha=2026-11-06, cajero=C02, monto=30.50]]
        System.out.println(desde(retiros, LocalDate.of(2026, 11, 6)));
    }
}

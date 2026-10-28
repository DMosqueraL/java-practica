package dev.doris.sesion12_parseo_fechas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class LecturaFechas {
    static String leer(String texto, DateTimeFormatter formatter) {
        try {
            return "OK " + LocalDate.parse(texto, formatter);
        } catch (DateTimeParseException error) {
            return "ERROR " + texto;
        }
    }

    static List<LocalDate> leerValidas(List<String> textos, DateTimeFormatter formatter) {
        List<LocalDate> validas = new ArrayList<>();
        for (String texto : textos) {
            try {
                //validas = new ArrayList<>();
                validas.add(LocalDate.parse(texto, formatter));
            } catch (DateTimeParseException error) {
                // se salta el texto inválido
            }
        }
        return validas;
    }

    public static void main(String[] args) {
        DateTimeFormatter normal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter estricto = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);

        // [1] formatter normal ---> OK 2026-10-05
        System.out.println(leer("05-10-2026", normal));
        // [2] formatter normal ---> ERROR 2026-10-05
        System.out.println(leer("2026-10-05", normal));
        // [3] formatter normal ---> ERROR 5-10-2026
        System.out.println(leer("5-10-2026", normal));
        // [4] formatter normal ---> OK 2026-04-30
        System.out.println(leer("31-04-2026", normal));
        // [5] formatter estricto ---> ERROR 31-04-2026
        System.out.println(leer("31-04-2026", estricto));
        // [6] formatter estricto ---> OK 2026-04-30
        System.out.println(leer("30-04-2026", estricto));
        // [7] formatter normal ---> ERROR 15-13-2026
        System.out.println(leer("15-13-2026", normal));

        List<String> lista = List.of("01-10-2026", "xx", "03/10/2026");
        System.out.println("VEO: " + leerValidas(lista, normal));
    }
}

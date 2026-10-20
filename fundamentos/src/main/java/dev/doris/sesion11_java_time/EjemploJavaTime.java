package dev.doris.sesion11_java_time;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EjemploJavaTime {

    // true si la fecha cae entre desde y hasta, con ambos días incluidos
    static boolean enRango(LocalDate fecha, LocalDate desde, LocalDate hasta) {
        return !fecha.isBefore(desde) && !fecha.isAfter(hasta);
    }

    public static void main(String[] args) {
        LocalDate lunes = LocalDate.of(2026, 10, 19);
        LocalDate viernes = LocalDate.of(2026, 10, 23);
        LocalDate otroLunes = LocalDate.of(2026, 10, 19);

        // 1. Leer una fecha
        System.out.println(lunes);                              // 2026-10-19
        System.out.println(lunes.getMonthValue());              // 10

        // 2. Inmutabilidad
        lunes.plusDays(4);
        System.out.println(lunes);                              // 2026-10-19
        LocalDate masCuatro = lunes.plusDays(4);
        System.out.println(masCuatro);                          // 2026-10-23

        // 3. Comparar
        System.out.println(lunes.isBefore(viernes));            // true
        System.out.println(lunes.isAfter(viernes));             // false
        System.out.println(lunes.isBefore(otroLunes));          // false
        System.out.println(lunes == otroLunes);                 // false
        System.out.println(lunes.equals(otroLunes));            // true
        System.out.println(lunes.compareTo(viernes));           // -4

        // 4. Rango con ambos extremos incluidos
        System.out.println(enRango(lunes, lunes, viernes));                 // true
        System.out.println(enRango(viernes.plusDays(1), lunes, viernes));   // false

        // 5. Fecha y hora
        LocalDateTime cobro = LocalDateTime.of(2026, 10, 19, 14, 30);
        System.out.println(cobro);                                          // 2026-10-19T14:30
        System.out.println(cobro.toLocalDate().equals(lunes));              // true
        System.out.println(cobro.isBefore(LocalDateTime.of(2026, 10, 19, 18, 0))); // true

        // 6. Fecha que no existe
        try {
            LocalDate.of(2026, 2, 30);
            System.out.println("creada");
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());                 // Invalid date 'FEBRUARY 30'
        }
    }
}

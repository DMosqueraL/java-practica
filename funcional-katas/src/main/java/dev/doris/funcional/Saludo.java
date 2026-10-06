package dev.doris.funcional;

/**
 * Prueba de humo del entorno: si esto compila y su test pasa,
 * Java 21 y Maven están bien configurados. Bórralo cuando empieces la Kata 01.
 */
public sealed interface Saludo permits Saludo.Formal, Saludo.Casual {

    record Formal(String nombre) implements Saludo {}

    record Casual(String nombre) implements Saludo {}

    static String texto(Saludo saludo) {
        return switch (saludo) {
            case Formal(var nombre) -> "Buenas noches, " + nombre;
            case Casual(var nombre) -> "Hola, " + nombre;
        };
    }
}

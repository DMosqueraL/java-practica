# Trampas del 1Z0-830

Repaso de las trampas de examen que salen al final del Día A. Son **solo informativas**: no llevan predicción, no se evalúan y no entran a `fallos.md`. Es código de examen, no de producción.

- La salida o el error del compilador de cada trampa se ejecuta, no se supone.
- La actualiza el chat de Tema al cierre de la sesión, en el mismo commit que Progreso, solo si hubo trampa (ver "Trampa del 1Z0-830" en `PLAN.md`).
- Las trampas T01 a T04 se reconstruyeron en Planeación (v1.16) a partir de Progreso y se verificaron con `javac` 21.

## Índice

| # | Sesión | Tema | ¿Compila? | Regla |
|---|---|---|---|---|
| T01 | 10 | Excepciones propias | Sí (imprime `null`) | Sin `super(mensaje)`, el constructor llama a `super()` implícito y `getMessage()` devuelve `null` |
| T02 | 11 | `java.time` | No | `LocalDate.isBefore` recibe una fecha (`ChronoLocalDate`); un `LocalDateTime` se pasa con `toLocalDate()` |
| T03 | 12 | `DateTimeFormatter` y `DateTimeParseException` | No | Un `catch` de la hija después del `catch` del padre no compila: el padre ya la atrapó |
| T04 | 13 | `try` con recursos | No | La variable del `try` con recursos es implícitamente `final`: no se le puede asignar otro valor |
| T05 | 14 | `split` y parseo de CSV | Sí (imprime `4`) | `split(",")` descarta los textos vacíos del final: `"a,b,c,d,,"` da 4 partes, no 6 |
| T06 | 15 | `try`/`catch` dentro del bucle | No | Un `catch` de una excepción comprobada (`IOException`) cuyo `try` no puede lanzarla no compila |

## Detalle

### T01 · Sesión 10 · Constructor de excepción propia sin `super(mensaje)`

```java
public class Trampa1 {
    static class SaldoInsuficienteException extends RuntimeException {
        public SaldoInsuficienteException(String mensaje) {
            // falta super(mensaje);
        }
    }

    public static void main(String[] args) {
        try {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        } catch (SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        }
    }
}
```

- **¿Compila?** Sí.
- **Salida:** `null`
- **Por qué:** todo constructor llama primero al constructor del padre. Si no se escribe `super(...)`, Java pone `super()` sin argumentos, y `RuntimeException()` no guarda ningún mensaje. El parámetro `mensaje` se recibe pero nadie lo usa.
- **Corrección:** `super(mensaje);` como primera línea del constructor. Salida: `Saldo insuficiente`.

### T02 · Sesión 11 · `LocalDate.isBefore` con un `LocalDateTime`

```java
LocalDate fecha = LocalDate.of(2026, 10, 19);
LocalDateTime ahora = LocalDateTime.of(2026, 10, 20, 9, 30);
System.out.println(fecha.isBefore(ahora));
```

- **¿Compila?** No.
- **Error:** `incompatible types: LocalDateTime cannot be converted to ChronoLocalDate`
- **Por qué:** `LocalDate.isBefore` recibe un `ChronoLocalDate` (una fecha sin hora). `LocalDateTime` es fecha más hora y no es de ese tipo.
- **Corrección:** `fecha.isBefore(ahora.toLocalDate())`. Salida: `true`.

### T03 · Sesión 12 · `catch` del padre antes que el de la hija

```java
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu");
try {
    LocalDate fecha = LocalDate.parse("5/13/2026", formatter);
    System.out.println(fecha);
} catch (DateTimeException e) {
    System.out.println("fecha inválida");
} catch (DateTimeParseException e) {
    System.out.println("texto inválido");
}
```

- **¿Compila?** No.
- **Error:** `exception DateTimeParseException has already been caught`
- **Por qué:** `DateTimeParseException` es hija de `DateTimeException`. Java revisa los `catch` de arriba abajo, así que el primero ya atrapa a la hija y el segundo nunca podría ejecutarse. El compilador lo rechaza.
- **Corrección:** primero la hija y después el padre. Con el mes 13, la salida es `texto inválido`.

### T04 · Sesión 13 · Asignar otro valor a la variable del `try` con recursos

```java
public static void main(String[] args) throws IOException {
    Path ruta = Path.of("movimientos.txt");
    try (BufferedReader lector = Files.newBufferedReader(ruta)) {
        lector = Files.newBufferedReader(Path.of("otro.txt"));
        System.out.println(lector.readLine());
    }
}
```

- **¿Compila?** No.
- **Error:** `auto-closeable resource lector may not be assigned`
- **Por qué:** la variable declarada en el `try` con recursos es implícitamente `final`. Si se pudiera reasignar, el `try` cerraría el segundo lector y el primero quedaría abierto.
- **Corrección:** declarar el otro lector en el mismo `try`, separado con `;`, o en otro `try` con recursos.

### T05 · Sesión 14 · `split` descarta los vacíos del final

```java
String[] partes = "5/6/2026,0001,DEBITO,10.00,,".split(",");
System.out.println(partes.length);
System.out.println(partes[3]);
```

- **¿Compila?** Sí.
- **Salida:** `4` y `10.00`
- **Por qué:** `split(",")` corta en cada coma, pero quita los textos vacíos que quedan al final del arreglo. Las dos comas finales producirían dos partes vacías (`""`), y `split` las descarta. Por eso una línea con columnas vacías de más al final pasa la validación `partes.length != 4`. Casos extremos: `",,".split(",").length` da `0`, y `"".split(",").length` da `1` (el texto vacío completo).
- **Corrección (informativa, no se ha visto en las sesiones):** `split(",", -1)` conserva los vacíos del final. Con la misma línea, `length` da `6`.

### T06 · Sesión 15 · `catch (IOException)` con un `try` que no puede lanzarla

```java
import java.io.IOException;

public class Trampa6 {
    public static void main(String[] args) {
        try {
            System.out.println("cargando");
        } catch (IOException error) {
            System.out.println("no se pudo leer");
        }
    }
}
```

- **¿Compila?** No.
- **Error:** `exception IOException is never thrown in body of corresponding try statement`
- **Por qué:** `IOException` es comprobada, así que el compilador sabe qué la puede lanzar. Como un `catch` solo atrapa lo que se lanza dentro de su `try`, y nada dentro de ese `try` la lanza, el `catch` nunca podría ejecutarse y el compilador lo rechaza. Con una no comprobada, como `IllegalArgumentException`, el mismo código compila e imprime `cargando`: el compilador no revisa quién lanza las no comprobadas.
- **Corrección:** quitar el `try`/`catch`, o dejar dentro del `try` la llamada que sí puede lanzar `IOException` (por ejemplo, `Files.newBufferedReader(ruta)`).

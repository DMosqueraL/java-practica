# Repaso de temas

Qué temas ha vuelto a escribir Doris después de la sesión en que se vieron, y cuándo. Alimenta el bloque "Repaso espaciado" (ver `PLAN.md`). No es un registro de fallos: lo que falló y si se reforzó sigue en `fallos.md`.

- **Qué cuenta:** solo lo que escribe Doris: variación, kata, recuerdo, ejercicio de cierre, reescritura de una solución y repaso espaciado. No cuenta el código del tema, de Busca el error ni de los cierres de tema (lo escribe Claude), ni las predicciones.
- **Códigos de bloque:** V (variación), K (kata), R (recuerdo), E (ejercicio de cierre), X (reescritura de una solución), S (repaso espaciado). Con "falló" si tuvo un Error.
- **Última vez:** la sesión más reciente de la columna anterior. El repaso espaciado toma el concepto con la "Última vez" más antigua y al menos 5 sesiones sin escribirse.
- **Quién y cuándo:** el chat de Tema al cierre de la sesión, en el mismo commit que Progreso. Agrega la sesión y el bloque a cada tema de otra sesión que Doris escribió y una fila por cada tema nuevo.
- Las filas de la Fase 0 las reconstruyó Planeación en la v1.38 (2026-11-23) a partir del código de `fundamentos`, `recuerdos/`, Progreso y `fallos.md`, antes del repaso del ejercicio de cierre de la Semana 4.

| Concepto | Se vio | Escrito por Doris (sesión y bloque) | Última vez |
|---|---|---|---|
| `Set`/`HashSet` y el `boolean` de `add` | s6 | s6 K | s6 |
| Clase normal: campos `private`, constructor y getters | s2 | s2 K; s10 V, K; s11 E | s11 |
| `equals`/`hashCode` en clase normal | s6 | s6 V; s7 R (falló); s11 E (falló) | s11 |
| Excepción propia (`extends RuntimeException`, `super(mensaje)`) | s10 | s10 V, K; s11 E (falló) | s11 |
| `LocalDateTime`, `toLocalDate` y `plusDays` | s11 | s11 V | s11 |
| `valueOf` de un `enum` | s4 | s4 K; s14 K; s15 R | s15 |
| `isBefore`/`isAfter` y rango con extremos incluidos | s11 | s11 V, K; s12 R; s16 X | s16 |
| `DateTimeFormatter` con `STRICT` y `catch (DateTimeParseException)` | s12 | s12 V, K; s13 R (falló); s14 K; s15 R; s16 E | s16 |
| `BufferedReader`, `while` con `readLine()` y `try` con recursos | s13 | s13 V, K; s14 R; s15 V, K; s16 E (falló) | s16 |
| `split(",", -1)`, `trim()` y `String[]` | s14 | s14 V, K; s15 R; s16 E | s16 |
| `setScale` con `RoundingMode.HALF_EVEN` | s14 | s14 V, K; s15 R; s16 E | s16 |
| `try`/`catch` dentro del bucle y contador de línea | s15 | s15 V, K; s16 E | s16 |
| `throw new IllegalArgumentException` al validar | s9 | s9 V, K; s10 K; s11 K; s12 K; s14 K; s16 E | s16 |
| `compareTo` con `BigDecimal` | s8 | s9 K; s10 K; s11 E (falló); s16 V, E (falló) | s16 |
| `subtract` y saldo negativo | s17 | s10 V; s17 V, K; s18 R | s18 |
| `for` con índice | s3 | s16 K; s17 R; s18 V, K | s18 |
| Acumular con `getOrDefault` + `add` + `put` | s7 | s8 V; s10 K; s11 E; s17 V, K; s18 R | s18 |
| `BigDecimal.add` con el resultado asignado | s8 | s8 K; s14 K; s16 E; s17 V, K; s18 R | s18 |
| `values()` de un `enum` | s17 | s17 V, K; s18 K; s19 R | s19 |
| `readAllLines` | s13 | s13 V; s20 V | s20 |
| `args` en el `main` | s16 | s16 V, K; s20 V | s20 |
| `@TempDir` y `Files.writeString` | s19 | s19 V, K; s20 R | s20 |
| `assertThatThrownBy` con `isInstanceOf` | s9 | s9 K; s10 K; s11 K; s12 K; s13 K; s14 K; s20 K | s20 |
| `Map` con una lista por clave (`containsKey` o `getOrDefault`, `get` y `add`) | s7 | s7 K; s8 R; s10 K; s21 E (falló) | s21 |
| Recorrer un `Map` con `keySet()` y `get` | s17 | s18 K; s19 R (falló); s21 E | s21 |
| `TreeMap` para claves ordenadas | s18 | s18 K; s21 E | s21 |
| `String.format` con ancho y `-` | s18 | s18 V, K; s19 R; s21 E | s21 |
| Acumular un `String` en un `for` | s16 | s16 K; s17 R; s18 K; s19 R; s21 E (falló) | s21 |
| `record` | s4 | s4 K; s15 V; s21 E | s21 |
| for-each | s3 | todas las sesiones; s21 E | s21 |
| `for` dentro de otro `for` | — (no fue tema de la Fase 0) | nunca (s21 E lo pidió sin verificar la estructura) | — |
| `Arrays.asList` (tamaño fijo) | Diagnóstico | nunca (va en la s24) | — |
| Constructor `private` | s7 | s7 R (falló) (tema de la s21) | s7 |

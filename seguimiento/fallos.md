# Seguimiento de fallos

Objetivo: ver de un vistazo **dónde se falla** y si ya se reforzó. No es un puntaje.

- **Estado:** `pendiente` = falló y aún no ha vuelto a salir bien. `reforzado (sesión N)` = volvió a aparecer y se acertó.
- La salida real de cada ejercicio de cierre se ejecuta, no se supone.
- Qué entra y cómo se actualiza: ver "Registro de fallos" en `PLAN.md` (v1.4). La sesión 6 se reconstruyó desde su fila de Progreso y el diagnóstico desde su resultado en `PLAN.md`.

## Índice de fallos

| Concepto | Dónde falló | Por qué falla | Estado |
|---|---|---|---|
| `String` se compara con `equals`, no con `==` | Diagnóstico y ejercicio de cierre de la Semana 1 (s6) | `==` compara referencias; `equals` compara el contenido | pendiente |
| Leer qué devuelve cada llamada encadenada (`.size()` sobre el resultado de un método) | Ejercicio de cierre de la Semana 1 (s6) | Se predijo la lista donde el método devuelve un `int` | pendiente |
| Sintaxis de `assertThat(...)` | Ejercicio de cierre de la Semana 1 (s6) | Paréntesis mal cerrado, argumento de tipo equivocado y valor esperado incorrecto | pendiente |
| Un nombre de `package` no admite guiones | Ejercicio de cierre de la Semana 1 (s6) | Sigue las reglas de los identificadores: letras, dígitos, `_` y puntos entre partes | pendiente |
| `@Override` se escribe con O mayúscula | Recuerdo de `Pago` (s7) | Java distingue mayúsculas: `@override` no existe | pendiente |
| Un constructor `private` impide `new` desde fuera de la clase | Recuerdo de `Pago` (s7) | `private` limita el acceso a la propia clase | pendiente |
| `Arrays.asList` devuelve una lista de tamaño fijo: `add` lanza `UnsupportedOperationException` | Diagnóstico (2026-10-06) | La lista está respaldada por un arreglo; no admite agregar ni quitar | pendiente |
| Plantear un test: qué comprobar | Kata-02 (s6), tema de la s7 y Kata-03 (s7) | `contains` suelto no ve elementos de más; falta comprobar cuántos; el test de lista vacía no llamaba al método; datos copiados sin ajustar. En la kata-03: "Comprueba" mezclaba entrada con salida, "Por qué" describía una situación en vez de un error posible, entrada sin los movimientos, y restos copiados de un caso a otro | pendiente (se retoma en la kata de la s8) |
| Orden de operaciones con `Set`: `contains` después de `add` | Kata-02, `hayCuentasRepetidas` (s6) | `contains` siempre daba `true` porque el elemento ya se había agregado | pendiente |
| `equals` en clase normal: comparar cada campo con `equals` | Recuerdo de `Pago` (s7) | Escribió `otro.id() && otro.tipo()`: usó sintaxis de `record` y `&&` sobre `String` | pendiente |
| `getOrDefault` devuelve el valor por defecto, no `null` | Cierre de tema Map (s7), línea 5 | Respondió `null` en vez de `-1`; el `null` lo da `get` | pendiente |
| `get` de una clave inexistente devuelve `null`; usarlo como valor (asignarlo a un `int`, llamar a `.add`) lanza `NullPointerException` | Cierre de tema Map (s7), línea 10 | Un `int` no admite `null` y sobre `null` no se puede llamar un método: lanza `NullPointerException`, no queda `null` | pendiente (se ejercita en la variación del tema de la s8) |
| Agrupar con `Map`: `put` sobre una clave existente reemplaza el valor; hay que obtener la lista y hacer `add` | Kata-03, `agruparPorCuenta` (s7) | Escribió `put(cuenta, List.of(mov))`, que reemplaza la lista en cada movimiento (y `List.of` no admite `add`); luego guardó la lista en una variable local sin ponerla en el `Map`. Había acertado `put` reemplaza en el cierre de tema (líneas 1 y 3) pero no lo aplicó | pendiente |
| Modelo de los cajones de `HashSet` (`hashCode` y luego `equals`) | Tema Set y `hashCode` (s6) | Modelo inicial incorrecto de cómo se busca | reforzado (sesión 7): explicó bien "mismo cajón, pero `equals` dio `false`" |
| `==` leído como `equals` | Tema Set y `hashCode` (s6) | Dos objetos con `new` son referencias distintas | reforzado (sesión 6): cierre 7/7, línea 7 |
| Sintaxis del for-each: `for (Tipo x : lista)` | Diagnóstico (2026-10-06) | No recordaba la forma del for-each | reforzado (sesión 6): escrito bien en el ejercicio de cierre de la Semana 1 |

## Detalle por sesión

### Sesión 7 · Map y HashMap (2026-10-12)

Cierre de tema: predicción de 10 líneas, ejecutada para obtener las salidas reales.

| # | Tu respuesta | Salida real | | Por qué |
|---|---|---|---|---|
| 1 | 5 | 5 | ✅ | `put` sobre una clave existente devuelve el valor anterior y lo reemplaza |
| 2 | 3 | 3 | ✅ | Hay tres claves |
| 3 | 6 | 6 | ✅ | El valor reemplazado en la línea 1 |
| 4 | null | null | ✅ | `get` de una clave inexistente devuelve `null` |
| 5 | null | **-1** | ❌ | `getOrDefault(clave, -1)` devuelve el segundo argumento cuando falta la clave |
| 6 | true | true | ✅ | `containsKey` |
| 7 | A | A | ✅ | El `record` genera `equals` y `hashCode` por sus campos |
| 8 | 40 | 40 | ✅ | `Sede` sobrescribe `equals` y `hashCode` con el mismo campo |
| 9 | null | null | ✅ | `Bodega` no los sobrescribe: son dos objetos distintos para el `Map` |
| 10 | null | **`NullPointerException`** | ❌ | `get("jueves")` es `null`, pero asignarlo a un `int` obliga a convertirlo y falla |

Otros fallos de la sesión: recuerdo de `Pago` (ver índice). La variación `ConteoColores` salió sin errores.
Mensaje real de la línea 10: `Cannot invoke "java.lang.Integer.intValue()" because the return value of "java.util.Map.get(Object)" is null`.

### Sesión 6 · Set, HashSet y `hashCode` (2026-10-09 y 2026-10-11)

Cierre de tema: predicción de 7 líneas, todas acertadas. Los fallos de la sesión vinieron del tema y de la kata-02 (ver índice): modelo inicial de los cajones, `p1 == p2` leído como `equals`, `contains` que siempre daba `true`, test sin `hasSize`, test de lista vacía que no llamaba al método y datos copiados sin ajustar.

Ejercicio de cierre de la Semana 1 (formato distinto, solo sus fallos en el índice): `String` con `==` en vez de `equals`, `.size()` sobre el resultado de un método, sintaxis de `assertThat(...)` y guion en `package`. El error de digitación en un nombre de variable no entra al registro.

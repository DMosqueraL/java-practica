# Seguimiento de fallos

Objetivo: ver de un vistazo **dónde se falla** y si ya se reforzó. No es un puntaje.

- **Estado:** `pendiente` = falló y aún no ha vuelto a salir bien. `reforzado (sesión N)` = volvió a aparecer y se acertó.
- La salida real de cada ejercicio de cierre se ejecuta, no se supone.
- Qué entra y cómo se actualiza: ver "Registro de fallos" en `PLAN.md` (v1.4). La sesión 6 se reconstruyó desde su fila de Progreso y el diagnóstico desde su resultado en `PLAN.md`.

## Índice de fallos

| Concepto | Dónde falló | Por qué falla | Estado |
|---|---|---|---|
| Recorrer un `for` a mano, vuelta por vuelta, para saber qué contiene una variable al final: `validas = new ArrayList<>()` dentro del `for` crea una lista nueva en cada vuelta y descarta la anterior (familia 6, Riesgo) | Busca el error (s12) | En `leerValidas` respondió que `validas` quedaba siempre vacía y puso la familia 5 (tipo o precisión). Con la pista 1 (recorrido a mano) y con el nivel 2 (qué hace el `new` y que el `add` va después) siguió igual; con el esqueleto parcial (nivel 3) completó bien las vueltas 2 y 3 y corrigió la salida (`[2026-10-03]`) y la familia (6). La misma familia ya había salido en la kata-03 (`put` que reemplaza) | pendiente |
| Un entero con cero a la izquierda es un literal octal: `05` vale 5, pero `08` y `09` no compilan (Riesgo) | Kata-07 (s11) | Escribió `LocalDate.of(2026, 10, 05)`, `01` y `02`: funcionan porque 01 a 07 valen lo mismo en octal, pero con `09` no compila. Se escribe `5`, `1`, `2` | reforzado (sesión 12): en el recuerdo de la kata-07 escribió `LocalDate.of(2026, 9, 9)` y `(2026, 10, 5)` sin cero a la izquierda |
| Constructor de una clase normal: lleva su lista de parámetros; `public Nombre {` sin paréntesis es el constructor compacto del `record` | Ejercicio de cierre de la Semana 2, `MontoExcedidoException` (s11) | Escribió `public MontoExcedidoException {` y usó `exceso` en `super(...)` sin declararlo; en la s10 sí lo escribió bien (`CuentaInvalidaException(String cuenta)`) | pendiente |
| Dirección de `compareTo`: `a.compareTo(b) > 0` significa `a > b`; "supera el tope" es `monto.compareTo(tope) > 0` | Ejercicio de cierre de la Semana 2, `crear` (s11) | Escribió `monto.compareTo(tope) < 0`: habría lanzado `MontoExcedidoException` con `100.50` (familia 3, condición invertida). Sí usó `compareTo` y no `<` | pendiente |
| Alcance de una variable: lo declarado dentro del `for` no existe fuera de él | Ejercicio de cierre de la Semana 2, `totalPorComercio` (s11) | Devolvió `totalCobroPorComercio`, declarada dentro del `for`, en vez del `Map` `totalPorComercio` | reforzado (sesión 12): en el recuerdo de `filtrarPorRango` declaró `lista` fuera del `for` y devolvió esa |
| Usar los nombres del enunciado (campos del `record`, método pedido) | Ejercicio de cierre de la Semana 2 (s11) | `cobro.cuenta()` y `cobro.cuenta` cuando el `record` tiene `comercio` (nombre copiado de la kata-06), y `totalPorComercios` en vez de `totalPorComercio`: el `main` no habría compilado | reforzado (sesión 12): en el recuerdo usó los nombres del enunciado (`Transaccion`, `cuenta`, `monto`, `fecha`, `Transacciones`, `filtrarPorRango`, `transacciones`, `desde`, `hasta`) |
| Una clase usada como clave de `HashSet`/`HashMap` lleva campos `final` (Riesgo) | Ejercicio de cierre de la Semana 2, `Comercio` (s11) | Campos `private String` sin `final`: si `codigo` cambia con el objeto ya dentro de un `HashSet`, cambia su `hashCode` y el set no lo encuentra | pendiente |
| `String` se compara con `equals`, no con `==` | Diagnóstico y ejercicio de cierre de la Semana 1 (s6), Busca el error (s10) | `==` compara referencias; `equals` compara el contenido | reforzado (sesión 10): en Busca el error identificó `==` como el error y propuso `equals`, sin pista |
| Leer qué devuelve cada llamada o expresión (`.size()` sobre el resultado de un método; `==` dentro de un `println`) | Ejercicio de cierre de la Semana 1 (s6), cierre de tema BigDecimal, línea 1 (s8), kata-04 (s8) y cierre de tema Excepciones, marca [6] (s9) | Se predijo la lista donde el método devuelve un `int`; en la s8 se escribió el valor de `total` en vez del `boolean` de `total == 1.0` (sabía que era `false`; escribió primero la explicación). En la kata-04 creyó que un método que arranca con `total = null` lanzaría `NullPointerException` con la lista vacía; con la lista vacía el `for` no da ninguna vuelta, así que nunca se llama `total.add` y devuelve `null`. En la s9 contestó `F` (lo que imprime la línea anterior) en vez de la marca [6], que estaba en `println("G")` dentro del `catch`; ese `catch` no se ejecuta porque el `try` no lanzó nada. Su explicación ("no hay ningún llamado a algún método") era correcta | pendiente |
| Sintaxis de `assertThat(...)` | Ejercicio de cierre de la Semana 1 (s6) y kata-05 (s9) | Paréntesis mal cerrado, argumento de tipo equivocado y valor esperado incorrecto. En la kata-05: `isEqualTo("0001", new BigDecimal("150.5"))` con dos argumentos, cuando recibe uno solo (había que comparar contra un `Abono`) | reforzado (sesión 11): en la kata-07 escribió bien `assertThat(...).hasSize(...)`, `containsExactly(...)` y `assertThatThrownBy(...).isInstanceOf(...).hasMessage(...)` |
| El `catch` se decide por tipo; sin `catch` que coincida el programa termina con stack trace; `IllegalArgumentException` es la que lanza el programador ante un argumento inválido o nulo | Teach-back de la kata-05 (s9); cierre de tema de Excepciones propias, marcas [5] a [7], y teach-back (s10) | Describió solo el caso en que sí hay `catch` que coincide, sin decir que el método termina en el `throw` ni que la excepción sube por la pila; dijo que el `catch` "coincide" sin decir que es por tipo; y usó `IllegalArgumentException` "porque la pidieron" en vez de por argumento inválido o nulo. Con la pregunta de apoyo explicó bien qué pasa sin `catch` En la s10 creyó que, si el primer `catch` no coincide, el `main` termina sin mirar los demás: en [5] predijo stack trace, cuando Java revisa los `catch` en orden y ejecuta el primero que coincide ([6] y [7] sí se imprimen). Acertó [8] por otra razón: era un `NullPointerException` que el `catch` de `IllegalArgumentException` no atrapa. En el teach-back dijo que `IllegalArgumentException` "es para el sistema"; la lanza el programador, mientras que la JVM lanza `NullPointerException` y `ArithmeticException`. | pendiente (s10: sigue) (s11: en el cierre de `java.time` acertó que el `try` salta al `catch` por `DateTimeException`; el orden de varios `catch` y `IllegalArgumentException` no se evaluaron porque el `main` del ejercicio de la Semana 2 quedó en blanco; en la kata-07 usó `IllegalArgumentException` para nulos y rango invertido y en el teach-back la justificó como rango imposible, sin decir "argumento inválido" ni que la lanza el programador) (s12: en el recuerdo de la kata-07 justificó `IllegalArgumentException` como "argumento inválido o nulo, que lanza el programador", pero con una frase casi igual a la que Claude le había escrito minutos antes, así que no se cuenta como reforzada; en la variación `LecturaFechas` escribió `catch (Exception)` en vez de `DateTimeParseException` (Riesgo: atrapa cualquier error, y `leer(null, normal)` devolvía `ERROR null` en vez de fallar con `NullPointerException`) y lo corrigió en la sesión; en el cierre de tema acertó [8]: `catch (DateTimeException)` atrapa una `DateTimeParseException` por ser su hija; en la kata-08 el `catch` necesitó pistas hasta el nivel 3: primero escribió `catch (IllegalArgumentException)` con `return` de un `String`, y con el esqueleto completó `catch (DateTimeParseException)` con `throw`; en el teach-back explicó con sus palabras que ella, como programadora, lanza `IllegalArgumentException` porque el texto es un argumento inválido, y que `catch (Exception)` esconde errores como `NullPointerException`, pero esto último repite lo que Claude escribió en la revisión de la variación, así que el concepto sigue pendiente) |
| Dos `String` literales iguales comparten objeto (string pool): `==` da `true`; solo `new String(...)` o el texto leído en ejecución da `false` | Busca el error (s10) | Predijo `0` para `contarPorCuenta` con `==` y la entrada hecha con literales (`"0001"` y `"0001"`); la salida real es `3`, porque los literales iguales son el mismo objeto. Lo corrigió usando `new String("0001")`. Importa para la Semana 3: el texto de un CSV no es literal | pendiente |
| Acumular un total por clave en un `Map<_, BigDecimal>`: `getOrDefault` + `add` + `put` | Kata-06, `totalesPorTipo` (s10) | La primera versión creaba la clave con un valor inicial pero nunca sumaba el monto, y dejaba una variable `total` fuera del `for` sin usar. Con el nivel 1 no llegó; con el esqueleto (nivel 3) lo completó bien. Había usado el patrón en la variación `TotalesPorCuenta` (s8). En el teach-back explicó que `add` suma y `put` reemplaza el total, sin mencionar que `BigDecimal` es inmutable | reforzado (sesión 11): en el ejercicio de cierre de la Semana 2 escribió `getOrDefault(..., BigDecimal.ZERO)` + `add` + `put` con el resultado reasignado |
| Un nombre de `package` no admite guiones | Ejercicio de cierre de la Semana 1 (s6) | Sigue las reglas de los identificadores: letras, dígitos, `_` y puntos entre partes | reforzado (sesión 12): en el recuerdo escribió `package dev.doris.sesion11_java_time;` sin guiones |
| `@Override` se escribe con O mayúscula | Recuerdo de `Pago` (s7) | Java distingue mayúsculas: `@override` no existe | reforzado (sesión 11): escribió `@Override` bien en `equals` y `hashCode` |
| Un constructor `private` impide `new` desde fuera de la clase | Recuerdo de `Pago` (s7) | `private` limita el acceso a la propia clase | pendiente |
| `Arrays.asList` devuelve una lista de tamaño fijo: `add` lanza `UnsupportedOperationException` | Diagnóstico (2026-10-06) | La lista está respaldada por un arreglo; no admite agregar ni quitar | pendiente |
| Plantear un test: qué comprobar | Kata-02 (s6), tema de la s7, Kata-03 (s7) y kata-05 (s9) y kata-06 (s10) y kata-07 (s11) | `contains` suelto no ve elementos de más; falta comprobar cuántos; el test de lista vacía no llamaba al método; datos copiados sin ajustar. En la kata-03: "Comprueba" mezclaba entrada con salida, "Por qué" describía una situación en vez de un error posible, entrada sin los movimientos, y restos copiados de un caso a otro. En el taller y la kata-04 (s8): Caso y Entrada genéricos, "Comprueba" describía el método, planteamientos copiados entre las versiones B y C; sí localizó bien el error de B y C. En la kata-04, primera versión del Test 3: usó montos `double` y comprobó "no es 0.3", así que el test pasaba igual con el método bueno y con uno que suma con `double`, y el nombre del test no decía lo que hacía; el "Atrapa" del Test 2 no sabía qué método erróneo imaginar. En la kata-05 (s9): un Esperado copiado de otro test sin ajustar, y las condiciones `monto <= 0` y `largo != 4` probadas solo con valores lejos del límite, sin test de borde; dice que lo difícil es hallar la entrada que atrapa un error En la kata-06 (s10): `Entrada` copiado sin ajustar en dos tests, y el test de `totalesPorTipo` comprobaba una sola clave del `Map`, sin el tamaño (mismo patrón de "falta comprobar cuántos" de la kata-02, s6). | pendiente (s9: en la kata-05 baja a pista máxima 2 y plantea los tests casi sola; siguen pendientes el test de borde y el "Atrapa") (s10: planteó los 4 tests sin pistas e incluyó el monto en cero; siguen los datos copiados sin ajustar y comprobar solo una parte del resultado; el "Atrapa" no se practicó) (s11: kata-07 sin pistas; comprobó cuántos y cuáles elementos; falló la `Entrada` del test 1, que no incluía el rango, y puso las fechas en el `Esperado` del test 2; ningún test probó el borde `desde`) (s12: kata-08 con los 3 tests planteados sin pistas, con Entrada y Esperado completos y sin datos copiados; Riesgo: ningún test comprueba que el año tenga 4 dígitos, y un patrón `d/M/u` dejaba los 3 tests en verde, verificado con Java simple) |
| Orden de operaciones con `Set`: `contains` después de `add` | Kata-02, `hayCuentasRepetidas` (s6) | `contains` siempre daba `true` porque el elemento ya se había agregado | pendiente |
| `equals` en clase normal: comparar cada campo con `equals` | Recuerdo de `Pago` (s7) y ejercicio de cierre de la Semana 2, `Comercio.equals` (s11) | Escribió `otro.id() && otro.tipo()`: usó sintaxis de `record` y `&&` sobre `String`. En la s11 castea a `Cobro` (otra clase) en vez de `Comercio` y devuelve `otro.codigo()`, un `String`, cuando `equals` devuelve `boolean`: faltaba `codigo.equals(otro.codigo)`. `hashCode` con `Objects.hash(codigo)` y `@Override` sí salieron bien | pendiente (s11: sigue) |
| Crear `BigDecimal` con `String` y no con `double`: `new BigDecimal(0.1)` arrastra el error del `double` | Cierre de tema BigDecimal, línea 10 (s8) | Aplicó bien `equals` (valor y escala) pero no vio que el constructor con `double` trae la aproximación (escala 55), así que nunca es `equals` a `"0.1"` | reforzado (sesión 8): en la kata-04 explicó que con `String` el resultado es exacto y que el problema está en meter `double` |
| `getOrDefault` devuelve el valor por defecto, no `null` | Cierre de tema Map (s7), línea 5 | Respondió `null` en vez de `-1`; el `null` lo da `get` | reforzado (sesión 8): variación `TotalesPorCuenta`, predijo `$0` para la cuenta ausente |
| `get` de una clave inexistente devuelve `null`; usarlo como valor (asignarlo a un `int`, llamar a `.add`) lanza `NullPointerException` | Cierre de tema Map (s7), línea 10 | Un `int` no admite `null` y sobre `null` no se puede llamar un método: lanza `NullPointerException`, no queda `null` | reforzado (sesión 8): predijo `NullPointerException` en `vacio.get("0001").add(...)`; al explicarlo habló de una conversión, pero ahí se llama un método sobre `null` (la conversión fue el caso del `int`) |
| Agrupar con `Map`: `put` sobre una clave existente reemplaza el valor; hay que obtener la lista y hacer `add` | Kata-03, `agruparPorCuenta` (s7) | Escribió `put(cuenta, List.of(mov))`, que reemplaza la lista en cada movimiento (y `List.of` no admite `add`); luego guardó la lista en una variable local sin ponerla en el `Map`. Había acertado `put` reemplaza en el cierre de tema (líneas 1 y 3) pero no lo aplicó | reforzado (sesión 8): recuerdo de `agruparPorCuenta` y variación `TotalesPorCuenta` (acumuló con `put` + `getOrDefault` + `add`) |
| Modelo de los cajones de `HashSet` (`hashCode` y luego `equals`) | Tema Set y `hashCode` (s6) | Modelo inicial incorrecto de cómo se busca | reforzado (sesión 7): explicó bien "mismo cajón, pero `equals` dio `false`" |
| `==` leído como `equals` | Tema Set y `hashCode` (s6) | Dos objetos con `new` son referencias distintas | reforzado (sesión 6): cierre 7/7, línea 7 |
| Sintaxis del for-each: `for (Tipo x : lista)` | Diagnóstico (2026-10-06) | No recordaba la forma del for-each | reforzado (sesión 6): escrito bien en el ejercicio de cierre de la Semana 1 |

## Testing por kata

Una fila por kata, desde la kata-04 (ver "Busca el error" y la regla "Error real → test" en `PLAN.md`). Pista máxima: 0 = sin pistas; 1 a 4 = el nivel más alto que hizo falta (4 = solución dada). La progresión esperada es que baje hasta 0 o 1.

| Kata | Tests | Pista máxima | Busca el error (familia; ¿lo encontró?) | Errores reales convertidos en test |
|---|---|---|---|---|
| 04 (s8) | 3, todos en verde: varios abonos (familia 1), lista vacía (familia 2) y suma exacta de decimales (familia 5) | 4: el planteamiento del Test 1 se dio como referencia y el del Test 3 se dio completo a su pedido (pista 4); el Test 2 lo escribió casi sola y solo faltó el "Atrapa" (pista 2). La tabla de pensamiento (correcto frente a erróneo) no le sirvió y no la usó | — (la kata es anterior al bloque, que empieza en la sesión 9) | Ninguno registrado. El Test 3 quedó sin rojo exigido (v1.9): lo verificó Claude con Java simple (con `double` daría `0.30000000000000004`, no `0.30`) y su "Atrapa" (familia 5) es opcional |
| 05 (s9) | 6 en verde (JUnit 5 + AssertJ, `assertThatThrownBy`): camino feliz, cuenta nula, monto nulo, monto negativo, cuenta de 5 caracteres y monto igual a cero (este último, de borde, lo agregó después de la revisión como práctica opcional). Sin "Atrapa" (opcional) | 2: nivel 1 en `compareTo` (lo recordó sola) y nivel 2 en el `isEqualTo` del camino feliz. Planteó los tests casi sola. El test de borde se guió con pasos (nivel 3), fuera del mínimo de la kata | familia 3 (condición de más: `>= 100` en vez de `> 100`); sí lo encontró, pista 0 | Ninguno registrado: el único error real fue de compilación (`isEqualTo` con dos argumentos). Riesgo de la revisión: ningún test probaba el borde de `monto <= 0` ni el del largo de la cuenta; se agregó el del `0` y falta el del largo (cuenta de 3 caracteres) |
| 06 (s10) | 4 en verde (JUnit 5 + AssertJ, `assertThatThrownBy`): `crear` con tipo nulo, `crear` con cuenta de 6 caracteres, `crear` con monto cero y `totalesPorTipo` con 7 movimientos. Sin "Atrapa" (opcional) | 3 (esqueleto parcial) solo en `totalesPorTipo`; el nivel 2 no aportó nada distinto al 1. Los tests los planteó sin pistas | familia 4 (comparación equivocada: `==` en vez de `equals`); sí lo encontró, pista 0. La predicción con literales falló por el string pool y la corrigió con `new String` | El error real de `totalesPorTipo` (no acumulaba) queda cubierto por el test 4. Riesgo de la revisión: el test 4 comprobaba una sola clave; se agregó el tamaño del `Map` |
| 07 (s11) | 3 en verde (JUnit 5 + AssertJ, `assertThatThrownBy`): rango con 3 resultados en el orden de la lista, rango invertido (`IllegalArgumentException` con mensaje) y borde `hasta` incluido. Sin "Atrapa" (opcional) | 0: solo pidió una lista de transacciones con datos; los 3 tests los planteó sin pistas | familia 2 (valor inicial equivocado: `LocalDate.of(2026, 1, 1)` fijo); sí lo encontró, pista 1 (recorrido a mano con la entrada) | Ninguno registrado: no hubo un error real que convertir en test. Riesgo de la revisión: ningún test tiene una transacción con fecha igual a `desde`; un filtro con `isAfter(desde)` dejaba los 3 tests en verde (verificado con Java simple y los datos de los tests 1 y 3) |
| 08 (s12) | 3 (JUnit 5 + AssertJ, `assertThatThrownBy`; verificados con Java simple, sin Maven en el chat): texto nulo, `"5/6/2026"` válida con un dígito y `"31/6/2026"` inexistente. Sin "Atrapa" (opcional) | 3: pista 1 para armar el formatter (juntar `flexible` y `estricto`) y niveles 1 a 3 en el `catch` del punto 3 (atrapar `DateTimeParseException` y lanzar con `throw`). Los 3 tests los planteó sin pistas | familia 6 (reemplaza en vez de agregar: `new ArrayList<>()` dentro del `for`); encontró la línea sin pistas, pero la salida y la familia solo con pistas hasta el nivel 3 | Ninguno registrado: el error real fue de concepto en el `catch` (`return` de un `String` e `IllegalArgumentException` en vez de `DateTimeParseException`), no un resultado de test. Riesgo de la revisión: ningún test comprueba que el año tenga 4 dígitos; un patrón `d/M/u` dejaba los 3 tests en verde (verificado con Java simple) |

## Detalle por sesión

### Sesión 12 · Convertir texto a fecha: `DateTimeFormatter` y `DateTimeParseException` (Día A: 2026-10-20 y 2026-10-27)

**Recuerdo de la kata-07 (2026-10-20):** `record Transaccion` y `Transacciones.filtrarPorRango` con las tres validaciones y el rango con extremos incluidos, completos y correctos. Pendientes aplicados y acertados: `package` sin guion, fechas sin cero a la izquierda (`9` y `5`), nombres del enunciado y alcance de `lista` (ver índice). La justificación de `IllegalArgumentException` era correcta pero casi igual a la frase que Claude le había escrito antes: no se cuenta como reforzada. Digitación (no entra): falta un `)` en `if(desde.isAfter(hasta) {`. Estilo (no entra): `if(` y `for(` sin espacio. Claude dijo por error que el import de `BigDecimal` en `Transacciones` sobraba: se usa en el `main`.

Cierre de tema (2026-10-27): predicción de 8 marcas, ejecutada (Java 21) para obtener las salidas reales. Resultado: 8/8.

| # | Tu respuesta | Salida real | | Por qué |
|---|---|---|---|---|
| 1 | 2026-12-31 | 2026-12-31 | ✅ | Sin formatter, `parse` lee el formato ISO |
| 2 | sin formatter | sin formatter | ✅ | `31/12/2026` no es ISO: `DateTimeParseException` y entra el `catch` |
| 3 | 2026-03-07 | 2026-03-07 | ✅ | `d/M/yyyy` lee día 7 y mes 3 |
| 4 | falla | falla | ✅ | `dd` exige dos dígitos |
| 5 | 2026-02-28 | 2026-02-28 | ✅ | El estilo `SMART` baja el día al último de febrero (2026 no es bisiesto) |
| 6 | 2024-02-29 | 2024-02-29 | ✅ | `STRICT` acepta el 29 de febrero de un año bisiesto |
| 7 | invalida | invalida | ✅ | `STRICT` rechaza el 29 de febrero de 2026 |
| 8 | atrapada | atrapada | ✅ | `DateTimeParseException` es hija de `DateTimeException`, así que su `catch` la atrapa |

Salida real completa: `[1] 2026-12-31`, `[2] sin formatter`, `[3] 2026-03-07`, `[4] falla`, `[5] 2026-02-28`, `[6] 2024-02-29`, `[7] invalida`, `[8] atrapada`.

Otros resultados del Día A: la pausa de comprensión ([6] frente a [7], 2026-10-20) la explicó bien: `SMART` devuelve el último día del mes y `STRICT` exige que la fecha exista; puso el rango del día y del mes en el paso Leer, y es del paso Resolver (el mensaje cambia: `at index N` frente a `Invalid value for DayOfMonth`). La variación `LecturaFechas` (`leer` con `try`/`catch`, formatters `normal` y `estricto`, 2026-10-27) salió con las 7 predicciones iguales a la salida real; antes necesitó aclaraciones sobre qué hace `leer` (convierte el éxito o la excepción de `parse` en un `String`, sin `if`) y sobre cómo pasar los formatters como argumento. Riesgo: `catch (Exception)` (ver índice), corregido en la sesión. Estilo (no entra): paréntesis en el `return`. El Día A se partió en dos días (20 y 27 de oct).

### Sesión 12 · Día B (2026-10-27)

**Busca el error (5 min):** `leerValidas` con `validas = new ArrayList<>();` dentro del `try` (familia 6, reemplaza en vez de agregar): con `["01/10/2026", "xx", "03/10/2026"]` devuelve `[2026-10-03]`. Encontró la línea sin pistas, pero dijo que la variable se "volvía a declarar" (es una asignación, sin tipo) y añadió que faltaba el `formatter` y que el `catch` no mostraba nada (no son errores). Respondió que `validas` quedaba siempre vacía y la familia 5. Pistas: nivel 1 (recorrido a mano) y nivel 2 (qué hace el `new`) sin avance; con el nivel 3 (esqueleto con la vuelta 1 resuelta) completó bien las vueltas 2 y 3 y corrigió salida y familia. Pista máxima: 3.

**Kata-08 (`Fechas.parsear`, 3 tests), revisión:**
- Error: ninguno.
- Riesgo: ningún test comprueba el año de 4 dígitos; un patrón `d/M/u` dejaba los 3 tests en verde y `parsear("5/6/26")` devolvía `0026-06-05` (verificado con Java simple). Los tests sí atrapan un formatter sin `STRICT` (el 31/6 daría el 30/6) y uno con `dd/MM`. Doris señaló que la kata no pedía probar el año: la regla 1 decía "año de 4 dígitos", pero el mínimo de 3 tests no lo exigía; queda como Riesgo opcional.
- Estilo (opcional, no entra al registro; Doris pidió no enmarcarlo como "lo que haría un senior"): formatter como constante de la clase, revisar el `null` antes de crear el formatter, "formatear" en un comentario donde es "parsear", una comilla sobrante en el Esperado del test 3 y un `main` sin uso.
- Pistas: pista 1 en el formatter; niveles 1 a 3 en el `catch` del punto 3 (primero `catch (IllegalArgumentException)` con `return` de un `String`; con el nivel 2 y el esqueleto lo completó bien). Los tests, sin pistas. Pista máxima: 3.
- Tests: se replicaron con Java simple (verde) por falta de Maven en el chat; Doris confirmó el verde en IntelliJ (3 tests passed, 2026-10-27).

**Teach-back:** (1) acertó: ella, como programadora, lanza `IllegalArgumentException` porque el texto es un argumento inválido; precisión: también lo es si el texto no tiene la forma del patrón. (2) acertó la razón (`Exception` esconde errores de programación como `NullPointerException`), pero repite lo que Claude escribió en la revisión de la variación; precisión: `DateTimeParseException` cubre el texto con forma incorrecta y la fecha inexistente. (3) acertó: sin `STRICT`, `31/6/2026` da `2026-06-30`.

**Inglés:** `Text '5/10/2026' could not be parsed at index 0` (matiz: "no pudo ser interpretado", en pasado, y `index` es la posición desde 0) y `exception DateTimeParseException has already been caught` (faltó `already`: "ya fue capturada", que es el sentido del error).

### Sesión 11 · `java.time`: `LocalDate`, `LocalDateTime` y comparar fechas (Día A: 2026-10-19 y 2026-10-20)

**Ejercicio de cierre de la Semana 2 (cobros por comercio, a ciegas, 2026-10-19):** el código no compilaba, el `main` llegó solo hasta `map.size()` y el test no se escribió; dejó comentarios `// aquí quería...` en lo bloqueado. Bien: `record Cobro`, `hashCode` con `Objects.hash`, `@Override`, acumulación por clave con `BigDecimal`, `IllegalArgumentException` elegida sola, `compareTo` en vez de `<`, excepción propia con campo `final` y getter. Fallos: ver el índice (`equals` en clase normal, constructor con parámetros, dirección de `compareTo`, alcance de una variable, nombres del enunciado, campos `final`). Sin evaluar: `==` frente a `equals` en `String` (`contarDeComercio` quedó en blanco), string pool, `Set` con `add`, orden de los `catch` y `assertThat`. Estilo (no entra al registro): parámetro `cuenta` por `comercio`, `tope` como constante, package `sesion11` sin `_java_time`. Los errores de digitación (`jsva`, `substract`, `))`) y los imports que faltaban no entran.

Cierre de tema: predicción de 8 marcas, ejecutada (Java 21) para obtener las salidas reales. Resultado: 8/8.

| # | Tu respuesta | Salida real | | Por qué |
|---|---|---|---|---|
| 1 | 2026-11-02 | 2026-11-02 | ✅ | `plusDays` devuelve otro objeto y se descartó |
| 2 | false | false | ✅ | `==` compara referencias: son dos objetos |
| 3 | true | true | ✅ | `equals` compara año, mes y día |
| 4 | true | true | ✅ | Extremo incluido: `!fin.isBefore(inicio)` y `!fin.isAfter(fin)` dan `true`. Su explicación puso `isAfter` como primera validación; la primera es `isBefore` |
| 5 | false | false | ✅ | `fin.isBefore(fin)` es `false`: `isBefore` es estricto |
| 6 | 2026-11-06T09:05 | 2026-11-06T09:05 | ✅ | `LocalDateTime` es fecha más hora |
| 7 | "creada", y explicó que no se imprime | no se imprime | ✅ | `LocalDate.of(2026, 11, 31)` lanza `DateTimeException`; escribió el texto del `println` pero la explicación dice que no se imprime |
| 8 | invalida | invalida | ✅ | El `catch` coincide por tipo |

Salida real completa: `[1] 2026-11-02`, `[2] false`, `[3] true`, `[4] true`, `[5] false`, `[6] 2026-11-06T09:05`, `[8] invalida`.

Otros resultados del Día A: la pausa de comprensión (por qué `enRango(lunes, lunes, viernes)` da `true`) la explicó con la idea correcta del extremo incluido, pero puso el "no" dentro de `isBefore` e `isAfter` (el `!` es de `enRango`) y comparó `isAfter` con `lunes` en vez de `hasta`; se aclaró con el mismo ejemplo. La variación `Vencimientos` (`estaVencida` y `masReciente`) salió con las 6 predicciones iguales a la salida real y sin Error ni Riesgo; Estilo: `b.isAfter(a)` en vez de `compareTo`, y llaves en el `if`. En `masReciente` escribió "venc 2026-10-31" como salida (el `println` solo imprime la fecha; no entra al registro).

### Sesión 11 · Día B (2026-10-20)

**Busca el error (5 min):** `masReciente` con `LocalDate reciente = LocalDate.of(2026, 1, 1)` (familia 2, valor inicial equivocado): con una lista de dos fechas de 2025 el `if` nunca da `true` y devuelve `2026-01-01`. No lo veía; con la pista de nivel 1 (recorrido a mano con la entrada) trazó que el `if` nunca se cumple y que devuelve la fecha inicial, y luego dijo la línea y la familia. Pista máxima: 1.

**Kata-07 (`Transaccion` con `LocalDate` y `Transacciones.filtrarPorRango`, 3 tests), revisión:**
- Error: la `Entrada` del test 1 no decía el rango (`desde` y `hasta`) y en el test 2 las fechas estaban en el `Esperado` y no en la `Entrada`.
- Riesgo: ningún test tenía una transacción con fecha igual a `desde`; un filtro con `isAfter(desde)` dejaba los 3 tests en verde (verificado con Java simple y los datos de los tests 1 y 3). Y `LocalDate.of(2026, 10, 05)`: el cero a la izquierda hace un literal octal; con `09` no compila.
- Estilo (no entra al registro): el nombre `filtrarPorRangoCorrecto` no dice el caso, `hasSize` es redundante con `containsExactly`, los nombres `transaccion` y `transaccions`, y 4 transacciones sin usar en el test 2.
- Pistas: ninguna; solo pidió una lista de transacciones con datos. Pista máxima: 0. Los 3 tests los planteó sin pistas.

**Teach-back:** (1) acertó: con `isAfter(desde)` una transacción con fecha igual a `desde` quedaría fuera. (2) acertó en lo esencial (mismo orden y mismo contenido); no nombró `equals` ni que no sobren elementos. (3) lo explicó como un rango imposible; no dijo "argumento inválido" ni que la lanza el programador.

**Inglés:** `Invalid date 'FEBRUARY 30'` e `incompatible types: LocalDateTime cannot be converted to ChronoLocalDate`: tradujo bien las dos.

### Sesión 10 · Excepciones propias + kata de cierre de la Semana 2 (2026-10-17, Día A y Día B)

Cierre de tema: predicción de 8 marcas, ejecutada (Java 21) para obtener las salidas reales. Resultado: 5/8.

| # | Tu respuesta | Salida real | | Por qué |
|---|---|---|---|---|
| 1 | 150.00 | 150.00 | ✅ | 50.00 + 100.00 no supera el tope de 500.00 |
| 2 | no se imprime | no se imprime | ✅ | 250.00 + 300.00 = 550.00 supera el tope y `transferir` lanza `TopeDiarioException` |
| 3 | Tope diario superado por 50.00 | Tope diario superado por 50.00 | ✅ | El `catch` coincide por tipo y `getMessage()` trae el mensaje |
| 4 | sobrante: 50.00 | 50.00 | ✅ | El valor es correcto; agregó la etiqueta "sobrante:", que el `println` no imprime (no se registra como fallo) |
| 5 | stack trace | **no se imprime** | ❌ | `CuentaCerradaException` no coincide con el primer `catch`, pero Java sigue con el siguiente, que sí coincide |
| 6 | no se imprime | **Cuenta cerrada: 0000** | ❌ | Es el `println` del segundo `catch`, que sí se ejecutó |
| 7 | no se imprime | **Monto invalido: 0.00** | ❌ | El `main` no había terminado: monto en cero lanza `IllegalArgumentException` y su `catch` coincide |
| 8 | no se imprime | no se imprime | ✅ | Acertó por otra razón: con cuenta `null`, `cuenta.equals(...)` lanza `NullPointerException`, que el `catch` de `IllegalArgumentException` no atrapa, y ahí termina el `main` |

Salida real completa: `[1] 150.00`, `[3] Tope diario superado por 50.00`, `[4] 50.00`, `[6] Cuenta cerrada: 0000`, `[7] Monto invalido: 0.00` y luego `Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because "cuenta" is null`, con `at CierreTema.transferir(...)` y `at CierreTema.main(...)`.

Otros resultados del Día A: el recuerdo de `Abono` y `Abonos.crear` tuvo la lógica correcta (solo faltó una comilla de apertura, que no entra al registro). En la pausa de comprensión explicó bien el caso 3; en el caso 5 creyó que sin coincidencia en el primer `catch` el `main` termina, y se aclaró con la regla completa (ver índice). La variación `Depositar` (`CuentaCongeladaException` y `LimiteExcedidoException` con `exceso()`) salió con las predicciones iguales a la salida real.

### Sesión 10 · Día B (2026-10-17)

**Busca el error (5 min):** `contarPorCuenta` con `abono.cuenta() == cuenta` (familia 4, comparación equivocada). Encontró el operador y propuso `equals`, y dijo la familia: 3/3, pista 0. La salida que predijo (`0`) estaba hecha con literales; con literales iguales `==` da `true` por el string pool y la salida real es `3`. Corrigió la entrada con `new String("0001")`, donde `0` es correcto.

**Kata-06 (`Movimientos.crear`, `totalesPorTipo` y `agruparPorCuenta` con excepciones propias, 4 tests), revisión:**
- Error: la primera versión de `totalesPorTipo` no sumaba el monto (ver índice); `Entrada` copiado sin ajustar en los tests 2 y 3.
- Riesgo: el test de `totalesPorTipo` comprobaba una sola clave del `Map`; se agregó el tamaño.
- Estilo (no entra al registro): un `if` por cada nulo en `crear` para que el mensaje diga cuál era, `acumulado` en vez de `cantPorTipo`, una línea en blanco sobrante y `computeIfAbsent` para más adelante.
- Pistas: nivel 1 y 2 sin avance y nivel 3 (esqueleto) en `totalesPorTipo`; el nivel 2 repitió el 1 y no aportó nada nuevo. Pista máxima: 3. Los tests los planteó sin pistas.

**Teach-back:** (1) la excepción propia es para una regla de negocio; dijo que `IllegalArgumentException` "es para el sistema", cuando la lanza el programador ante un argumento inválido o nulo. (2) explicó que `add` suma por tipo y `put` reemplaza el valor de la clave, sin mencionar la inmutabilidad de `BigDecimal`. (3) acertó que `isInstanceOf` es el que falla si el tipo lanzado es otro. En una versión anterior de las preguntas contestó bien que, sin `catch` que coincida, el `main` termina.

**Inglés:** `4 tests passed` / `4 tests total` y `Cannot invoke "String.equals(Object)" because "cuenta" is null`: tradujo bien las dos.

### Sesión 9 · Excepciones: `throw`, `try`/`catch` y excepciones estándar (2026-10-17, Día A y Día B)

Cierre de tema: predicción de 8 marcas, ejecutada (Java 21) para obtener las salidas reales. Resultado: 7/8.

| # | Tu respuesta | Salida real | | Por qué |
|---|---|---|---|---|
| 1 | 5 | 5 | ✅ | División entera normal |
| 2 | A; no se imprime B | no se imprime B | ✅ | `dividir(10, 0)` lanza y el resto del `try` se salta |
| 3 | C b no puede ser 0 | C b no puede ser 0 | ✅ | El `catch` coincide por tipo y `getMessage()` trae el mensaje |
| 4 | D | D | ✅ | Está fuera del `try`/`catch` |
| 5 | E n invalido: -1 | E n invalido: -1 | ✅ | La excepción se lanza en `repartir`, sube por `cuota` y llega al `catch` |
| 6 | F | **no se imprime** | ❌ | La marca estaba en `println("G")`, dentro del `catch`; el `try` no lanzó nada, así que el `catch` se salta. Contestó `F`, la línea anterior |
| 7 | `NullPointerException`, nadie la atrapa y `main` termina | **`NullPointerException`** | ✅ | `largo(null)` falla con `NullPointerException`; el `catch` es de `IllegalArgumentException`, no coincide |
| 8 | no se imprime | no se imprime | ✅ | `main` terminó con el error de la marca 7 |

Salida real completa del programa: `5`, `A`, `C b no puede ser 0`, `D`, `E n invalido: -1`, `F`, `H` y luego `Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.length()" because "texto" is null`, con `at CierreTema.largo(...)` y `at CierreTema.main(...)`.

Otros resultados del Día A: la pausa de comprensión (caso 6 del tema) la explicó bien. El recuerdo de `Abono` y `Abonos.sumarMontos` tuvo la lógica correcta (solo el `package` en inglés, que no entra al registro). La variación (`sumarMontos` con `null` y `Retiros.restar`) salió sin Error ni Riesgo; los puntos de Estilo (validar primero, import sin uso, `;;`) los corrigió en la misma sesión.


### Sesión 9 · Día B (2026-10-17)

**Busca el error (5 min):** `precioConDescuento` con `porcentaje >= 100` en vez de `> 100` (familia 3, condición de más). Lo encontró, dijo qué devolvería o lanzaría con una entrada concreta y la familia: 3/3, pista 0. La segunda pregunta se reformuló a "qué devolvería o lanzaría este método con esa entrada, tal cual está", porque el primer enunciado pedía compararlo con el comportamiento correcto, que el plan no pide.

**Kata-05 (`Abonos.crear`, 6 tests), revisión:**
- Error: `isEqualTo("0001", new BigDecimal("150.5"))` en el camino feliz (recibe un solo argumento; había que comparar contra un `Abono`), y el Esperado del test del monto negativo copiado del test de la cuenta nula. Ambos corregidos en la sesión.
- Riesgo: ningún test probaba el borde de `monto <= 0` ni el del largo de la cuenta (`!= 4`); con `<` o `> 4` los 5 tests seguían en verde. Agregó el test del monto `0` (práctica opcional); queda sin test el borde del largo (cuenta de 3 caracteres).
- Estilo (no entra al registro): espacio antes de `{`, línea en blanco al abrir métodos, `.isInstanceOf(...).hasMessage(...)` en la misma línea.
- Pistas: nivel 1 en `compareTo`, nivel 2 en `isEqualTo`; el test de borde se guió con pasos (nivel 3), fuera del mínimo. Pista máxima: 2.

**Teach-back:** (1) describió solo el caso en que sí hay `catch` que coincide; al preguntarle qué pasa sin ninguno explicó bien que el `main` termina y la JVM imprime el stack trace. (2) dijo que el `catch` se decide porque "coincide", sin decir que es por tipo. (3) respondió que usó `IllegalArgumentException` "porque la pidieron en la kata"; la razón es que el programador la lanza ante un argumento inválido o nulo.

**Inglés:** `Process finished with exit code 0` y `Cannot invoke "String.length()" because "texto" is null`: tradujo bien las dos.

### Sesión 8 · `BigDecimal` para dinero (2026-10-13; el taller y la kata-04 siguen el 2026-10-14)

Cierre de tema: predicción de 10 líneas, ejecutada para obtener las salidas reales. Resultado: 8/10.

| # | Tu respuesta | Salida real | | Por qué |
|---|---|---|---|---|
| 1 | 0.9999999999999999 | **false** | ❌ | El `println` imprime `total == 1.0`, que es un `boolean`; sabía que era `false` y escribió el valor de `total` |
| 2 | 3.75 | 3.75 | ✅ | `add` conserva la mayor escala |
| 3 | 1.5 | 1.5 | ✅ | `add` devuelve un objeto nuevo y el resultado se descartó |
| 4 | false | false | ✅ | Escala 2 frente a escala 3 |
| 5 | 0 | 0 | ✅ | `compareTo` compara solo el valor |
| 6 | -1 | -1 | ✅ | `3.00` es menor que `3.75` |
| 7 | 1.00 | 1.00 | ✅ | `ZERO` (escala 0) + `0.5` (escala 1) + `0.50` (escala 2) |
| 8 | true | true | ✅ | `compareTo` da 0 |
| 9 | false | false | ✅ | Acertó dudando de la escala: `0.00` tiene escala 2 y `ZERO` tiene escala 0 |
| 10 | true | **false** | ❌ | `new BigDecimal(0.1)` recibe un `double` y trae `0.1000000000000000055511151231257827021181583404541015625` |

Otros resultados de la sesión: el recuerdo (`agruparPorCuenta` y el conteo con `getOrDefault`) tuvo la lógica correcta; solo fallaron errores de digitación (`;`, `)`, nombre de clase y `<` en vez de `>`), que no entran al registro. La variación `TotalesPorCuenta` (`Map<String, BigDecimal>`) acertó los 7 valores; el orden de `keySet()` de un `HashMap` no coincidió con la predicción (lo sabía; no entra al registro).
Mensaje real de la excepción: `Cannot invoke "java.math.BigDecimal.add(java.math.BigDecimal)" because the return value of "java.util.Map.get(Object)" is null`.

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

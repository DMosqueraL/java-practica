# Seguimiento de fallos

Objetivo: ver de un vistazo **dónde se falla** y si ya se reforzó. No es un puntaje.

- **Estado:** `pendiente` = falló y aún no ha vuelto a salir bien. `reforzado (sesión N)` = volvió a aparecer y se acertó.
- La salida real de cada ejercicio de cierre se ejecuta, no se supone.
- Qué entra y cómo se actualiza: ver "Registro de fallos" en `PLAN.md` (v1.4). La sesión 6 se reconstruyó desde su fila de Progreso y el diagnóstico desde su resultado en `PLAN.md`.

## Índice de fallos

| Concepto | Dónde falló | Por qué falla | Estado |
|---|---|---|---|
| Las claves que salen de los datos (cuentas) no se pueden poner en cero antes de acumular: no hay un `values()` de cuentas; entran con `getOrDefault` cuando aparecen. Con un `enum` sí, porque su lista de constantes es fija | Teach-back de la kata-13 (s17) | A "¿por qué en `saldoPorCuenta` no se ponen todas las cuentas en cero, como los tipos en `totalesPorTipo`?" respondió "no sé". En la Parte 1 del tema también costó ver que el primer `for` recorre `values()` y no la lista | pendiente |
| Acumular un `String` en un `for`: la variable va a los dos lados del `=` (`texto = texto + "\n" + elemento`), y el separador va dentro del `for` para que con la lista vacía no quede una línea de más | Kata-12, `Reporte.armar`, y teach-back (s16) | Primera versión: concatenó la `List` entera (salía `[..., ...]` en una línea). Segunda: `linea = resultado.reporte().get(i)` dentro del `for`, que se queda solo con la última (familia 1), y el `"\n"` fijo en el `return`, que deja una línea vacía con `reporte()` vacío. Creía que la lista tenía las líneas unidas con comas (solo pasa al imprimir la lista entera). Lo corrigió con pista 1. En el teach-back explicó a medias por qué no queda la línea vacía | reforzado (sesión 17): en el recuerdo lo escribió a ciegas con el `"\n"` dentro del `for` |
| Separar el reporte del `main`: el método que arma el reporte devuelve un `String` (se puede testear con `isEqualTo`) y solo el `main` imprime | Teach-back (s16) | A "¿por qué `armar` devuelve un `String` en vez de hacer el `println`?" respondió "no sé", aunque acababa de testear `armar` con `isEqualTo` en `ReporteTest` | reforzado (sesión 17): en el teach-back explicó que un `println` no se puede testear y un método que devuelve el valor sí |
| Un método con tipo de retorno (no `void`) termina con `return` del valor | Ejercicio de cierre de la Semana 3, `convertir` (s16) | Validó los 3 campos y el monto pero no escribió `return new Retiro(fecha, cajero, monto);`: no compila (`missing return statement`) | pendiente |
| Nombres de la lectura de archivos: `BufferedReader` y `Files.newBufferedReader` (tipo y método de Java; cuentan) | Ejercicio de cierre de la Semana 3, `resumir` (s16) | Escribió `BufferReader` y `Files.newBufferReader`, que no existen (`cannot find symbol`). La estructura (`try` con recursos, `readLine()` antes del `while` y al final) sí salió bien | pendiente |
| `HALF_EVEN` en el empate exacto va al vecino con el último dígito par: `50.125` → `50.12`; `HALF_UP` daría `50.13` | Ejercicio de cierre de la Semana 3, `main` (s16) | Predijo `80.63` como total; el real es `80.62` (`50.12 + 30.50`, verificado con Java 21 y en IntelliJ): redondeó como `HALF_UP` | pendiente |
| Lo que depende de la línea (`split`, la conversión) se calcula dentro del bucle: antes del `while` se calcula una sola vez, con la primera línea | Variación `LecturaAbonos.totalValidos` (s15) | Escribió `String[] datos = linea.split(",")` antes del `while`: sumaba `100.00` seis veces (`600.00`, verificado con Java 21). Además convertía a mano con `split(",")` y `new BigDecimal` en vez de llamar a `convertirPago`; con el `split` dentro del bucle, la línea `0003,` lanzaba `ArrayIndexOutOfBoundsException`, que el `catch (IllegalArgumentException)` no atrapa (verificado). Lo corrigió con la revisión | reforzado (sesión 15): en la kata-11 llamó a `convertir(linea)` dentro del `while`, sin pistas |
| El mensaje del `catch` se arma con el texto de entrada (`partes[0].trim()`); `parse(...)` sin clase no existe en `LineaCsv` | Recuerdo de la kata-10 (s15) | Escribió `"Fecha Inválida: " + parse(partes[0].trim())` en el `catch` de la fecha: no compilaría (`cannot find symbol`). Con `LocalDate.parse(...)` volvería a lanzar `DateTimeParseException` dentro del `catch`, donde ningún `catch` de ese `try` la atrapa, y saldría del método sin convertirse en `IllegalArgumentException` | reforzado (sesión 16): en el ejercicio de cierre de la Semana 3, el `catch` de la fecha y el del monto arman el mensaje con `partes[0].trim()` y `partes[2].trim()`, sin pistas |
| Un `catch` solo atrapa lo que se lanza dentro de su `try`: la línea que puede fallar va dentro del `try`, y el `catch` usa el texto de entrada, no la variable que no se llegó a crear | Variación `LecturaPagos.montoDePago` y kata-10 (s14) y variación `LecturaAbonos` (s15) | Dejó `new BigDecimal(partes[1].trim()).setScale(...)` antes del `try` y dentro solo `return monto;`. Predijo `Monto invalido: abc` en [5] y salió el mensaje de Java: la `NumberFormatException` se lanzó antes del `try` y la atrapó el `catch (IllegalArgumentException)` del `main`. El `catch` del método además usaba `monto`. Con la pregunta previa no lo vio; con el recorrido paso a paso y el esqueleto comparado con la kata-08 lo corrigió. En la kata-10 repitió el bloque del monto con `new BigDecimal` fuera del `try`; lo corrigió al compararlo con su `montoDePago` (pista 2) | reforzado (sesión 16): en el ejercicio de cierre de la Semana 3 dejó `new BigDecimal(...)` dentro del `try` del monto, `convertir(linea)` dentro del `try` del bucle y `linea = lector.readLine();` fuera, sin pistas. Antes, en la kata-11 dejó `convertir(linea)` dentro del `try` del bucle, sin pistas. Antes, en la misma sesión: en el recuerdo de la kata-10 dejó `new BigDecimal` dentro del `try`, sin pistas; en la variación `LecturaAbonos.totalValidos` dejó `convertirPago(linea)` antes del `try` (la carga se detenía en la línea 4 con `IllegalArgumentException: monto inválido: `) y lo corrigió con el esqueleto comentado (pista 3); en el cierre de tema acertó [5] y [6]; en el teach-back explicó que una `ArrayIndexOutOfBoundsException` corta la carga porque su `catch` no la atrapa (comparó con `IOException` en vez de con el tipo del `catch`) |
| Los métodos de `BigDecimal` (`add`, `setScale`) devuelven un objeto nuevo: el resultado se asigna (familia 1) | Kata-10 (s14) | Escribió `monto.setScale(2, RoundingMode.HALF_EVEN);` sin asignar: el monto quedaba con 3 decimales (`10.125`, verificado con Java simple). Minutos antes, en Busca el error, había encontrado sin pistas el mismo problema con `total.add(monto)` | reforzado (sesión 16): en el ejercicio de cierre de la Semana 3 asignó `monto = ...setScale(...)` y `total = total.add(...)`, sin pistas. Antes, en el recuerdo de la kata-10 asignó `setScale` a `monto`, en la variación escribió `totalValidos = totalValidos.add(...)` y en el cierre de tema, marca [3], predijo `0` con `total.add(...)` sin asignar |
| El `try` con recursos solo cierra el recurso: sin `catch` no atrapa nada, y la `IOException` sigue subiendo por el `throws` del método. En un test, la excepción lanzada dentro de la lambda la atrapa `assertThatThrownBy` | Kata-09 y teach-back (s13) y ejercicio de cierre de la Semana 3 (s16) | En el teach-back dijo que el test de 3 líneas lleva `throws IOException` porque "el `try` con recursos captura todas las `IOException`", y que la `NoSuchFileException` del test del inexistente la atrapa el `try` con recursos de `leerLineas` o un `main`. En la kata escribió en el `Esperado` "`NoSuchFileException` que es capturada por `IOException`" (lo corrigió a `NoSuchFileException`). El código del método sí estaba bien (`throws IOException` y sin `catch`) | pendiente: recaída (sesión 16). En `resumir` faltó `throws IOException` aunque el enunciado daba la firma con él y el `try` con recursos no tiene `catch` (no compila: `unreported exception`); en el `main` sí lo puso. Antes: reforzado (sesión 15) |
| Nombre de la excepción que lanza `LocalDate.parse(texto, formatter)`: `DateTimeParseException` (paquete `java.time.format`). Es un tipo de la API de Java, no un nombre propio: en un `catch` decide qué se atrapa | Recuerdo de la kata-08 (s13) | Escribió `DateTimeFormatterException`, que no existe, en el `import` y en el `catch`: no compilaría (`cannot find symbol`). La estructura del `catch` con `throw new IllegalArgumentException(...)` sí salió bien | reforzado (sesión 14): en el cierre de tema, marca [7], escribió `DateTimeParseException` como tipo del `catch`, sin pistas |
| Cada `readLine()` consume una línea aunque no se guarde lo que devuelve: la llamada N devuelve la línea N, y en un archivo de N líneas la llamada N + 1 devuelve `null` | Cierre de tema de lectura de archivos, marca [6] (s13) y Busca el error (s15) | Con `lector.readLine();` sin asignar y después `String linea = lector.readLine();`, predijo la primera línea en vez de la segunda: tomó la llamada descartada como si no avanzara. En las preguntas de control (3 llamadas descartadas + 1 guardada, y el ejemplo de `colores.txt`) contó bien las llamadas | reforzado (sesión 16): en el ejercicio de cierre de la Semana 3, con el encabezado descartado por un `readLine()` sin asignar, predijo bien los números de las inválidas (`línea 3`, `4`, `5` y `7`). Antes: recaída (sesión 15) en Busca el error, donde contó el encabezado como procesado |
| Recorrer un `for` a mano, vuelta por vuelta, para saber qué contiene una variable al final: `validas = new ArrayList<>()` dentro del `for` crea una lista nueva en cada vuelta y descarta la anterior (familia 6, Riesgo) | Busca el error (s12) | En `leerValidas` respondió que `validas` quedaba siempre vacía y puso la familia 5 (tipo o precisión). Con la pista 1 (recorrido a mano) y con el nivel 2 (qué hace el `new` y que el `add` va después) siguió igual; con el esqueleto parcial (nivel 3) completó bien las vueltas 2 y 3 y corrigió la salida (`[2026-10-03]`) y la familia (6). La misma familia ya había salido en la kata-03 (`put` que reemplaza) | reforzado (sesión 13): en Busca el error (`marcar` con `new ArrayList<>()` dentro del `for`) dio la línea, la salida vuelta por vuelta y la familia 6, sin pistas |
| Un entero con cero a la izquierda es un literal octal: `05` vale 5, pero `08` y `09` no compilan (Riesgo) | Kata-07 (s11) | Escribió `LocalDate.of(2026, 10, 05)`, `01` y `02`: funcionan porque 01 a 07 valen lo mismo en octal, pero con `09` no compila. Se escribe `5`, `1`, `2` | reforzado (sesión 12): en el recuerdo de la kata-07 escribió `LocalDate.of(2026, 9, 9)` y `(2026, 10, 5)` sin cero a la izquierda |
| Constructor de una clase normal: lleva su lista de parámetros; `public Nombre {` sin paréntesis es el constructor compacto del `record` | Ejercicio de cierre de la Semana 2, `MontoExcedidoException` (s11) | Escribió `public MontoExcedidoException {` y usó `exceso` en `super(...)` sin declararlo; en la s10 sí lo escribió bien (`CuentaInvalidaException(String cuenta)`) | pendiente |
| Dirección de `compareTo`: `a.compareTo(b) > 0` significa `a > b`; "supera el tope" es `monto.compareTo(tope) > 0` | Ejercicio de cierre de la Semana 2, `crear` (s11) y ejercicio de cierre de la Semana 3, `convertir` (s16) | Escribió `monto.compareTo(tope) < 0`: habría lanzado `MontoExcedidoException` con `100.50` (familia 3, condición invertida). Sí usó `compareTo` y no `<` | pendiente (s16: en el ejercicio escribió `partes[2].trim() <= 0`: `<=` sobre un `String`, que no compila; sabía que iba `compareTo` pero no contra qué comparar (`BigDecimal.ZERO`). En la variación del mismo día, minutos después de ver la regla `a > b` → `a.compareTo(b) > 0`, escribió bien `deposito.monto().compareTo(mayor) > 0`; se confirma en otra sesión) |
| Alcance de una variable: lo declarado dentro de un bloque (`for`, `try`) no existe fuera de él; si se usa después, se declara antes del bloque | Ejercicio de cierre de la Semana 2, `totalPorComercio` (s11) y ejercicio de cierre de la Semana 3, `resumir` (s16) | Devolvió `totalCobroPorComercio`, declarada dentro del `for`, en vez del `Map` `totalPorComercio` | pendiente: recaída (sesión 16). Declaró `total` dentro del `try` con recursos y lo usó en el `return` de abajo (`cannot find symbol`). Antes: reforzado (sesión 12) |
| Usar los nombres del enunciado (campos del `record`, método pedido) | Ejercicio de cierre de la Semana 2 (s11) | `cobro.cuenta()` y `cobro.cuenta` cuando el `record` tiene `comercio` (nombre copiado de la kata-06), y `totalPorComercios` en vez de `totalPorComercio`: el `main` no habría compilado | reforzado (sesión 12): en el recuerdo usó los nombres del enunciado (`Transaccion`, `cuenta`, `monto`, `fecha`, `Transacciones`, `filtrarPorRango`, `transacciones`, `desde`, `hasta`) |
| Una clase usada como clave de `HashSet`/`HashMap` lleva campos `final` (Riesgo) | Ejercicio de cierre de la Semana 2, `Comercio` (s11) | Campos `private String` sin `final`: si `codigo` cambia con el objeto ya dentro de un `HashSet`, cambia su `hashCode` y el set no lo encuentra | pendiente |
| `String` se compara con `equals`, no con `==` | Diagnóstico y ejercicio de cierre de la Semana 1 (s6), Busca el error (s10) | `==` compara referencias; `equals` compara el contenido | reforzado (sesión 10): en Busca el error identificó `==` como el error y propuso `equals`, sin pista |
| Leer qué devuelve cada llamada o expresión (`.size()` sobre el resultado de un método; `==` dentro de un `println`) | Ejercicio de cierre de la Semana 1 (s6), cierre de tema BigDecimal, línea 1 (s8), kata-04 (s8) y cierre de tema Excepciones, marca [6] (s9) | Se predijo la lista donde el método devuelve un `int`; en la s8 se escribió el valor de `total` en vez del `boolean` de `total == 1.0` (sabía que era `false`; escribió primero la explicación). En la kata-04 creyó que un método que arranca con `total = null` lanzaría `NullPointerException` con la lista vacía; con la lista vacía el `for` no da ninguna vuelta, así que nunca se llama `total.add` y devuelve `null`. En la s9 contestó `F` (lo que imprime la línea anterior) en vez de la marca [6], que estaba en `println("G")` dentro del `catch`; ese `catch` no se ejecuta porque el `try` no lanzó nada. Su explicación ("no hay ningún llamado a algún método") era correcta | pendiente (s16: en el ejercicio de cierre de la Semana 3 escribió `total.add(convertir(linea))`: `convertir` devuelve un `Retiro`, no un `BigDecimal` (`incompatible types`); faltaba `.monto()`) |
| Sintaxis de `assertThat(...)` | Ejercicio de cierre de la Semana 1 (s6) y kata-05 (s9) | Paréntesis mal cerrado, argumento de tipo equivocado y valor esperado incorrecto. En la kata-05: `isEqualTo("0001", new BigDecimal("150.5"))` con dos argumentos, cuando recibe uno solo (había que comparar contra un `Abono`) | reforzado (sesión 11): en la kata-07 escribió bien `assertThat(...).hasSize(...)`, `containsExactly(...)` y `assertThatThrownBy(...).isInstanceOf(...).hasMessage(...)` |
| El `catch` se decide por tipo; sin `catch` que coincida el programa termina con stack trace; `IllegalArgumentException` es la que lanza el programador ante un argumento inválido o nulo | Teach-back de la kata-05 (s9); cierre de tema de Excepciones propias, marcas [5] a [7], y teach-back (s10) | Describió solo el caso en que sí hay `catch` que coincide, sin decir que el método termina en el `throw` ni que la excepción sube por la pila; dijo que el `catch` "coincide" sin decir que es por tipo; y usó `IllegalArgumentException` "porque la pidieron" en vez de por argumento inválido o nulo. Con la pregunta de apoyo explicó bien qué pasa sin `catch` En la s10 creyó que, si el primer `catch` no coincide, el `main` termina sin mirar los demás: en [5] predijo stack trace, cuando Java revisa los `catch` en orden y ejecuta el primero que coincide ([6] y [7] sí se imprimen). Acertó [8] por otra razón: era un `NullPointerException` que el `catch` de `IllegalArgumentException` no atrapa. En el teach-back dijo que `IllegalArgumentException` "es para el sistema"; la lanza el programador, mientras que la JVM lanza `NullPointerException` y `ArithmeticException`. | pendiente (s10: sigue) (s11: en el cierre de `java.time` acertó que el `try` salta al `catch` por `DateTimeException`; el orden de varios `catch` y `IllegalArgumentException` no se evaluaron porque el `main` del ejercicio de la Semana 2 quedó en blanco; en la kata-07 usó `IllegalArgumentException` para nulos y rango invertido y en el teach-back la justificó como rango imposible, sin decir "argumento inválido" ni que la lanza el programador) (s12: en el recuerdo de la kata-07 justificó `IllegalArgumentException` como "argumento inválido o nulo, que lanza el programador", pero con una frase casi igual a la que Claude le había escrito minutos antes, así que no se cuenta como reforzada; en la variación `LecturaFechas` escribió `catch (Exception)` en vez de `DateTimeParseException` (Riesgo: atrapa cualquier error, y `leer(null, normal)` devolvía `ERROR null` en vez de fallar con `NullPointerException`) y lo corrigió en la sesión; en el cierre de tema acertó [8]: `catch (DateTimeException)` atrapa una `DateTimeParseException` por ser su hija; en la kata-08 el `catch` necesitó pistas hasta el nivel 3: primero escribió `catch (IllegalArgumentException)` con `return` de un `String`, y con el esqueleto completó `catch (DateTimeParseException)` con `throw`; en el teach-back explicó con sus palabras que ella, como programadora, lanza `IllegalArgumentException` porque el texto es un argumento inválido, y que `catch (Exception)` esconde errores como `NullPointerException`, pero esto último repite lo que Claude escribió en la revisión de la variación, así que el concepto sigue pendiente) (s13: en el recuerdo de la kata-08 escribió solo el `catch` con `throw new IllegalArgumentException(...)`, que en la kata necesitó pista 3, pero con un tipo inexistente, `DateTimeFormatterException` (ver su fila); en el cierre de tema, marca [8], predijo que `catch (IOException)` no atrapaba `NoSuchFileException`, que es su hija, y que el `main` terminaba con stack trace, cuando en la s12 acertó el caso equivalente con `DateTimeException`; en la pregunta de control acertó que `catch (NoSuchFileException)` también imprime `[8] atrapada`) (s14: en el cierre de tema acertó [6], `catch (IllegalArgumentException)` atrapa la `NumberFormatException` de `new BigDecimal("1,50")` por ser su hija, y [8], la `ArrayIndexOutOfBoundsException` no es de esa familia y el `main` termina; no se evaluó quién lanza `IllegalArgumentException`) |
| Dos `String` literales iguales comparten objeto (string pool): `==` da `true`; solo `new String(...)` o el texto leído en ejecución da `false` | Busca el error (s10) | Predijo `0` para `contarPorCuenta` con `==` y la entrada hecha con literales (`"0001"` y `"0001"`); la salida real es `3`, porque los literales iguales son el mismo objeto. Lo corrigió usando `new String("0001")`. Importa para la Semana 3: el texto de un CSV no es literal | reforzado (sesión 14): predijo `false` para `==` sobre el texto que sale de `split` en la variación ([7]) y en el cierre de tema ([2]) |
| Acumular un total por clave en un `Map<_, BigDecimal>`: `getOrDefault` + `add` + `put` | Kata-06, `totalesPorTipo` (s10) | La primera versión creaba la clave con un valor inicial pero nunca sumaba el monto, y dejaba una variable `total` fuera del `for` sin usar. Con el nivel 1 no llegó; con el esqueleto (nivel 3) lo completó bien. Había usado el patrón en la variación `TotalesPorCuenta` (s8). En el teach-back explicó que `add` suma y `put` reemplaza el total, sin mencionar que `BigDecimal` es inmutable | reforzado (sesión 11): en el ejercicio de cierre de la Semana 2 escribió `getOrDefault(..., BigDecimal.ZERO)` + `add` + `put` con el resultado reasignado |
| Un nombre de `package` no admite guiones | Ejercicio de cierre de la Semana 1 (s6) | Sigue las reglas de los identificadores: letras, dígitos, `_` y puntos entre partes | reforzado (sesión 12): en el recuerdo escribió `package dev.doris.sesion11_java_time;` sin guiones |
| `@Override` se escribe con O mayúscula | Recuerdo de `Pago` (s7) | Java distingue mayúsculas: `@override` no existe | reforzado (sesión 11): escribió `@Override` bien en `equals` y `hashCode` |
| Un constructor `private` impide `new` desde fuera de la clase | Recuerdo de `Pago` (s7) | `private` limita el acceso a la propia clase | pendiente |
| `Arrays.asList` devuelve una lista de tamaño fijo: `add` lanza `UnsupportedOperationException` | Diagnóstico (2026-10-06) | La lista está respaldada por un arreglo; no admite agregar ni quitar | pendiente |
| Plantear un test: qué comprobar | Kata-02 (s6), tema de la s7, Kata-03 (s7) y kata-05 (s9) y kata-06 (s10) y kata-07 (s11) y kata-12 (s16) | `contains` suelto no ve elementos de más; falta comprobar cuántos; el test de lista vacía no llamaba al método; datos copiados sin ajustar. En la kata-03: "Comprueba" mezclaba entrada con salida, "Por qué" describía una situación en vez de un error posible, entrada sin los movimientos, y restos copiados de un caso a otro. En el taller y la kata-04 (s8): Caso y Entrada genéricos, "Comprueba" describía el método, planteamientos copiados entre las versiones B y C; sí localizó bien el error de B y C. En la kata-04, primera versión del Test 3: usó montos `double` y comprobó "no es 0.3", así que el test pasaba igual con el método bueno y con uno que suma con `double`, y el nombre del test no decía lo que hacía; el "Atrapa" del Test 2 no sabía qué método erróneo imaginar. En la kata-05 (s9): un Esperado copiado de otro test sin ajustar, y las condiciones `monto <= 0` y `largo != 4` probadas solo con valores lejos del límite, sin test de borde; dice que lo difícil es hallar la entrada que atrapa un error En la kata-06 (s10): `Entrada` copiado sin ajustar en dos tests, y el test de `totalesPorTipo` comprobaba una sola clave del `Map`, sin el tamaño (mismo patrón de "falta comprobar cuántos" de la kata-02, s6). | pendiente (s9: en la kata-05 baja a pista máxima 2 y plantea los tests casi sola; siguen pendientes el test de borde y el "Atrapa") (s10: planteó los 4 tests sin pistas e incluyó el monto en cero; siguen los datos copiados sin ajustar y comprobar solo una parte del resultado; el "Atrapa" no se practicó) (s11: kata-07 sin pistas; comprobó cuántos y cuáles elementos; falló la `Entrada` del test 1, que no incluía el rango, y puso las fechas en el `Esperado` del test 2; ningún test probó el borde `desde`) (s12: kata-08 con los 3 tests planteados sin pistas, con Entrada y Esperado completos y sin datos copiados; Riesgo: ningún test comprueba que el año tenga 4 dígitos, y un patrón `d/M/u` dejaba los 3 tests en verde, verificado con Java simple) (s14: kata-10 con 9 tests planteados sin pistas, con `Entrada` y `Esperado` concretos y casos de borde del redondeo; falló la `Entrada` del test 3, copiada sin ajustar: `5/6/2026` en el comentario y `7/6/2026` en el código) (s16: kata-12 con los 3 casos definidos en el enunciado a pedido; comprobó el `String` con `contains`, que deja verde un texto con una línea de más (verificado con su versión anterior de `armar`), y lo cambió a `isEqualTo`; el `Esperado` del test 1 quedó copiado sin ajustar (`Inválidas: 0` con 3 inválidas) y el del test 3, vacío; los corrigió en la revisión) |
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
| 09 (s13) | 3 en verde en IntelliJ (JUnit 5 + AssertJ) sobre archivos de `src/test/resources/sesion13/`: archivo de 3 líneas (`containsExactly`), archivo vacío (`isEmpty`) e inexistente (`assertThatThrownBy` con `isInstanceOf(NoSuchFileException.class)`). Sin "Atrapa" (opcional) | 0: sin pistas. Hubo dudas que no son pistas (dónde estaba `src/test/resources`, qué formato devolver) y una revisión del método antes de los tests | familia 6 (reemplaza en vez de agregar: `new ArrayList<>()` dentro del `for`); sí lo encontró, pista 0, con la salida y la familia correctas | Ninguno: no hubo un error real que convertir en test. Sin Riesgo en la revisión: los 3 tests atrapan un método que se salte o reemplace líneas, que devuelva `null` con el archivo vacío o que atrape la `NoSuchFileException` |
| 10 (s14) | 9 en verde en IntelliJ (JUnit 5 + AssertJ, `assertThatThrownBy`): línea válida sin espacios, línea válida con espacios y fecha de dos dígitos, monto de 3 decimales que baja (`10.525` → `10.52`), monto de 3 decimales que sube (`10.575` → `10.58`), columnas de menos, línea vacía, fecha inexistente, tipo desconocido y monto no numérico. Sin "Atrapa" (opcional) | 2: pidió revisar `convertir` antes de los tests y Claude señaló los dos errores del monto y que los comparara con `montoDePago`. Los tests, sin pistas | familia 1 (no acumula: `total.add(monto)` sin reasignar); sí lo encontró, pista 0, con la salida (`0`) y la familia correctas | `setScale` sin asignar (familia 1): lo atrapan los tests 2, 3 y 4. `new BigDecimal` fuera del `try`: lo atrapa el test del monto no numérico (`hasMessage`). Sin Riesgo en la revisión |
| 11 (s15) | 3 en verde en IntelliJ (JUnit 5 + AssertJ) sobre archivos de `src/test/resources/sesion15/`: todo válido (`hasSize(4)` y reporte `isEmpty`), mezcla (`containsExactly` de las 3 transacciones válidas y de los 4 textos del reporte) y solo encabezado (las dos listas vacías). Sin "Atrapa" (opcional) | 2: en el `assertThat` sobre el `record` (`hasSize` no aparecía): concepto de sacar las listas con `validas()` y `reporte()`. Los archivos `todo-valido.csv` y `mezcladas.csv` los creó Claude a pedido (datos, no pista); dudas sobre rutas e imports | familia 2 (valor inicial equivocado: `int numero = 0;`); línea y familia sin pistas, salida ❌: contó el encabezado como procesado y no corrió los números (real: `línea 2`, `línea 4` y `línea 5`) | Ninguno en la kata. La corrección T05 de la kata-10 (`split(",", -1)`) se convirtió en el test `convertirLineaConMasColumnas`; familia 8 (la API corrige o descarta sin avisar), agregada al catálogo en v1.18. Sin Riesgo en la revisión: los tests atrapan no saltar el encabezado, `numero` desde 0, el `try` fuera del bucle, el `readLine()` dentro del `try`, la falta de `setScale` y saltarse la última línea |
| 12 (s16) | 3 en verde en IntelliJ (JUnit 5 + AssertJ), sin archivos, con el `ResultadoCarga` creado en el test: sin inválidas (2 líneas), todo vacío (`"Válidas: 0\nInválidas: 0"`) y 3 inválidas en orden; `isEqualTo` sobre el texto completo. Package `app_transacciones` completo: 16 tests passed. Sin "Atrapa" (opcional) | 1: en `Reporte.armar` (acumular el texto). La primera pista 1 no atendió el bloqueo y no cuenta (v1.25); la que sirvió la dio Claude sin que se pidiera subir de nivel y cuenta como nivel 1. Antes no se hizo la pregunta diagnóstica (nivel 0). Los datos de los tests (`t1`, `t2` y el `reporte`) los dio Claude a pedido (datos, no pista) | familia 3 (condición invertida: `compareTo(minimo) < 0` cuando se pedía "mayor o igual"); línea, salida (`1`) y familia sin pistas. Duda entre la 3 y la 4 por ambigüedad del catálogo (resuelta en v1.24) | Quedarse con la última línea (familia 1): lo atrapa `reporteExitosoConValidasEInvalidas` (3 inválidas). El `"\n"` de más con la lista vacía (sin familia del catálogo): lo atrapan `reporteSinInvalidas` y `reporteConDatosVacios` con `isEqualTo`; con `contains` quedaban en verde (verificado) |
| 13 (s17) | 3 en verde en IntelliJ (JUnit 5 + AssertJ), sin archivos: `totalesPorTipo` con lista vacía (2 claves en 0), `totalesPorTipo` con 4 transacciones (`DEBITO` 30.75 y `CREDITO` 150.00) y `saldoPorCuenta` con las mismas (3 claves; `0001` 89.75, `0002` -20.50 y `0003` 50.00); `hasSize`, `get` e `isEqualByComparingTo`. Package `app_transacciones` completo: 19 tests passed. Sin "Atrapa" (opcional) | 0: sin pistas. Una aclaración que no es pista (buscaba `TipoTransaccion` en `src/test`). La parte 3 (`App`) la hizo después de la revisión porque la había olvidado | familia 7 (se salta un elemento: `for (int i = 1; ...)`); línea, salida (2 claves: Viajes 40.00 y Ahorro -30.50) y familia sin pistas | Ninguno: no hubo un error real que convertir en test |

## Detalle por sesión

### Sesión 17 · Día B (2026-11-12)

**Busca el error (5 min):** `saldoPorBolsillo` con `for (int i = 1; ...)` (familia 7) sobre Ahorro `INGRESO 100.00`, Viajes `INGRESO 40.00` y Ahorro `GASTO 30.50`. Línea 3 ✅, 2 claves con Viajes = `40.00` y Ahorro = `-30.50` ✅ (verificado con Java 21), familia 7 ✅.

**Kata-13 (`CalculoTransacciones` + `CalculoTransaccionesTest` + cálculo en `App`, en `app_transacciones`), revisión:**
- `totalesPorTipo` (claves en cero con `values()` y acumulado) y `saldoPorCuenta` (`if`/`else` con `add`/`subtract` asignados y `put` después): sin Error ni Riesgo.
- Tests: los 3 casos del enunciado con los valores esperados correctos. Sin Error ni Riesgo.
- `App`: llama a los dos cálculos con `resultado.validas()`, imprime después del reporte, recorre `TipoTransaccion.values()` y `saldos.keySet()`. Predicción de los valores correcta (las cuentas salen en el orden `0002`, `0003`, `0001`; vale cualquier orden). La hizo después de la revisión porque había olvidado esa parte del enunciado.
- Estilo: `getOrDefault` en `totalesPorTipo`, donde basta `get` porque las claves ya están en cero; el Esperado del test 3 no menciona las 3 claves que comprueba `hasSize(3)`.
- No importa nada de los packages `sesionNN_`. Pista máxima: 0. Tests: Doris confirmó el verde en IntelliJ (3 de `CalculoTransaccionesTest`; 19 en el package, 2026-11-12).

**Teach-back:** (1) ❌ "no sé" a por qué las cuentas no se ponen en cero antes de acumular (no hay `values()` de cuentas; salen de los datos). (2) ✅ los métodos devuelven el `Map` porque un `println` no se puede testear. (3) ✅ el `keySet()` de un `HashMap` no asegura el orden.

**Inglés:** `Process finished with exit code 0` (bien: "proceso finalizado sin errores; un código distinto de 0 es error"; dio el significado, que es válido; complemento: *exit code* es "código de salida") y `cannot find symbol` (bien: "no se puede encontrar el símbolo"; matiz: *symbol* es un nombre del código, como una variable, un método o una clase, que el compilador no reconoce).

### Sesión 17 · Cálculos del reporte: totales por tipo y saldo por cuenta (Día A: 2026-11-11 y 2026-11-12)

**Recuerdo (kata-12: `Reporte.armar` y el `main` de `App`):** sin Error ni Riesgo. `Reporte.armar` con la variable a los dos lados del `=` y el `"\n"` dentro del `for` (concepto del índice, reforzado). Agregó una versión con for-each, también correcta (Estilo: es más clara cuando el índice no se usa; dos métodos con la misma firma no compilan juntos). No cuentan: la `,` en el `for`, el `)` que faltaba y el `;` del `package`.

**Tema** (3 partes, con el código en el chat y en `sesion17_calculos`, ejecutadas en IntelliJ):
- Parte 1 (totales por tipo con todas las claves en cero con `values()`): la primera presentación describió el código sin mostrarlo en el mensaje (Doris lo señaló; v1.27) y no se entendió. Se explicó vuelta por vuelta y con un ejemplo nuevo sin `for` (`Parte1SinFor`, dos "planillas"). Pausa (por qué basta `get` en vez de `getOrDefault`): parcial, "el primer `for` agrega los tipos que entren"; faltaba que recorre `values()` y no la lista. Control 1 (claves al terminar el primer `for` con un solo `GASTO`): respondió 1, pensando en un `for` sobre la lista (la pregunta admitía esa lectura). Control 2 (valor de `INGRESO` al terminar): ✅ `0`.
- Parte 2 (saldo por bolsillo con `if`/`else`, `add`/`subtract` y el `put` después): pausa (con el `put` solo dentro del `if`, ¿saldo de "Mercado"?): respondió `0`; el real es `null` (`getOrDefault` solo lee y, sin `put`, la clave no existe). Control al día siguiente (cuántas claves): ✅ 2.
- Parte 3 (tests de un Map: `hasSize`, `get` e `isEqualByComparingTo`): 3 tests passed en IntelliJ. Pausa (`isEqualTo(new BigDecimal("0.00"))` con la lista vacía): ✅ rojo por la escala; atribuyó el `ZERO` al `getOrDefault` de `saldoPorBolsillo` en vez del `put` del primer `for` de `totalesPorTipo`.

**Variación `CalculoNatillera`** (`TipoAporte`, `Aporte`, `totalesPorTipo` y `saldoPorSocio`, con 2 tests): 2 tests passed en IntelliJ, sin pistas. Error y Riesgo: ninguno. Estilo: el nombre `calculoNatilleraTotalPorTipo` no dice el caso (lista vacía). Sin reescritura (no hubo pista 3).

**Cierre de tema** (`CierreDeTema`, en `recuerdos/sesion17/CierreDeTema.txt` y en el package; salidas ejecutadas con Java 21). Resultado: 8/8.

| Línea | Respuesta de Doris | Salida real | | Por qué |
|---|---|---|---|---|
| [1] | `35.75` | `35.75` | ✅ | Ingresos: `25.50 + 10.25` |
| [2] | `45.50` | `45.50` | ✅ | Gastos: `40.00 + 5.50` |
| [3] | `1` | `1` | ✅ | Sin poner las claves en cero, solo entra `GASTO` (el único tipo que llega) |
| [4] | `null` | `null` | ✅ | Nunca hubo `put(INGRESO, ...)`; `getOrDefault` solo lee |
| [5] | `0` | `0` | ✅ | El primer `for` puso `INGRESO` en `BigDecimal.ZERO`, que se imprime con escala 0 |
| [6] | `-29.75` | `-29.75` | ✅ | "Ahorro": `0 − 40.00 + 10.25`; el primer movimiento es un gasto y resta desde cero |
| [7] | `2` | `2` | ✅ | Una clave por bolsillo ("Ahorro" y "Viajes"), aunque cada uno tenga 2 movimientos |
| [8] | `false` | `false` | ✅ | `equals` compara valor y escala: `0` frente a `0.00` |


### Sesión 16 · Día B (2026-11-10)

**Busca el error (5 min):** `contarDesde` con `compareTo(minimo) < 0` cuando se pedía "mayor o igual" (familia 3), sobre `100.00`, `50.50` y `25.25` con mínimo `50.00`. Línea 4 ✅, devuelve `1` ✅ (verificado con Java 21), familia 3 ✅. Ambigüedad: dudó entre la 3 y la 4 porque los ejemplos del catálogo no cubrían el caso (resuelto en v1.24 con el criterio 3 frente a 4).

**Kata-12 (`Reporte.armar` + `ReporteTest` + `App` en `app_transacciones`), revisión:**
- `Reporte` se creó primero en `sesion16_app_consola` (importando `ResultadoCarga` de `app_transacciones`); se movió con Move Class.
- Error (corregido): concatenar la `List` entera; luego `linea = ...get(i)` que deja solo la última línea (familia 1) y el `"\n"` fijo en el `return`, que deja una línea vacía con `reporte()` vacío. Ver índice.
- Riesgo (corregido): `contains` en los tests dejaba verde la versión con la línea de más; pasó a `isEqualTo`.
- Estilo: comprobaciones de `size()` sobre el `ResultadoCarga` creado en el test (las quitó); `Paths.get` en vez de `Path.of`.
- `App`: argumentos, mensaje de uso, `try` con `cargar` → comentario del cálculo (s17) → `Reporte.armar`, `catch (IOException)`. Predicción de la salida con `transacciones.csv` ✅ y sin argumentos ✅. Nota (no cuenta): al mensaje del `catch` le falta `": "`.
- Usó un `for` clásico con índice (visto en la sesión 3); funciona.
- Pista máxima: 1. Tests: Doris confirmó el verde en IntelliJ (3 de `ReporteTest`; 16 en el package, 2026-11-10).

**Teach-back:** (1) ❌ "no sé" a por qué `armar` devuelve un `String` en vez de imprimir (para testearlo con `isEqualTo` y para que solo el `main` toque la consola). (2) ✅ con una ruta inexistente imprime el mensaje del `catch` con la ruta. (3) Parcial: "es por el `for` y el salto de línea"; faltó que el `"\n"` va dentro del `for` y con la lista vacía el `for` no da vueltas.

**Inglés:** `Main method must return a value of type void in class Trampa7` (bien: "el método main de la clase Trampa7 debe devolver un valor de tipo void"; matiz: `void` significa que no devuelve nada) y `Copy specified files` (tradujo "copia específica de archivos"; `Copy` es el verbo en imperativo y `specified` califica a `files`: "copiar los archivos especificados", es decir, los que seleccionaste).


### Sesión 16 · Estructura de una app de consola (Día A: 2026-11-09)

**Ejercicio de cierre de la Semana 3 (retiros de cajero, a ciegas, ~27 min; partes 1 a 3 de 4):** en `recuerdos/sesion16/EjercicioSemana3.txt`; solución en `EjercicioSemana3-solucion.txt`.
- Bien: `split(",", -1)` y `length != 3`, formatter estricto, `fecha` declarada antes del `try`, `catch (DateTimeParseException)` con `throw` y el texto de entrada, `new BigDecimal(...).setScale(2, HALF_EVEN)` dentro del `try` y asignado, `convertir(linea)` dentro del `try` del bucle, `readLine()` fuera, `total = total.add(...)` asignado, contador de línea y números del reporte (3, 4, 5 y 7).
- Error: `partes[2].trim() <= 0` (`bad operand types`); falta el `return` de `convertir`; `BufferReader` / `newBufferReader` no existen; `total.add(convertir(linea))` sin `.monto()`; `total` declarado dentro del `try` y usado fuera; `resumir` sin `throws IOException`. Ver índice.
- Nota (no entra): faltaban imports de `Path`, `Files`, `BufferedReader` e `IOException`.
- Parte 3: total ❌ (`80.63`; real `80.62`, `HALF_EVEN`); reporte ✅ (precisión: `println` de una `List` sale en una línea con `[ ]`). Parte 4: no alcanzó.
- Repaso: reescribió en IntelliJ el final de `convertir`, `resumir` y `desde`; salida en IntelliJ `80.62`, el reporte y el filtro, correctos.

**Arranque de la app:** Refactor → Copy Class a `dev.doris.app_transacciones` de `Transaccion`, `TipoTransaccion`, `LineaCsv`, `ResultadoCarga`, `CargaTransacciones`, `LineaCsvTest` y `CargaTransaccionesTest`. IntelliJ dejó 3 líneas de `LineaCsvTest` con el nombre completo `dev.doris.sesion14_parseo_csv.Transaccion` (comparaba contra la clase vieja); se corrigió con Reemplazar. Verde en IntelliJ: 13 tests passed. Sin referencias a `dev.doris.sesion…` dentro de `app_transacciones`.

**Tema:** la primera presentación (todo en un mensaje) no se entendió; se rehízo en dos partes con ejecución en IntelliJ: (1) `args` y `return` en el `main` (sin argumentos `args.length = 0`; con la ruta en Program arguments, `1`); (2) lectura, cálculo y reporte en clases separadas (`AppDepositos`), con el `catch (IOException)` en el `main`.
- Pausa 1 (por qué no salió `Ruta recibida`): ✅ entró al `if` y el `return` terminó el programa.
- Pausa 2 (por qué no se ejecutaron `total` ni `armar` con un archivo inexistente): respondió "no había data"; la razón es que `leer` lanzó la excepción y se salta al `catch`. Pregunta de control (archivo con solo el encabezado): recorrió bien `leer` (lista vacía), pero dijo que se mostraría la lista; la app imprime el reporte `Depósitos: 0` / `Total: 0` (ejecutado).
- La primera variación pedía dos argumentos, que no se habían mostrado; Doris lo señaló y se cambió por `AppMayor` (un argumento).

**Variación `AppMayor`** (`mayorMonto` con `compareTo(mayor) > 0`, `armarMayor` y `main` con un argumento): predicciones 3/3 (con la ruta, sin argumentos e inexistente, con `\` de Windows). Error y Riesgo: ninguno. Estilo: `throws IOException` sobra en el `main` porque el `catch` ya la atrapa. Sin reescritura (no hubo pista 3).

**Cierre de tema** (`CierreDeTema` con 3 ejecuciones; salidas ejecutadas con Java 21, la ruta de C con `\` en Windows). Resultado: 6/6.

| Ejecución | Respuesta de Doris | Salida real | | Por qué |
|---|---|---|---|---|
| A (sin argumentos) | `[1] uso` y nada más | `[1] uso` | ✅ | `args.length` es 0, entra al `if` y el `return` termina el `main` (no llega a `[4]`) |
| B (`depositos.csv`) | `[2] Depósitos: 3` | `[2] Depósitos: 3` | ✅ | `leer` devuelve 3 depósitos |
| B | `Total: 175.75` | `Total: 175.75` | ✅ | El `"\n"` del reporte parte el texto en dos líneas |
| B | `[4] fin` | `[4] fin` | ✅ | Sin excepción no entra al `catch` y sigue después del `try` |
| C (`no-existe.csv`) | `[3] src\main\resources\sesion16\no-existe.csv` | igual | ✅ | `leer` lanza `NoSuchFileException`, se salta `[2]` y el `catch (IOException)` la atrapa por ser su hija |
| C | `[4] fin` | `[4] fin` | ✅ | Después del `catch` la ejecución sigue |


### Sesión 15 · Día B (2026-11-08)

**Busca el error (5 min):** `reportar` con `int numero = 0;` (familia 2) sobre `pagos.csv`. Dio la línea 5 y la familia 2 sin pistas. Salida ❌: respondió `[línea 1: monto inválido: monto, línea 3: ..., línea 5: ..., línea 6: ...]`; la real (Java 21) es `[línea 2: monto inválido: abc, línea 4: columnas: 1, línea 5: columnas: 3]`: el encabezado no entra al bucle y cada número sale uno menos (ver índice, `readLine()`).

**Kata-11 (`CargaTransacciones.cargar` → `ResultadoCarga`, 3 tests), revisión:**
- Preparación: `LineaCsv.convertir` pasó a `public` para usarlo desde `sesion15_cargar_csv`; `ResultadoCarga` con el esqueleto del enunciado.
- Error: ninguno. Riesgo: ninguno (ver "Testing por kata"). Estilo: nada que señalar.
- El reporte usa `"Línea N: "` con mayúscula (texto propio, no cuenta).
- Parte 3 (opcional): `main` con `transacciones.csv`; predijo `10` y `[Línea 7: Monto inválido: abc, Línea 9: fecha inválida: 31/6/2026, Línea 10: tipo inválido: RETIRO, Línea 12: columnas: 3, Línea 14: columnas: 6]` ✅ (verificado con Java 21 y la misma lógica).
- Pista máxima: 2. Tests: Doris confirmó el verde en IntelliJ (3 tests passed, 2026-11-08).

**Teach-back:** (1) ✅ una `ArrayIndexOutOfBoundsException` corta la carga porque el `catch (IllegalArgumentException)` no la atrapa; precisión: comparó con `IOException` y la comparación es con el tipo del `catch`. (2) ✅ con solo `List<Transaccion>` se pierde en qué líneas hubo error; precisión: un método devuelve un solo valor y el `record` junta las dos listas. (3) Anulada: preguntaba qué error atrapa el test de solo encabezado, que es un "Atrapa" (opcional hasta la Fase 1); Doris lo señaló y no se evalúa.

**Inglés:** `Process finished with exit code 0` (bien: "proceso finalizado con código de salida 0"; matiz: por convención, 0 significa que terminó sin error) y `exception IOException is never thrown in body of corresponding try statement` (tradujo `thrown` como "atrapada"; es "lanzada": "la excepción IOException nunca se lanza en el cuerpo de la sentencia try correspondiente"; `throw` = lanzar, `catch` = atrapar).


### Sesión 15 · Cargar el CSV completo y reportar las líneas inválidas (Día A: 2026-11-08)

**Recuerdo de la kata-10 (`LineaCsv.convertir`):** imports, `split(",")`, validación de `partes.length != 4`, formatter estricto, `fecha` declarada antes del `try`, `catch (DateTimeParseException)` con `throw`, `valueOf` dentro del `try` y monto con `new BigDecimal(...).setScale(2, HALF_EVEN)` dentro del `try` y asignado: correctos. Error: `parse(partes[0].trim())` en el mensaje del `catch` de la fecha (ver índice). Digitación (no entra): falta un `)` en esa línea y `Mono`.

**Corrección de la kata-10 (T05):** cambió `split(",")` por `split(",", -1)` en `LineaCsv.convertir` y agregó `convertirLineaConMasColumnas` (`"5/6/2026,0001,DEBITO,10.00,,"` → `IllegalArgumentException` con `columnas: 6`), con planteamiento sin pistas. Verde en IntelliJ: 10 tests passed. Preguntó cómo se usa `split` en la vida real (con `-1` cuando importa la cantidad de columnas; el separador es una regex; un CSV con comillas necesita una librería).

**Pausa de comprensión** (si `linea = lector.readLine();` pasa dentro del `try`, debajo del `add`): respondió que se comporta como `cargarCortando`. ❌ El bucle no termina: después del `catch` se sigue dentro del `while`, `linea` no cambia y la misma línea falla en cada vuelta (verificado con un freno en la vuelta 8: `línea 3`, `línea 4`... con el mismo `monto inválido: abc`). Con el recorrido paso a paso, la pregunta de control (qué se ejecuta después del `catch` en `cargar`) la respondió bien: `linea = lector.readLine();`. Antes preguntó por qué la línea 7 (`0006,3`) es válida (`3` → `3.00`) y por qué son 3 válidas y no 4 (la línea 6 `0005,20.00,` da 3 partes con `-1`).

**Variación `LecturaAbonos`** (`totalValidos` y `contar` con `record Conteo`): primera versión con `split` fuera del `while` y sin `convertirPago` (ver índice); segunda versión con `convertirPago(linea)` antes del `try` (pendiente del `catch`, ver índice); con el esqueleto comentado (pista 3) quedó bien, y `contar` salió bien sin pistas. Preguntó si el `catch` vacío cumple "se saltan sin detener la carga" (sí: lo hace la existencia del `catch`). Predicciones 3/3: `[1] 180.12`, `[2] Conteo[validas=3, invalidas=3]` y [3] `NoSuchFileException` (no imprime `[3]`; el `main` termina).

Cierre de tema: predicción de 8 marcas, ejecutada (Java 21) para obtener las salidas reales. Resultado: 8/8.

| # | Tu respuesta | Salida real | | Por qué |
|---|---|---|---|---|
| 1 | 3  0002,abc | 0002,abc | ✅ | Dos `readLine()` sin asignar consumen las líneas 1 y 2; el tercero devuelve la línea 3. El `3` de la respuesta era el número de línea |
| 2 | 3, 5 y 6 | 3, 5 y 6 | ✅ | El `catch` está dentro del bucle e imprime `numero` en cada línea inválida, y la carga sigue |
| 3 | 0 | 0 | ✅ | `total.add(...)` sin asignar: el resultado se pierde y `total` sigue en `0` |
| 4 | 7 | 7 | ✅ | `numero` empieza en 1 (encabezado) y sube una vez por cada una de las 6 líneas de datos |
| 5 | monto inválido: abc | monto inválido: abc | ✅ | El `try` envuelve el `while`: la línea 3 lo saca del bucle |
| 6 | [0001] | [0001] | ✅ | Solo entró la línea 2 antes del corte |
| 7 | atrapada en main | atrapada en main | ✅ | `primera` no tiene `catch`: la `NoSuchFileException` sube por su `throws` y la atrapa el `catch (IOException)` del `main` (es su hija) |
| 8 | NoSuchFileException | no se imprime; `Exception in thread "main" java.nio.file.NoSuchFileException: src/main/resources/sesion15/falta.csv` | ✅ | Fuera de un `try`, nada la atrapa y el `main` termina |

Salida real completa: `[1] 0002,abc`, `[2] 3`, `[2] 5`, `[2] 6`, `[3] 0`, `[4] 7`, `[5] monto inválido: abc`, `[6] [0001]`, `[7] atrapada en main` y luego `Exception in thread "main" java.nio.file.NoSuchFileException: src/main/resources/sesion15/falta.csv` (en Windows, la ruta con `\`).


### Sesión 14 · De una línea CSV a una `Transaccion` (Día A: 2026-11-02)

**Recuerdo de la kata-09 (`LectorMovimientos.leerLineas`):** completo y correcto: imports, `throws IOException`, `try` con recursos sin `catch`, primera lectura antes del `while` y la siguiente al final de cada vuelta, `return` fuera del `try`. Sin Error ni Riesgo. Digitación (no entra): `leerLines`. El pendiente de `readLine()` (llamada descartada) no se evaluó.

Cierre de tema: predicción de 8 marcas, ejecutada (Java 21) para obtener las salidas reales. Resultado: 8/8.

| # | Tu respuesta | Salida real | | Por qué |
|---|---|---|---|---|
| 1 | 4 | 4 | ✅ | `split(",")` da 4 partes; `length` sin paréntesis |
| 2 | false | false | ✅ | El texto de `split` y `trim` se crea en ejecución: no es el literal del pool |
| 3 | 20.50 | 20.50 | ✅ | `trim()` quita el espacio de `" 20.5"` y `setScale(2, HALF_EVEN)` completa con un cero |
| 4 | 0.24 | 0.24 | ✅ | Empate exacto: `HALF_EVEN` va al vecino con el último dígito par (4) |
| 5 | 0.25 | 0.25 | ✅ | `HALF_UP` sube en el empate |
| 6 | atrapada | atrapada | ✅ | `new BigDecimal("1,50")` lanza `NumberFormatException`, hija de `IllegalArgumentException` |
| 7 | `DateTimeParseException`; fecha invalida | fecha invalida | ✅ | `STRICT` rechaza el 31 de junio; tipo del `catch` escrito bien |
| 8 | no se imprime; el `main` termina con `Index 4 out of bounds for length 4` | `ArrayIndexOutOfBoundsException: Index 4 out of bounds for length 4` | ✅ | No es hija de `IllegalArgumentException`: el `catch` no coincide. Dio el mensaje y no el nombre del tipo |

Salida real completa: `[1] 4`, `[2] false`, `[3] 20.50`, `[4] 0.24`, `[5] 0.25`, `[6] atrapada`, `[7] fecha invalida` y luego `Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 4 out of bounds for length 4`, con `at dev.doris.sesion14_parseo_csv.CierreDeTema.main(...)`.

Otros resultados del Día A: la pausa de comprensión (por qué `10.125` da `10.12` y `10.135` da `10.14` con `HALF_EVEN`) la explicó bien; precisión: se mira el último dígito que se conserva y solo cuenta en el empate exacto. La variación `LecturaPagos` (`montoDePago` y `esDeCuenta`) salió con 6/7 predicciones; antes preguntó si hacía falta `trim()` en el monto (sí: `new BigDecimal(" 7.5 ")` lanza `NumberFormatException`). Error en [5]: `new BigDecimal` fuera del `try` (ver índice), corregido en la sesión con salida `Monto invalido: abc`. Pidió las líneas de entrada del `main` y Claude le dio primero el `main` completo por un malentendido.

### Sesión 14 · Día B (2026-11-02)

**Busca el error (5 min):** `totalDebitos` con `total.add(monto);` sin reasignar (familia 1): con dos débitos de `10.50` y `4.25` y un crédito devuelve `0`. Dio la línea 7, la salida `0` y la familia 1 sin pistas, y explicó que `BigDecimal` es inmutable.

**Kata-10 (`LineaCsv.convertir` → `Transaccion`, 9 tests), revisión:**
- Error: la `Entrada` del test 3 quedó copiada sin ajustar (ver índice). Durante la kata, en el bloque del monto: `new BigDecimal` fuera del `try` y el resultado de `setScale` sin asignar (ver índice); corregidos antes de los tests.
- Riesgo: ninguno con lo visto. Los tests atrapan un método sin `setScale` (test 2: `80.5` no es `equals` a `80.50`), uno con `HALF_UP` (test 3: daría `10.53`) y uno con `new BigDecimal` fuera del `try` (`hasMessage`). Fuera de lo visto: por T05, `"5/6/2026,0001,DEBITO,10.00,,"` pasa como válida (queda para Planeación).
- Estilo (no entra): `BigDecimal monto = BigDecimal.ZERO;` con un valor inicial que nunca se usa.
- La novedad (atrapar la `IllegalArgumentException` de `valueOf` y lanzar otra con el campo) salió sin pistas. Pista máxima: 2.
- Tests: Doris confirmó el verde en IntelliJ (9 tests passed, 2026-11-02).

**Teach-back:** (1) ✅ `valueOf` ya lanza `IllegalArgumentException`, pero su mensaje no dice qué campo falló. (2) ✅ con `HALF_UP`, `10.525` da `10.53`. (3) ✅ `fecha` se declara antes del `try` para poder usarla en el `return`; precisión: su alcance sería el bloque `try`.

**Inglés:** `Rounding necessary` ("se necesita redondear"; matiz: `rounding` es sustantivo, "redondeo necesario") e `Index 4 out of bounds for length 4` (bien; matiz: "índice", "fuera de los límites" y `length` es "longitud", como el campo del arreglo).

### Sesión 13 · Leer archivos: `Path`, `Files.readAllLines`, `BufferedReader` y `try` con recursos (Día A: 2026-10-28)

**Recuerdo de la kata-08 (`Fechas.parsear`):** `package`, imports de `LocalDate`, `DateTimeFormatter` y `ResolverStyle`, firma, patrón `"d/M/uuuu"` con `ResolverStyle.STRICT`, validación del nulo y `try` con `return` y `catch` con `throw new IllegalArgumentException(...)`, iguales a la sesión 12. Error: `DateTimeFormatterException` (no existe) en el `import` y en el `catch`, en lugar de `DateTimeParseException` (ver índice). Digitación (no entra): `packcage` y el texto del mensaje (`"Fecha invalida "`). Doris recordó que en el recuerdo no se exige igualdad en nombres ni textos; queda como `Para planeación` precisar que sí cuentan los tipos y métodos de la API de Java.

Cierre de tema: predicción de 8 marcas, ejecutada (Java 21) para obtener las salidas reales. Resultado: 6/8.

| # | Tu respuesta | Salida real | | Por qué |
|---|---|---|---|---|
| 1 | ruta creada | ruta creada | ✅ | `Path.of` solo guarda la ruta; no revisa si `falta.txt` existe |
| 2 | 0002;CREDITO;80.50 | 0002;CREDITO;80.50 | ✅ | `get(1)` es el segundo elemento (la lista empieza en 0) |
| 3 | false | false | ✅ | Un archivo vacío da una lista vacía, no `null` |
| 4 | 0 | 0 | ✅ | El archivo existe (vacío ≠ inexistente): no entra al `catch` |
| 5 | null | null | ✅ | En un archivo vacío, la primera llamada a `readLine()` ya devuelve `null` |
| 6 | 0001;DEBITO;150.00 | 0002;CREDITO;80.50 | ❌ | El primer `readLine()` sin asignar igual consume la línea 1; el segundo devuelve la línea 2 |
| 7 | 0003;DEBITO;20.00 | 0003;DEBITO;20.00 | ✅ | `ultima` conserva la tercera línea cuando `linea` llega a `null` |
| 8 | main termina con stack trace | atrapada | ❌ | `NoSuchFileException` es hija de `IOException`, así que `catch (IOException)` la atrapa; `println("[8] leido")` se salta |

Salida real completa: `[1] ruta creada`, `[2] 0002;CREDITO;80.50`, `[3] false`, `[4] 0`, `[5] null`, `[6] 0002;CREDITO;80.50`, `[7] 0003;DEBITO;20.00`, `[8] atrapada`.

Preguntas de control: (1) "tres llamadas y se guarda la tercera" era ambigua (Claude); Doris la leyó como 3 descartadas + 1 guardada y su recorrido (`null` en la cuarta) es correcto. Con el ejemplo nuevo de `colores.txt` (rojo, verde, azul) contó bien las 4 llamadas, pero predijo `verde y ` cuando la salida real es `verde y null`: concatenar `null` con un `String` escribe el texto `null` (en la marca [5] sí lo predijo). (2) `catch (NoSuchFileException)` → `[8] atrapada`: acertó.

Otros resultados del Día A: el ejemplo falló primero con `NoSuchFileException` porque la configuración de ejecución de IntelliJ usaba la carpeta del proyecto (`D:\dev\java-practica`) y no la del módulo; Claude había dicho lo contrario. Se arregló con Working directory = `$MODULE_WORKING_DIR$`. La pausa de comprensión (por qué termina el `while`) la respondió a medias: atribuyó el fin a `numero`; con la tabla de llamadas lo corrigió (la condición `linea != null` deja de cumplirse). La variación `LecturaMovimientos` (`contarLineas` con `BufferedReader`, `try` con recursos y `while`; `numerar` con `readAllLines` y `for` con índice) salió correcta, con 6/6 salidas predichas; antes del `throws IOException` preguntó por qué IntelliJ subrayaba las llamadas (excepción comprobada). Estilo (no entra): `numero` sin uso en `contarLineas` y `(i + 1)` sin paréntesis.

### Sesión 13 · Día B (2026-11-02)

**Busca el error (5 min):** `marcar(Path)` con `resultado = new ArrayList<>();` dentro del `for` (familia 6): con `movimientos.txt` devuelve `[> 0003;DEBITO;20.00]`. Dio la línea 4, el recorrido vuelta por vuelta y la familia 6 sin pistas (ver índice: reforzado).

**Kata-09 (`LectorMovimientos.leerLineas`, 3 tests), revisión:**
- Error: ninguno.
- Riesgo: ninguno.
- Estilo (no entra): ninguno en la versión final. En la versión previa a los tests había un `System.out.println(lista.add(linea))` (lo puso a propósito para ver el `true` de `add`, no es fallo) y un contador sin uso; los quitó.
- Pistas: ninguna. Pista máxima: 0.
- Tests: Doris confirmó el verde en IntelliJ (3 tests passed, 2026-11-02). El `Esperado` del test del inexistente decía "capturada por `IOException`" y lo cambió a `NoSuchFileException`; también le puso `throws IOException`, que compila pero sobra (la llamada va dentro de la lambda). Claude dijo por error que IntelliJ subrayaba ese `throws`: no lo hace por defecto.

**Teach-back:** (1) ❌ dijo que el test de 3 líneas lleva `throws IOException` porque el `try` con recursos captura las `IOException`; la razón es que el test llama directo a un método que declara una excepción comprobada, y el del inexistente la llama dentro de la lambda de `assertThatThrownBy`. (2) ✅ sin `try` con recursos el archivo queda abierto (hasta que termina el programa). (3) ❌ dijo que la `NoSuchFileException` la atrapa el `try` con recursos de `leerLineas` o un `main`; la atrapa `assertThatThrownBy` (ver índice).

**Inglés:** `unreported exception IOException; must be caught or declared to be thrown` (bien en lo general; matiz: `thrown` es el participio de `throw`, "lanzada", no la palabra `throws`: "debe ser atrapada o declarada como lanzada") y `auto-closeable resource lector may not be assigned` (falló el matiz: `may not` es prohibición, "no se puede", no posibilidad, y `lector` es el nombre del recurso: "al recurso auto-cerrable `lector` no se le puede asignar otro valor").

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

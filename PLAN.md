# Plan: Java 21 de cero a producción

Pista para aprender a **escribir** Java desde cero hasta funcional y reactivo. Es independiente de la teoría de la certificación 1Z0-830: esa entrena a leer código; esta, a producirlo.

- **Dedicación:** 2 h diarias, lunes a viernes (10 h/semana).
- **Duración estimada:** 24 semanas del plan. Una "semana del plan" son 5 sesiones, y cada sesión se planea en 2 días de 2 h (ver Formato de sesión), así que en calendario, por ahora, una semana del plan toma unas 2 semanas reales. Se recalcula con datos reales al cerrar la Fase 0.
- **Dominio común:** transacciones bancarias. El mismo problema evoluciona de imperativo a OO, luego a funcional y luego a reactivo.

---

## Organización de los chats

| Chat | Responsabilidad | Qué no hace |
|---|---|---|
| **Planeación** (único) | Cambios de fases, temario, ritmo o alcance; reglas de sesión; retrospectiva semanal; toda edición de las secciones de plan y reglas de este archivo | No da temas ni revisa katas |
| **Tema** (`Semana N - Tema: X`, uno por sesión) | Recuerdo o ejercicio de cierre semanal, tema nuevo, kata, revisión, registro en Progreso y mensaje de apertura de la sesión siguiente | No cambia el plan ni las reglas |
| **Sprint** (`Sprint N - <incremento>`, uno por sprint, desde la Fase 8) | Desarrollo del incremento, revisión, registro en Progreso | No define el sprint siguiente: eso se hace en Planeación |

- Si en un chat de tema surge algo de estrategia (el ritmo no alcanza, un tema necesita otro día, una regla no funciona), se anota en una línea en la columna Estado de Progreso como `Para planeación: ...` y la sesión sigue con el plan vigente.
- Cada cambio acordado en Planeación se registra en el **Changelog** (al final) con fecha y versión. El mensaje de apertura de cada sesión incluye `Plan: vX.Y` para detectar desfases.
- Las instrucciones del Proyecto en claude.ai solo dicen que se lea este archivo al iniciar. Todo lo demás (rol, pistas, revisión, fases, temario) vive aquí, así que no hay que mantener dos lugares.
- Ningún chat usa otra copia de este archivo (GitHub, docs del Proyecto, memoria) si contradice la del repo local.
- **Modelo por tipo de chat:**
  - Tema y Sprint: Sonnet 5.5.
  - Opus 5.5: chat de Planeación, revisiones de cierre de fase y temas difíciles (sobre todo el diseño de concurrencia y Reactor, Fases 4 y 5).

---

## Fases

| Semanas | Fase | Criterio de salida (lo que debes escribir sin ayuda) |
|---|---|---|
| 1–4 | **0. Fundamentos de producción** | App de consola que lee un CSV de transacciones, valida, calcula y reporta. Clases, records, enums, colecciones, excepciones, `java.time`, JUnit |
| 5–7 | **1. Diseño OO** | Refactor de esa app: interfaces, inmutabilidad, composición, generics prácticos, `equals`/`hashCode` correctos, patrones Strategy, Factory y Builder |
| 8–11 | **2. Funcional** | La misma lógica en estilo funcional: lambdas, interfaces funcionales, streams, `Collectors`, `Optional`, `sealed` + pattern matching |
| 12–13 | **3. Proyecto funcional** | Procesador de extractos: núcleo puro, efectos en los bordes |
| 14–15 | **4. Concurrencia moderna** | `ExecutorService`, `CompletableFuture`, virtual threads (puente al reactivo) |
| 16–18 | **5. Reactor puro** | `Mono`/`Flux`, operadores, manejo de errores, backpressure, `StepVerifier` |
| 19–21 | **6. Spring Boot MVC** | API REST + JPA + PostgreSQL con tests |
| 22–24 | **7. WebFlux** | La misma API en reactivo con R2DBC, comparada con MVC + virtual threads |
| 25+ | **8. Práctica continua (sprints)** | Sin fin fijo: un sprint semanal sobre un ledger de doble partida (ver Fase 8) |

> Al terminar la Fase 7: nivel profesional sólido escribiendo Java. Lo que viene después se construye en la Fase 8, con un proyecto real que crece por sprints.

---

## Distribución de la Fase 0 (propuesta, ajustable)

| Semana | Foco | Kata de cierre |
|---|---|---|
| 1 | Modelo de datos: tipos, `String` y `equals`, clases, `record`, `enum`, `List`/`ArrayList`, for-each, primer test JUnit | `Transaccion` como `record` con `enum` de tipo, lista de transacciones, filtro y suma con tests |
| 2 | Colecciones (`List`, `Map`, `Set`), `BigDecimal` para dinero, excepciones propias y `try`/`catch` | Agrupar por tipo y por cuenta, con validaciones que lanzan excepciones |
| 3 | `java.time`, lectura de archivos (`Files`, `BufferedReader`), parseo de CSV | Cargar el CSV real y manejar líneas inválidas |
| 4 | Integración: app de consola completa, reporte, tests | Criterio de salida de la fase |

### Formato del CSV (sesiones 14 a 20)

Lo usan todas las sesiones desde la 14. Ningún chat de Tema lo cambia; un cambio se decide en Planeación.

- **Archivo real:** `fundamentos/src/main/resources/transacciones.csv`. Lo crea Claude en la sesión 15, con unas 10 líneas válidas (al menos una con monto de 3 decimales) y una inválida de cada tipo, entre ellas una con columnas vacías al final.
- **Encabezado:** la primera línea es `fecha,cuenta,tipo,monto` y se salta al cargar.
- **Separador:** coma, sin comillas. El monto usa punto decimal. Puede haber espacios alrededor de cada campo (se quitan con `trim()`).
- **fecha:** día/mes/año con `DateTimeFormatter.ofPattern("d/M/uuuu")` y `ResolverStyle.STRICT`, como en la kata-08 (acepta `5/6/2026` y `05/06/2026`; rechaza `31/6/2026`).
- **cuenta:** texto, por ejemplo `0001`.
- **tipo:** nombre exacto de una constante de `TipoTransaccion` (`DEBITO` o `CREDITO`), convertido con `valueOf`.
- **monto:** se crea con `new BigDecimal(texto)`. Si tiene más de 2 decimales se redondea a 2 con `setScale(2, RoundingMode.HALF_EVEN)`; si tiene menos, `setScale(2)` completa con ceros (`10.5` → `10.50`).
- **Línea inválida:** columnas de más o de menos, incluidas las vacías al final como `5/6/2026,0001,DEBITO,10.00,,` (por eso se separa con `split(",", -1)`; una línea vacía cuenta aquí), fecha inválida, tipo desconocido o monto no numérico. El método que convierte una línea lanza `IllegalArgumentException` en todos los casos, con un mensaje que dice qué campo falló; así la carga de la sesión 15 necesita un solo `catch`.

### Semana 1: un chat por sesión

Título de cada chat: `Semana 1 - Tema: <tema>`. Cada cierre incluye el mensaje de apertura de la sesión siguiente.

| Sesión | Tema | Entregable al cierre |
|---|---|---|
| 1 | Diagnóstico ampliado (listas, `equals`, string pool) | `Main` del diagnóstico corregido |
| 2 | Tipos, String y equals (primitivos vs referencia, inmutabilidad de `String`, encapsulación) | `Transaccion` con encapsulación correcta |
| 3 | List, ArrayList y for-each | Filtro y suma con `ArrayList` |
| 4 | Enum y record | `Transaccion` como `record` con `enum` |
| 5 | Primer test con JUnit y kata de cierre | Tests en verde + commit `kata-01` |

> La Sesión 1 se consumió en el diagnóstico, por eso el resto de la semana se corre un día y la kata de cierre queda con menos pistas.

### Semana 2: un chat por sesión

Título de cada chat: `Semana 2 - Tema: <tema>`. Como en la Semana 1, la última sesión combina un tema nuevo con la kata de cierre de la semana.

| Sesión | Tema | Entregable al cierre |
|---|---|---|
| 6 | Set, HashSet y hashCode (incluye el ejercicio de cierre de la Semana 1) | Kata-02 en verde + commit |
| 7 | Cómo plantear un test (datos, qué comprobar) + Map y HashMap | Agrupar movimientos por cuenta con `Map`, con tests |
| 8 | `BigDecimal` para dinero | Suma de montos con `BigDecimal` en lugar de `double`, con tests |
| 9 | Excepciones: `throw`, `try`/`catch` y excepciones estándar | Validación con excepción estándar (`IllegalArgumentException`), con tests |
| 10 | Excepciones propias + kata de cierre de la Semana 2 | Agrupar por tipo y por cuenta, con validaciones que lanzan excepciones propias, tests en verde + commit |

> La Sesión 6 se extendió 30 min por el ejercicio de cierre de la Semana 1 (regla del ejercicio de cierre semanal) y terminó el domingo 11 de octubre.

### Semana 3: un chat por sesión

Título de cada chat: `Semana 3 - Tema: <tema>`. La sesión 11 reemplaza el recuerdo por el ejercicio de cierre de la Semana 2 (Día A de 2 h 30). La última sesión combina un tema nuevo con la kata de cierre.

| Sesión | Tema | Entregable al cierre |
|---|---|---|
| 11 | `java.time`: `LocalDate`, `LocalDateTime`, comparar fechas | Fecha en `Transaccion` y filtro por rango de fechas, con tests |
| 12 | Convertir texto a fecha: `DateTimeFormatter` y `DateTimeParseException` | Parsear fechas de texto, con el caso de fecha inválida y tests |
| 13 | Leer archivos: `Path`, `Files.readAllLines`, `BufferedReader` y `try` con recursos | Leer un archivo de texto línea por línea; el método recibe un `Path`. Tests sobre archivos fijos en `fundamentos/src/test/resources/sesion13/` (uno con 3 líneas, uno vacío y uno inexistente), con `Path.of("src/test/resources/sesion13/...")`; `@TempDir` llega en la sesión 19 |
| 14 | De una línea CSV a una `Transaccion` (un solo tema, ver abajo) | Una línea CSV a `Transaccion` con el monto redondeado a 2 decimales con `HALF_EVEN`, con tests: línea válida, monto de 3 decimales redondeado y columnas de menos |
| 15 | Cargar el CSV completo, saltando y reportando las líneas inválidas + kata de cierre de la Semana 3 (ver abajo) | `cargar(Path)` devuelve un `record` con las transacciones válidas y el reporte de inválidas (`"línea N: " + mensaje`). Tests sobre archivos fijos en `fundamentos/src/test/resources/sesion15/`: todo válido, mezcla (cuántas y texto del reporte) y solo encabezado. Tests en verde + commit |

**Sesión 14: contenido del tema.**
- *Nuevo:* `split(",")` devuelve un `String[]` (primer arreglo del plan: índice desde 0, `length` sin paréntesis frente a `size()` de `List`, `ArrayIndexOutOfBoundsException` con un índice que no existe, por eso se valida `partes.length != 4` antes de leer); `trim()`; `new BigDecimal("abc")` lanza `NumberFormatException`, hija de `IllegalArgumentException`; escala: `scale()`, `setScale(2)` completa con ceros, `setScale(2)` con `10.555` lanza `ArithmeticException`, `setScale(2, RoundingMode.HALF_EVEN)` y su diferencia con `HALF_UP` (`10.125` → `10.12` frente a `10.13`).
- *Repaso aplicado:* fecha con el formatter estricto de la kata-08 (s12), `TipoTransaccion.valueOf` con tipo desconocido (s4), `new BigDecimal(String)` (s8); el texto que sale de `split` no es literal y se compara con `equals` (string pool, `pendiente`).
- La variación practica `split` y `setScale` con `RoundingMode`, para que la kata tenga como máximo una novedad.
- `divide` queda fuera de la Fase 0: se agrega en Planeación cuando una kata necesite dividir.

**Sesión 15: contenido del tema.**
- *Al inicio del tema, corrección de la kata-10 (T05, 5–10 min):* `split(",", -1)` conserva los vacíos del final (solo se enseña el `-1`, no la teoría completa del límite). Se cambia `LineaCsv.convertir` y se agrega un test con `"5/6/2026,0001,DEBITO,10.00,,"` que espera `IllegalArgumentException` (regla Error real → test). No cuenta como novedad de la kata.
- *Nuevo:* `try`/`catch` dentro del bucle: el `catch` registra la línea inválida y la carga sigue (con el `catch` fuera del bucle, como en la s9, la carga se corta en la primera inválida); contador del número de línea (la 1 es el encabezado y se salta); un solo `catch (IllegalArgumentException)` atrapa todos los casos porque el método de la s14 lanza esa excepción (el padre atrapa a las hijas); devolver dos resultados con un `record` de dos listas.
- *Repaso aplicado:* leer con `Path` y `BufferedReader` (s13), `getMessage()` (s10), `record` (s4) y el método de la s14.
- En la kata, la única novedad es el `try`/`catch` dentro del bucle; el `record` de resultado viene con su esqueleto en el enunciado.

### Semana 4: un chat por sesión

Título de cada chat: `Semana 4 - Tema: <tema>`. La app se construye en `dev.doris.app_transacciones` (ver "App de la Fase 0" en Reglas). La sesión 16 reemplaza el recuerdo por el ejercicio de cierre de la Semana 3 (Día A de 2 h 30), que incluye dos pendientes: la ubicación en el `try` (la línea que puede fallar va dentro; el `readLine()` que avanza el bucle va fuera) y asignar el resultado de `setScale` y `add`. La última sesión combina un tema nuevo con la kata de cierre, que es el criterio de salida de la Fase 0. La sesión 21 (Semana 5, Fase 1) lleva el ejercicio de cierre de la Semana 4.

| Sesión | Tema | Entregable al cierre |
|---|---|---|
| 16 | Estructura de una app de consola: `main`, argumentos y separar lectura, cálculo y reporte | Esqueleto de la app que carga el CSV y llama a las partes |
| 17 | Cálculos del reporte: totales por tipo y saldo por cuenta con `Map` y `BigDecimal` | Clase de cálculo con tests |
| 18 | Dar formato al reporte: `String.format`, `printf` y alineación de columnas | Reporte en texto legible, con test sobre el texto generado |
| 19 | Tests con archivos: `@TempDir` y `Files.writeString` para que el test cree su propio CSV; probar la carga de punta a punta | Test que crea un CSV temporal y comprueba el reporte completo |
| 20 | Empaquetar y ejecutar con Maven (`mvn package`, jar ejecutable) + kata de cierre de la Fase 0 | App completa: lee el CSV real, valida, calcula y reporta; tests en verde + commit |

> Si una semana queda corta, el ajuste se hace en la primera sesión de la semana siguiente y el resto se corre un día. Se puede recuperar en fin de semana o hacer dos sesiones el mismo día si no hay cansancio; en Progreso va la fecha real de cada parte.

---

## Distribución de la Fase 1 (borrador, se confirma al cerrar la Fase 0)

Borrador hecho en Planeación el 2026-11-09, antes de terminar la Fase 0. No es el plan vigente de esas sesiones: al cerrar la sesión 20 se revisa con la app terminada y los pendientes de `seguimiento/fallos.md`, y se aprueba con su versión. Hasta entonces, ningún chat de Tema lo usa.

**Lo que motiva el orden:**
- Tres pendientes de `fallos.md` son de Diseño OO y van temprano: `equals` en clase normal, campos `final` en una clave de `HashSet`/`HashMap` y constructor `private`.
- Cosas usadas en la Fase 0 sin sesión propia, que se cierran antes de interfaces y composición: `static`, visibilidad (`public`, `private` y el acceso por defecto de la sesión 15), `String[]` y `while`.
- El `for` con índice nunca se vio (familia 7 inactiva); se ve una sola vez, corto, antes de que los streams lo reemplacen en la Fase 2.

Todo se hace sobre la app en `app_transacciones`, con tests en verde después de cada paso y comparando contra el tag `fase-0`. El tema de cada sesión es "por qué existe y cómo se aplica a la app"; la kata es el refactor concreto.

### Semana 5: clases bien hechas

| Sesión | Tema | Kata (refactor en la app) |
|---|---|---|
| 21 | Ejercicio de cierre de la Semana 4 + visibilidad: `public`, `private`, acceso por defecto, `static` y cuándo no usarlo | Revisar la visibilidad de todo `app_transacciones` |
| 22 | Clases con estado frente a `record`: campos `final`, constructor que valida, `private` | Pasar la validación de `LineaCsv` a un constructor o método de fábrica estático en `Transaccion` |
| 23 | `equals`/`hashCode`/`toString` en clase normal y el contrato con `HashMap` | Clase `Cuenta` como clave de los saldos, con tests del contrato |
| 24 | Inmutabilidad: `List.copyOf`, colecciones no modificables, defensa en el constructor | `ResultadoCarga` que no se puede alterar desde fuera, con test que lo prueba |
| 25 | Arreglos y `for` con índice (una sesión; activa la familia 7) + kata de cierre | Reporte con índice de línea; cierre de la Semana 5 |

### Semana 6: contratos y composición

| Sesión | Tema | Kata |
|---|---|---|
| 26 | Ejercicio de cierre de la Semana 5 + interfaces: contrato, `implements`, programar contra la interfaz | `LectorTransacciones` como interfaz con la implementación CSV |
| 27 | Composición frente a herencia: inyectar por constructor | `App` recibe el lector y el calculador en vez de crearlos |
| 28 | Strategy: una regla que cambia (filtro por tipo, por cuenta, por rango) | Filtros intercambiables para el reporte, con tests por estrategia |
| 29 | Excepciones comprobadas frente a no comprobadas; traducirlas en los bordes | `IOException` convertida en una excepción propia de la app |
| 30 | Interfaces funcionales ya existentes (`Comparator`, `Predicate`) sin lambdas todavía + kata de cierre | Ordenar el reporte con un `Comparator`; cierre de la Semana 6 |

### Semana 7: generics y creación de objetos

| Sesión | Tema | Kata |
|---|---|---|
| 31 | Ejercicio de cierre de la Semana 6 + generics prácticos: leer `List<T>` y `Map<K, V>`, escribir un método genérico simple | `Resultado<T>` genérico para carga y cálculo |
| 32 | Factory: centralizar el `new` según el tipo | `TransaccionFactory` a partir de la línea CSV |
| 33 | Builder: objetos con muchos campos opcionales | Builder del reporte (título, filtros, orden) |
| 34 | `Optional` como valor de retorno (solo lo básico; el resto en la Fase 2) | Buscar una cuenta que puede no existir |
| 35 | Revisión del refactor completo frente al tag `fase-0` + kata de cierre de la Fase 1 | Criterio de salida de la fase |

**Por decidir al cerrar la Fase 0:** si la sesión 30 (interfaces funcionales sin lambdas) se queda o pasa a la Fase 2; si el `Optional` de la 34 adelanta demasiado; qué pendientes de `fallos.md` entran en cada ejercicio de cierre.

---

## Fase 8: Práctica continua por sprints (semana 25 en adelante)

Objetivo: no perder la costumbre de escribir código. Un proyecto que crece semana a semana, como en un trabajo real: mantener y extender código existente, no solo empezar de cero.

- **Dedicación:** la misma, 10 h/semana (lunes a viernes, 2 h).
- **Proyecto:** **ledger de doble partida** en Java, núcleo de pagos inspirado en PasaLu (no un port de PasaLu). Cada movimiento genera un débito y un crédito que siempre cuadran; los saldos se derivan de los asientos y no se editan; las transferencias son idempotentes y resisten concurrencia.
- **Sprint = 1 semana**, con su propio chat `Sprint N - <incremento>`. El sprint se define en el chat de Planeación al cierre del anterior: enunciado, criterios de aceptación y tests mínimos.
- **Mezcla de fases:** cada sprint combina temas de fases distintas para mantener vivo todo lo aprendido.
- **Ejercicio a ciegas:** la primera sesión de cada sprint abre con 30 min en `.txt` sin IDE. Alterna entre el incremento anterior y katas cortas de **otro dominio** (inventario, reservas, colas…) para no aprender solo a resolver problemas bancarios.
- **Mismas reglas:** código desde cero, tests en verde, revisión con Error / Riesgo / Estilo. Commit por sprint: `sprint-NN: descripción`.
- **Riesgo a vigilar:** escribir "NestJS con sintaxis de Java" (decoradores como anotaciones, DI de Nest copiada a Spring, `Promise` donde va `Mono`). La revisión lo marca cada vez que aparezca.
- **Conexión con la certificación Claude Developer (CCDV-F):** se espera para fechas cercanas al fin de la Fase 7, por eso los sprints de MCP y Claude API van temprano.

### Backlog tentativo (el orden se ajusta en Planeación)

| Sprint | Incremento |
|---|---|
| 1 | Dominio del ledger en Java puro: cuentas, asientos, invariante débito = crédito, saldo derivado, tests |
| 2 | API REST con Spring Boot MVC + PostgreSQL |
| 3 | Servidor MCP en Java que expone herramientas de consulta del ledger (`consultar_saldo`, `listar_movimientos`) |
| 4 | Tool use con la Claude API desde Java: asistente que responde preguntas sobre los movimientos |
| 5+ | Idempotencia y concurrencia en transferencias, Spring Security, versión reactiva, observabilidad, etc. |

---

## Formato de sesión

Una sesión es una unidad de contenido, no un día: se planea en **2 días de 2 h**. Si el Día A termina antes, el Día B empieza ese mismo día. En Progreso va la fecha real de cada parte.

**Pausas:** si entre el final de la sesión anterior y el inicio de la nueva, o entre el Día A y el Día B, pasan más de 2 días hábiles (lunes a viernes), el chat de Tema pregunta el motivo al inicio, en una sola línea, y lo anota en Progreso como `Pausa: N días hábiles (motivo)`, con una categoría: laboral, cansancio, personal, salud, conectividad u otro. No se piden detalles. Al recalcular la duración del plan se separan los días de pausa de los días de sesión.

| Día | Bloque | Duración | Qué se hace |
|---|---|---|---|
| A | Recuerdo | 15 min | Reescribir de memoria lo de la sesión anterior (en un `.txt` en blanco dentro de `recuerdos/sesionNN/`, sin IDE y sin mirar; no aplica en la primera sesión). En la primera sesión de cada semana del plan, desde la Semana 2, lo reemplaza el ejercicio de cierre semanal (ver abajo) |
| A | Tema nuevo | 60 min | Por qué existe → ejemplo guiado → pausa de comprensión → variación propia |
| A | Cierre de tema | 30 min | Predicción de 6 a 8 líneas con salidas ejecutadas, y su registro en `seguimiento/fallos.md` |
| A | Margen | 15 min | Dudas, pausas, trampa del 1Z0-830 (si aplica) y mensaje de commit |
| B | Busca el error | 5 min | Encontrar el error en un método dado (ver abajo) |
| B | Kata | 75 min | Escribir desde cero, con pistas por niveles. Mínimo 3 tests |
| B | Revisión + teach-back | 20 min | Revisión del código y teach-back: exactamente 3 preguntas, cada una con una sola pregunta (no varias en el mismo número). Hasta la Fase 1, el teach-back no pregunta qué error atrapa un test |
| B | Inglés | 5 min | Práctica "¿Qué dice en...?" con una o dos frases en inglés que hayan salido en la sesión (IDE, terminal, mensajes de error). Solo aquí: no se intercala en el tema ni en la kata |
| B | Cierre | 15 min | Registro de fallos, `trampas.md` si hubo trampa, fila de Progreso, mensaje de commit y mensaje de apertura de la sesión siguiente |

**Commits:** los hace Doris desde IntelliJ con el mensaje que da el chat (Tema, Sprint o Planeación). Ningún chat hace commits desde su entorno.

**Trampa del 1Z0-830 (opcional, solo informativa):** al final del Día A, después del cierre de tema, si existe una trampa de examen ligada al tema de la sesión, Claude la muestra: código corto, si compila o no, la salida, y por qué. Una sola, de 2 a 3 min, usando solo lo visto en la sesión y marcada como "código de examen, no de producción". No se pide predicción, no se evalúa y no entra a `seguimiento/fallos.md`. Si el tema no tiene una trampa clara, se omite. Se registra en `seguimiento/trampas.md`: una fila en el índice y su detalle (código, si compila, salida o error exacto ejecutado, por qué y corrección). Desde la sesión 14, Progreso ya no la anota.

### Archivos de práctica

Todo lo que Claude prepara para que Doris responda en un `.txt` lo crea Claude en la carpeta vinculada, en `recuerdos/sesionNN/`, con el formato de abajo. No se muestra solo en el chat. Doris responde en el archivo y avisa; Claude lee las respuestas desde la carpeta, no desde un pegado en el chat. Si la carpeta no está vinculada, el chat la pide antes de seguir.

| Archivo | Lo que deja Claude listo |
|---|---|
| `RecuerdoSesionMM.txt` (MM = sesión anterior) | Un comentario arriba con lo que hay que reescribir; debajo, en blanco |
| `BuscaElError.txt` | El método con número de línea a la izquierda, la entrada concreta y las tres preguntas, cada una con `--->` |
| `CierreDeTema.txt` | El código completo con las marcas `[1]` a `[8]`; debajo, `// --- Predicciones ---` con una línea `[N] --->` por marca (un bloque por ejecución si hay varias). Después de la revisión, Claude agrega `// --- Salida real ---` |
| `EjercicioSemanaK.txt` | El enunciado arriba, dentro de `/* ... */` y con las partes numeradas; debajo, `// --- Mi solución ---`. La solución de Claude va en `EjercicioSemanaK-solucion.txt` |

Ejemplos:

```
// RecuerdoSesion13.txt
// Reescribe sin mirar: LectorMovimientos.leerLineas de la kata-09 (BufferedReader, try con recursos y while).
```

```
// BuscaElError.txt
 1  static BigDecimal totalDebitos(List<String> lineas) {
 2      BigDecimal total = BigDecimal.ZERO;
 ...
11  }

Entrada:
List.of("5/6/2026,0001,DEBITO,10.50", "6/6/2026,0002,CREDITO,80.00")

Responde aquí con tres cosas:

1. En qué línea está el error. --->
2. Qué devuelve (o imprime) este método, tal como está, con esa entrada. --->
3. A qué familia del catálogo pertenece. --->
```

```
// CierreDeTema.txt
public class CierreDeTema {
    public static void main(String[] args) {
        System.out.println("[1] " + ...);
        ...
    }
}

// --- Predicciones ---
[1] --->
[2] --->
...

// --- Salida real ---   (la agrega Claude después de la revisión)
```

```
// EjercicioSemanaK.txt
/*
Ejercicio de cierre de la Semana K (40–45 min, a ciegas, sin package ni import)
Parte 1 (obligatoria): ...
Parte 2 (obligatoria): ...
Parte 3 (opcional): ...
Parte 4 (opcional): ...
*/

// --- Mi solución ---
```

### Ciclo de cada tema nuevo

1. **Por qué:** qué problema resuelve y cómo sería sin él.
2. **Ejemplo guiado:** Claude lo construye explicando cada línea (con analogías a JS/TS cuando ayuden).
3. **Variación:** Doris escribe algo parecido, pero no igual.
4. **Kata:** Doris escribe desde cero.
5. **Revisión + teach-back.**

A medida que un tema se domina, los pasos 1–3 se acortan y crece el 4.

#### Reglas de presentación de cada tema (aprobadas el 2026-10-09)

1. **Un solo mensaje por tema**, con código nuevo, completo y a la vista. No depende de código de sesiones anteriores.
2. **Orden fijo:** el problema que resuelve → ejemplo completo con las salidas reales (ejecutadas, no supuestas) → explicación línea por línea → el mecanismo.
3. **Pausa de comprensión:** una pregunta sin código nuevo. Solo se avanza cuando esa parte quedó clara.
4. **La variación usa únicamente lo ya mostrado.** No introduce una API o un concepto nuevo dentro del ejercicio. Si el enunciado pide una estructura (qué va dentro o fuera de un `try` o de un bucle, en qué orden), la muestra con un esqueleto comentado, no solo con palabras.
5. **Las predicciones se piden solo sobre lo ya visto.** Si falla, se explica con el mismo ejemplo; si vuelve a fallar, se usa un ejemplo nuevo.
6. **Cierre del tema:** un ejercicio de predicción de 6 a 8 líneas que abarca todo el tema de la sesión. Si lo acierta, el tema quedó interiorizado. El resultado línea por línea se registra en `seguimiento/fallos.md`.
7. **Reescritura de la corrección:** si algo de la variación se corrige con pista de nivel 3 o con un esqueleto, Doris reescribe esa parte una vez, sin mirar, antes de cerrar la variación (1–2 min).

### Pistas por niveles (cuando hay bloqueo en una kata)

1. Pregunta socrática.
2. Concepto o API relevante. Debe aportar algo nuevo frente al nivel 1, no repetirlo con otras palabras.
3. Esqueleto parcial.
4. Solución completa, solo si se pide.

### Ejercicio de cierre semanal (desde la Semana 2)

La primera sesión de cada semana del plan, a partir de la Semana 2, reemplaza el bloque de Recuerdo por un mini ejercicio que integra lo visto en la semana que acaba de cerrarse. Las otras 4 sesiones de la semana mantienen el Recuerdo de 15 min. Esta regla es obligatoria.

- **Qué es:** un problema nuevo (no la kata repetida) con los mismos ingredientes de la semana anterior, en el dominio de transacciones. Lo prepara Claude y se resuelve sin pistas.
- **Tamaño:** máximo 4 partes, numeradas por prioridad. Las partes 1 y 2 son obligatorias; la 3 y la 4, opcionales. Lo que no se alcance se anota, no se penaliza.
- **Formato:** a ciegas, sin IDE y sin mirar código anterior, en el archivo que deja listo Claude con el enunciado arriba (ver "Archivos de práctica"). Se guarda como `recuerdos/sesionNN/EjercicioSemanaK.txt` (N = sesión actual, K = semana que se cierra). No se escriben `package` ni `import` y no se evalúan; el nombre correcto de cada tipo de Java se evalúa donde se usa en el código.
- **Duración:** 40–45 min con corte firme (desde la sesión 21; antes, 25–30 min). El Día A de esa sesión se extiende 45 min (2 h 45), que incluyen el repaso.
- **Repaso (fijo, 10–15 min):** después de la revisión, Claude guarda la solución en `recuerdos/sesionNN/EjercicioSemanaK-solucion.txt` y Doris reescribe en IntelliJ solo las partes que falló o no alcanzó.
- **Revisión:** solo sobre lo visto hasta esa semana, con las etiquetas Error / Riesgo / Estilo y el criterio de "Qué se evalúa en el recuerdo" (ver Reglas). No se adelantan temas que aún no se han visto.
- **Registro:** los fallos van al índice de `seguimiento/fallos.md`. El ejercicio incluye los conceptos que estén en `pendiente`.

---

### Registro de fallos (`seguimiento/fallos.md`)

Objetivo: ver de un vistazo dónde se falla y si ya se reforzó. No es un puntaje.

- **Qué entra:** fallos del cierre de tema, del ejercicio de cierre semanal, de los recuerdos, de las katas y de los sprints. De las revisiones entran los **Error** y los **Riesgo**; los **Estilo** no. No entran errores de digitación, espaciado ni formato.
- **Índice de fallos** (al inicio): concepto, dónde falló, por qué y estado. Una fila por concepto, no por ocurrencia: si vuelve a fallar, se agrega el lugar a la misma fila.
- **Estados:** `pendiente` (falló y aún no ha salido bien) o `reforzado (sesión N)` (volvió a aparecer y se acertó). Si un concepto reforzado vuelve a fallar, regresa a `pendiente` con la nota "recaída (sesión N)".
- **Detalle por sesión** (debajo): solo para el cierre de tema, con línea, respuesta de Doris, salida real ejecutada, ✅/❌ y el porqué.
- **Quién y cuándo:** el chat de Tema o de Sprint lo actualiza al cierre de la sesión, en el mismo commit que la fila de Progreso.
- **Uso:** los conceptos en `pendiente` alimentan el recuerdo, el ejercicio de cierre semanal y las variaciones.

### Catálogo de familias de error

Para el "Atrapa" de cada test se elige una familia y se aplica al método. Es permanente: cuando aparece un fallo nuevo en `seguimiento/fallos.md` que no encaja, se agrega una familia.

| # | Familia | Cómo se ve en el código | Ejemplos reales | Datos que la atrapan |
|---|---|---|---|---|
| 1 | No acumula | `total.add(x)` sin reasignar; `total = x` en vez de sumar | `monto.setScale(2, RoundingMode.HALF_EVEN);` sin asignar (kata-10, s14); `total.add(monto)` sin reasignar (Busca el error, s14); `totalesPorTipo` creaba la clave pero no sumaba el monto (kata-06, s10) | Varios elementos con valores distintos |
| 2 | Valor inicial equivocado | Arranca en `null`, en el primer elemento o en 1 | `LocalDate.of(2026, 1, 1)` fijo como valor inicial (Busca el error, s11); `int numero = 0` en el contador de línea, que corre todos los números (Busca el error, s15); `total = null` (método equivocado del taller, s8) | Lista vacía; un solo elemento |
| 3 | Condición invertida o de más | `else` sobrante, `>` en vez de `>=`, `!` olvidado | `>= 100` en vez de `> 100` (Busca el error, s9); `monto.compareTo(tope) < 0` cuando se pedía "supera el tope" (ejercicio de cierre de la Semana 2, s11) | Elementos que cumplen, que no cumplen y uno justo en el límite |
| 4 | Comparación equivocada | `==` en vez de `equals`; `equals` en vez de `compareTo` | `==` en vez de `equals` con `String` (diagnóstico; ejercicio de cierre de la Semana 1, s6; Busca el error, s10); `equals` con `BigDecimal`, que también compara la escala (`1.0` frente a `1.00`) (tema, s8) | Iguales en valor pero distintos en referencia o escala (`1.0` y `1.00`) |
| 5 | Tipo o precisión | `double` en vez de `BigDecimal` | Suma de montos con `double` (`0.10 + 0.20`) (Test 3 del taller, kata-04, s8) | Montos como `0.10 + 0.20` |
| 6 | Reemplaza en vez de agregar | `put` sobre una clave existente; lista nueva en cada vuelta | `put(cuenta, List.of(mov))` (kata-03, s7); `new ArrayList<>()` dentro del `for` (Busca el error, s12 y s13) | Dos elementos con la misma clave |
| 7 | Se salta un elemento | `i = 1`, `i < size() - 1` | — | Inactiva: se activa cuando se use el `for` con índice |
| 8 | La API corrige o descarta sin avisar | `split(",")` sin `-1`; formatter sin `ResolverStyle.STRICT` | `split(",")` sin `-1` deja pasar `...,10.00,,` (T05, s14); formatter sin `STRICT`, que convierte 31/02 en 28/02 (tema, s12) | Entradas mal formadas que la API "arregla": columnas vacías al final, 31/02 |

**Familia 3 o 4:** se decide por qué se compara, no por qué se cambia.
- **Familia 4:** la comparación mira otra cosa. Por ejemplo, la referencia en vez del contenido (`==` frente a `equals`), o el valor junto con la escala (`equals` frente a `compareTo` en `BigDecimal`).
- **Familia 3:** la comparación mira lo correcto, pero en la dirección o con el límite equivocado. Por ejemplo, `< 0` en vez de `> 0` en `compareTo`, `>=` en vez de `>`, `isBefore` en vez de `isAfter`, o un `!` de más.

**Ejemplos reales:** los agrega el chat de Tema al cierre de la sesión, con un máximo de 3 por familia y la sesión de cada uno; es registro, igual que `fallos.md`. Los ejemplos que no fueron errores de Doris (métodos de Busca el error, del taller o del tema) llevan su origen. Las familias nuevas y los criterios los decide Planeación.

### Busca el error (5 min)

Al inicio del Día B, antes de la kata:

- Claude deja listo `recuerdos/sesionNN/BuscaElError.txt` (ver "Archivos de práctica") con un método corto con **un** error de una familia del catálogo, escrito solo con lo ya visto en las sesiones, sin decir dónde está.
- Doris dice en qué línea está, qué devolvería con una entrada concreta y a qué familia pertenece. No se escribe test.
- El resultado se anota en la tabla "Testing por kata" de `seguimiento/fallos.md`.

**Suspendido hasta el inicio de la Fase 1** (se decide en Planeación con los datos de "Testing por kata"): el taller con mutantes y rojo verificado, y el "Atrapa" obligatorio. Las reglas de testing no se vuelven a cambiar antes del cierre de la Fase 0, salvo un bloqueo real.

## Rol de Claude y revisión

- **Revisor y entrenador, no autor.** No escribe la solución de una kata salvo que Doris la pida explícitamente; si hay bloqueo, usa las pistas por niveles.
- **Revisión:** se lee el código directo de la carpeta vinculada (no capturas ni pegados). Cada punto lleva la etiqueta **Error** (falla o resultado incorrecto), **Riesgo** (funciona pero puede fallar en otro caso) o **Estilo** (funciona igual, pero hay una forma más clara o más común en Java). Error y Riesgo van sin suavizar. Estilo: como máximo 2 puntos por revisión, con el porqué y usando solo lo ya visto en las sesiones. El feedback no se enmarca como "lo que haría un senior": Doris está en nivel inicial y se compara con lo visto, no con prácticas avanzadas.
- **No asumir conocimiento:** no se da por sabido nada que no se haya visto en las sesiones.
- Analogías con JS/TS, RxJS y NestJS cuando ayuden. Español neutro/latinoamericano.
- La práctica de inglés va solo en su bloque de 5 min del Día B (ver Formato de sesión); durante la sesión se anotan las frases candidatas sin detenerse.

## Reglas

- Código escrito desde cero: sin copiar y pegar. Única excepción: el arranque de la app de la Fase 0 en la sesión 16 (ver abajo).
- Autocompletado de IA apagado (el autocompletado normal de IntelliJ sí vale).
- Cada kata se cierra con tests en verde. **El verde que cuenta es el de IntelliJ**, confirmado por Doris ("N tests passed"); la verificación de Claude con Java simple es solo de apoyo, y el chat no da la kata por cerrada sin esa confirmación.
- **Diseño de la kata (lo prepara Claude):** como máximo una pieza que no se haya practicado en la variación del tema. Cualquier otra novedad viene en el enunciado con su esqueleto (firma y estructura; por ejemplo, el `try`/`catch` vacío con el tipo de excepción) y Doris escribe el contenido.
- Un commit por kata: `kata-NN: descripción`.
- **App de la Fase 0 (sesiones 16 a 20):** vive en su propio package, `dev.doris.app_transacciones` (main y test). En la sesión 16, antes del tema (unos 5 min), se copian ahí con **Refactor → Copy Class** de IntelliJ `Transaccion`, `TipoTransaccion`, `LineaCsv`, `ResultadoCarga` y `CargaTransacciones`, con `LineaCsvTest` y `CargaTransaccionesTest`; los packages de las sesiones 14 y 15 no se tocan. Desde ahí, el código de la kata de cada sesión (16: `App` con `main` y argumentos; 17: cálculo; 18: reporte; 19: tests con `@TempDir`; 20: clase principal del jar) se agrega a ese package, y los ejemplos, la variación y el cierre de tema siguen en `sesionNN_tema_corto`. `app_transacciones` no importa nada de los packages `sesionNN_`, y la revisión lo verifica. En el índice del README, la columna Package de las sesiones 16 a 20 lleva los dos packages. Al cerrar la sesión 20 se crea el tag de git `fase-0`; en la Fase 1 la app se refactoriza en el mismo package y módulo, y el antes y el después se comparan contra ese tag.
- **Package de cada sesión:** `sesionNN_tema_corto` (número con dos dígitos, minúsculas, palabras separadas con `_`; nunca guiones ni mayúsculas), por ejemplo `dev.doris.sesion10_excepciones_propias`, igual en `main` y en `test`. Las carpetas de recuerdos solo llevan el número: `recuerdos/sesionNN/`. Al cerrar la sesión, el chat de tema agrega su fila al índice de sesiones del `README.md` (Sesión, Tema, Package, Kata).
- Si hay bloqueo, se deja un comentario con la intención (`// aquí quería...`) en lugar de borrar.
- **Qué se evalúa en el recuerdo y en el ejercicio de cierre semanal:** la lógica y el uso correcto de la API de Java (tipos, métodos y excepciones de Java). No cuentan los nombres que elige Doris (variables, clases propias, textos de mensajes), los errores de digitación, el espaciado ni el formato. Un tipo o método de Java que no existe sí cuenta (por ejemplo, `DateTimeFormatterException`).
- Antes de ejecutar, se escribe en un comentario `//` la salida literal esperada, línea por línea.
- Antes de escribir un test, se escribe en un comentario `//` su planteamiento con valores concretos: **Entrada** (los datos exactos) y **Esperado** (el valor que devuelve el método correcto). El caso lo dice el nombre del test. **Atrapa** (familia del catálogo y qué daría el método con ese error) es opcional hasta la Fase 1: se anota si se ve, no se fuerza. Debajo va la predicción de siempre. Ejemplos con la kata-04:

```java
// Entrada: abonos de 10.50, 0.10 y 0.20 de la cuenta "0001".
// Esperado: 10.80
// Atrapa (opcional): familia 1 (no acumula). Con total.add(abono.monto()) sin reasignar, daría 0.
// Predicción: verde
@Test
public void sumarMontosSumaTodosLosAbonos() {
    Abono a1 = new Abono("0001", new BigDecimal("10.50"));
    Abono a2 = new Abono("0001", new BigDecimal("0.10"));
    Abono a3 = new Abono("0001", new BigDecimal("0.20"));

    BigDecimal total = Abonos.sumarMontos(List.of(a1, a2, a3));

    assertThat(total).isEqualByComparingTo(new BigDecimal("10.80"));
}
```

  Un caso de borde lleva la misma estructura:

```java
// Entrada: List.of()
// Esperado: 0
// Predicción: verde
```
- **Error real → test:** cuando en una kata se comete un error de verdad (un `put` que reemplaza, un `add` sin reasignar), se escribe el test que lo habría atrapado y se anota su familia del catálogo. Así el repertorio de errores crece con la experiencia.
- Ante un error: leer el tipo de excepción y la línea del stack trace, y formular una hipótesis antes de probar cosas.

---

## Diagnóstico inicial (20–30 min)

Sin IA y sin internet. Se entrega lo logrado, aunque esté incompleto o no compile.

1. Una clase `Transaccion` con tres campos (`id` de tipo `String`, `monto` de tipo `double`, `tipo` de tipo `String`), un constructor y getters.
2. Un `main` que cree una `List` con 5 transacciones.
3. Un `for` que imprima solo las transacciones de tipo `"DEBITO"`.
4. Un método que reciba la lista y retorne la suma de los montos.
5. *(Opcional)* El punto 4 con streams. Se omite si no se sabe.

El resultado define en qué semana de la Fase 0 se arranca.

**Resultado (2026-10-06):** más de 1 h; bloqueos en `Arrays.asList()` + `add` (`UnsupportedOperationException`), `==` vs `equals` y sintaxis del for-each. Lógica correcta. Arranque: **Semana 1, sin comprimir**.

---

## Configuración inicial

### Entorno (verificado el 2 de noviembre de 2026)

- JDK: Temurin 21.0.13+9 en `C:\Program Files\Eclipse Adoptium\jdk-21.0.13.9-hotspot` (antes 21.0.12.1+1).
- `JAVA_HOME` apunta a esa carpeta, y `where.exe java` muestra una sola ruta.
- IDE: IntelliJ, con su Maven integrado (no hace falta instalar Maven).

### Abrir el proyecto

1. Descomprimir `java-practica.zip` en la carpeta de proyectos.
2. IntelliJ: **File → Open** → seleccionar la carpeta `java-practica` y abrir como proyecto Maven.
3. **File → Project Structure → Project**: SDK = Temurin 21 (`21.0.13`), Language level = 21.
4. Abrir `funcional-katas/src/test/java/dev/doris/funcional/SaludoTest.java` y ejecutarlo con el ícono verde junto a la clase. Debe pasar.
5. Working directory de los `main`: **Run → Edit Configurations → Edit configuration templates → Application → Working directory: `$MODULE_WORKING_DIR$`**. Así un `main` que lee archivos resuelve las rutas relativas desde el módulo (`fundamentos`), igual que los tests JUnit. Si una configuración ya existente falla con `NoSuchFileException`, se le cambia el Working directory o se borra y se vuelve a crear.
6. Apagar el autocompletado de IA: **Settings → Editor → General → Inline Completion**. Desactivar también los plugins JetBrains AI o Copilot si están instalados.
7. Formato e imports al guardar: **Settings → Tools → Actions on Save** → activar **Reformat code** y **Optimize imports**. Y en **Settings → Editor → Code Style → Java → Imports**: *Class count to use import with '\*'* = 99 y *Names count to use static import with '\*'* = 99, para que los imports sigan siendo uno por clase (sin `java.util.*` ni `Assertions.*`). Actions on Save vive en `.idea/workspace.xml`, que es local: si se reinstala IntelliJ o se clona el repo, hay que repetirlo. No es un paso de cada kata.
8. Crear en GitHub el repo vacío `java-practica` (sin README) y conectarlo:

```powershell
git remote add origin https://github.com/DMosqueraL/java-practica.git
git push -u origin main
```

### Estructura del repo

```
java-practica/
├── pom.xml                  (padre: Java 21, JUnit 5, AssertJ)
├── fundamentos/             (activo: Fases 0 y 1, semanas 1-7; la app en dev.doris.app_transacciones)
├── funcional-katas/         (Fase 2, semanas 8-11; por ahora solo la prueba de humo Saludo)
├── procesador-extractos/    (Fase 3, semanas 12-13; se agrega en su fase)
├── concurrencia/            (Fase 4, semanas 14-15; se agrega en su fase)
├── reactor-fundamentos/     (Fase 5, semanas 16-18; se agrega en su fase)
├── mvc-api/                 (Fase 6, semanas 19-21; se agrega en su fase)
├── webflux-api/             (Fase 7, semanas 22-24; se agrega en su fase)
├── recuerdos/               (recuerdos y ejercicios semanales en .txt, por sesión)
└── seguimiento/             (registro de fallos: fallos.md; trampas del 1Z0-830: trampas.md)
```

El módulo `fundamentos` (Fase 0) se creó el 2026-10-09 con todo lo de las sesiones 0 a 5, y sigue activo en la Fase 1; `funcional-katas` queda para la Fase 2. Los nombres `concurrencia` y `mvc-api` son tentativos: se confirman en Planeación al abrir cada fase. El ledger de la Fase 8 se define con su primer sprint. Los `.txt` de recuerdo viven en `recuerdos/sesionNN/`, fuera de `src/`.

---

## Progreso

Cada fila resume qué se hizo, el entregable y los pendientes `Para planeación`. Desde la sesión 7, los fallos se registran en [seguimiento/fallos.md](seguimiento/fallos.md).

| Fecha | Sesión | Tema | Estado |
|---|---|---|---|
| 2026-10-06 | Diagnóstico | Clase, List, for, suma | Hecho (>1 h; fallos: `Arrays.asList`, `==` vs `equals`, for-each) |
| 2026-10-06 | 1 | Diagnóstico ampliado (listas, `equals`, string pool) | Hecha |
| 2026-10-06 | 2 | Tipos, String y equals | Hecha (`equals` en `Transaccion`; `hashCode` pendiente) |
| 2026-10-06 | 3 | List, ArrayList y for-each | Hecha (filtro y suma con `ArrayList`, for-each y `for` clásico) |
| 2026-10-07 | 4 | Enum y record | Hecha. Kata: `record Transaccion` + `enum TipoTransaccion`, filtro con `==`, `sumaMontos` en `Main2`. Aprendido: `equals` compara contenido, `==` referencias; `valueOf` inexistente lanza `IllegalArgumentException` |
| 2026-10-08 | 5 | Primer test con JUnit y kata de cierre | Hecha. Kata: `Transacciones.filtrarPorTipo` y `sumarMontos` con 5 tests (JUnit 5 + AssertJ). Aprendido: `@Test`, `assertThat`, `isEqualTo` (equals) vs `isSameAs` (referencia), `containsExactly`, `isEmpty`; un `println` no se puede testear, un método que devuelve un valor sí |
| 2026-10-09 | 6 (cierre Semana 1) | Ejercicio de cierre semanal (a ciegas, 36 min) | Hecho. Bien: `enum`, `record`, `ArrayList` + for-each, `==` en enum, `==` vs `equals` en records, predicciones de `a == b`, `a.equals(b)` y 250.5. Fallaron: `String` comparado con `==` en vez de `equals` (se repite el fallo del diagnóstico), `.size()` sobre el resultado de un método (predijo la lista en vez de un `int`), sintaxis de `assertThat(...)` (paréntesis mal cerrado, argumento de tipo equivocado y valor esperado incorrecto), typo en nombre de variable, guion en el `package`. Reforzar: `equals` en `String`, leer qué devuelve cada llamada encadenada. Los errores se corrigieron en la misma sesión tras la revisión |
| 2026-10-09 y 2026-10-11 | 6 | Set, HashSet y hashCode | Hecha. Tema con predicción de cierre 7/7. Kata-02: `Cuentas.cuentasDistintas` y `hayCuentasRepetidas` (con `Set` y el booleano de `add`) más 5 tests (JUnit 5 + AssertJ). Aprendido: `Set` sin duplicados ni orden, `add` devuelve `boolean`, `HashSet` calcula `hashCode` y luego compara con `equals`; si `a.equals(b)` entonces mismo `hashCode`; `record` lo genera solo, en clase normal se sobrescriben ambos (`Objects.hash` con los mismos campos que `equals`). Fallaron: modelo inicial de los cajones, leer `p1 == p2` como `equals`, `contains` que siempre daba `true` en `hayCuentasRepetidas`, test sin `hasSize` (verificaba qué pero no cuántos), test de lista vacía que no llamaba al método, datos copiados sin ajustar. Pendiente: cómo plantear un test (datos, qué comprobar) |
| 2026-10-12 y 2026-10-13 | 7 | Cómo plantear un test + Map y HashMap | Hecha. Tema con predicción de cierre 8/10 y variación `ConteoColores` correcta. Kata-03: `Movimientos.agruparPorCuenta` (`Map<Cuenta, List<Movimiento>>`) más 5 tests (JUnit 5 + AssertJ), todos en verde. Aprendido: `put`, `get`, `getOrDefault` y `containsKey`; las claves del `Map` usan `equals`/`hashCode`; patrón de agrupar (si la clave no está, `put` de una lista nueva; luego `get` y `add`); planteamiento de un test (Caso / Entrada / Comprueba / Por qué) y predecir verde o rojo antes de correrlo; leer un error en el stack trace (`NullPointerException` frente a `AssertionError`). Fallos en [seguimiento/fallos.md](seguimiento/fallos.md). Para planeación: el planteamiento de tests (sobre todo el "Por qué") tomó mucho más de lo previsto y se hizo paso a paso con ayuda en cada caso; evaluar cuánta práctica de planteamiento se agrega a las katas siguientes. La Sesión 8 podría hacerse la noche del 13 de oct si no hay cansancio y el día pendiente recuperarse el fin de semana (como el 11 de oct); lo decide Planeación |
| 2026-10-13 y 2026-10-14 | 8 | `BigDecimal` para dinero | Hecha. El 13 de oct: recuerdo, tema con predicción de cierre 8/10 y variación `TotalesPorCuenta` (`Map<String, BigDecimal>`) correcta. El 14 de oct: taller de planteamiento y kata-04 (`Abonos.sumarMontos` con `BigDecimal` y 3 tests en verde con JUnit 5 + AssertJ: varios abonos, lista vacía y suma exacta de decimales). Aprendido: `double` falla con montos (binario; los errores se acumulan); `BigDecimal` se crea con `String` y no con `double`; es inmutable (`add` devuelve un objeto nuevo y hay que reasignarlo); la escala son los decimales, por eso `equals` compara valor y escala y `compareTo` solo el valor; en AssertJ, `isEqualByComparingTo` usa `compareTo` e `isEqualTo` usa `equals`; `BigDecimal.ZERO` como valor inicial neutro de la suma; `BigDecimal.ZERO` con `getOrDefault`; `get` de una cuenta ausente devuelve `null` y llamar `add` sobre él lanza `NullPointerException`; planteamiento de un test en 3 líneas (Entrada / Esperado / Atrapa) con el catálogo de familias de error. Fallos en [seguimiento/fallos.md](seguimiento/fallos.md). Para planeación: (a) plantear tests sigue siendo lo más difícil (pista máxima 4 en la kata-04; la tabla de pensamiento no le sirvió); ver en la Sesión 9 si baja con el formato de 3 líneas y el catálogo; (b) Doris percibe el Test 3 (suma con `double`) como poco útil en la práctica, porque un método con `BigDecimal` solo suma con `double` si se escribe a propósito, y la verificación en rojo le genera frustración; evaluar cuándo se exige esa verificación y qué tests se piden por kata; (c) la Sesión 8 se partió en dos días porque la parte 1 pasó de las 2 h; (d) la validación de lista `null` que surgió en `sumarMontos` queda para la Sesión 9 (`IllegalArgumentException`) |
| 2026-10-17 | 9 | Excepciones: `throw`, `try`/`catch` y excepciones estándar | Hecha en un solo día. Día A: recuerdo de `Abono` y `Abonos.sumarMontos`, tema con predicción de cierre 7/8 y variación (`sumarMontos` con lista `null` y `Retiros.restar`). Día B: Busca el error (3/3, sin pistas), kata-05 (`Abonos.crear` con 4 validaciones que lanzan `IllegalArgumentException` y 6 tests en verde con JUnit 5 + AssertJ), teach-back e inglés. Aprendido: `throw` termina el método y la excepción sube por la pila hasta un `catch` cuyo tipo coincide (a diferencia de JS, no atrapa todo); si ninguno coincide el `main` termina y la JVM imprime el stack trace; `IllegalArgumentException` la lanza el programador ante un argumento inválido o nulo, mientras que `NullPointerException` y `ArithmeticException` las lanza la JVM; validar al inicio (fail fast) y revisar `null` antes de usar el valor; `BigDecimal` se compara con `compareTo` (`<` y `<=` no sirven); `assertThatThrownBy(() -> ...)` con `isInstanceOf` y `hasMessage`; un test de borde da verde con el método real y solo se pone rojo si alguien rompe la condición. Fallos en [seguimiento/fallos.md](seguimiento/fallos.md). Para planeación: (a) la pista máxima bajó a 2 en la kata-05 (era 4 en la kata-04); (b) Doris dice que ver el error no es lo difícil, sino imaginar el test invisible, es decir qué entrada atrapa un error; rechazó la tabla de dos columnas (método bien frente a método mal) y el ejercicio extra de borde le generó estrés, así que en la Fase 1 probar otro formato y no pedir más del mínimo de la kata; (c) en este chat largo Claude volvió a exigir una familia distinta por test (regla de v1.8) y Doris lo detectó; reforzar `Plan: v1.9` en la apertura; (d) activar `Reformat code` y `Optimize imports` en Actions on Save de IntelliJ, para que el formato no dependa de la memoria (evaluar si pasa a ser paso fijo de las katas) |
| 2026-10-17 | 10 | Excepciones propias + kata de cierre de la Semana 2 | Hecha en un solo día. Día A: recuerdo de `Abono` y `Abonos.crear`, tema con variación (`Depositar` con `CuentaCongeladaException` y `LimiteExcedidoException`) y predicción de cierre 5/8. Día B: Busca el error (familia 4, sin pistas), kata-06 (`Movimientos.crear` con excepciones propias, `totalesPorTipo` y `agruparPorCuenta`, 4 tests en verde con JUnit 5 + AssertJ), teach-back e inglés. Aprendido: una excepción propia es `extends RuntimeException` con `super(mensaje)` y, si hace falta, un campo `private final` con su getter; se crea para una regla de negocio que quien llama quiere distinguir, y `IllegalArgumentException` se lanza ante un argumento inválido o nulo (la JVM lanza `NullPointerException` y `ArithmeticException`); Java revisa los `catch` de arriba abajo, ejecuta el primero cuyo tipo coincide e ignora los demás; acumular por clave con `getOrDefault` + `add` + `put` (`BigDecimal` es inmutable); `isInstanceOf` falla si el tipo lanzado es otro aunque el mensaje coincida; dos literales `String` iguales son el mismo objeto, así que `==` da `true` y solo falla con `new String(...)`. Fallos en [seguimiento/fallos.md](seguimiento/fallos.md). Trampa: constructor de excepción propia sin `super(mensaje)` → `getMessage()` devuelve `null` (compila; se llama a `super()` implícito). Para planeación: (a) la pista máxima de la kata-06 fue 3 solo en `totalesPorTipo` y los tests los planteó sin pistas; el nivel 2 repitió el 1, así que el nivel 2 debe aportar un concepto o API nuevo; (b) Doris pidió cerrar el teach-back sin más preguntas y señaló que meter varias preguntas en un mismo número es trampa: dejar el teach-back en 3 preguntas, cada una con una sola pregunta; (c) el chat arrancó con `Plan: v1.9` y el plan estaba en v1.11 al cerrar: revisar la versión de `PLAN.md` al iniciar; (d) los archivos de la sesión 10 quedaron en `dev.doris.sesion10`, anterior a la regla de v1.11 (`sesionNN_tema_corto`); decidir si se renombra el package |
| 2026-10-19 y 2026-10-20 | 11 | `java.time`: `LocalDate`, `LocalDateTime`, comparar fechas | Hecha. El Día A se partió en dos días (19 y 20 de oct) y el Día B fue el 20 de oct. Día A: ejercicio de cierre de la Semana 2 (cobros por comercio, a ciegas; el `main` y el test quedaron sin terminar), tema con predicción de cierre 8/8 y variación `Vencimientos` (`estaVencida` y `masReciente`) correcta. Día B: Busca el error (familia 2, pista 1), kata-07 (`Transaccion` con `LocalDate` y `Transacciones.filtrarPorRango` con validaciones y rango con extremos incluidos, 3 tests en verde con JUnit 5 + AssertJ), teach-back e inglés. Aprendido: `LocalDate.of(año, mes, día)` valida la fecha (meses de 1 a 12; una fecha inexistente lanza `DateTimeException`); es inmutable, así que `plusDays` devuelve otro objeto y hay que asignarlo; `isBefore` e `isAfter` son estrictos (con la misma fecha ambos dan `false`) y el rango con extremos incluidos es `!fecha.isBefore(desde) && !fecha.isAfter(hasta)`; `==` compara referencias y `equals` compara año, mes y día; de `compareTo` solo importa el signo; un `LocalDateTime` es fecha más hora y `toLocalDate()` descarta la hora; un entero con cero a la izquierda es octal (`05` vale 5, `09` no compila). Fallos en [seguimiento/fallos.md](seguimiento/fallos.md). Trampa: `LocalDate.isBefore(LocalDateTime)` no compila (`incompatible types: LocalDateTime cannot be converted to ChronoLocalDate`; hay que pasar `toLocalDate()`). Para planeación: (a) el ejercicio de cierre de la Semana 2 tenía 8 partes (3 clases, 3 métodos, un `main` de unas 15 líneas y un test) y Doris quedó bloqueada en varias; evaluar un tamaño menor o partes opcionales para el de la Semana 3; (b) Doris pidió la solución del ejercicio de cierre para repasarla y reescribirla letra por letra, y se le dio completa en el chat (no está en el repo); decidir si ese repaso pasa a ser un paso fijo del ejercicio de cierre semanal; (c) el cansancio frenó el Día A (dijo que leía las cosas cinco veces y no las procesaba): se paró y se retomó al día siguiente, y en Progreso queda la fecha real de cada parte |
| 2026-10-20 y 2026-10-27 | 12 | Convertir texto a fecha: `DateTimeFormatter` y `DateTimeParseException` | Hecha. El Día A se partió con una semana de pausa. 20 de oct: recuerdo de la kata-07 (`Transaccion` y `filtrarPorRango`), tema y pausa de comprensión. 27 de oct: variación `LecturaFechas` (7/7), cierre de tema (8/8) y trampa. El Día B fue el 27 de oct: Busca el error (familia 6), kata-08 (`Fechas.parsear` con formato día/mes/año estricto, fecha inválida con `IllegalArgumentException` y 3 tests con JUnit 5 + AssertJ), teach-back e inglés. Aprendido: `LocalDate.parse(texto)` solo lee el formato ISO; para otro formato, `DateTimeFormatter.ofPattern(...)` y `parse(texto, formatter)`; las letras del patrón (`d`/`dd`, `M`/`MM`, `yyyy`/`uuuu`); `DateTimeParseException` (hija de `DateTimeException`) se lanza al leer el texto o al resolver la fecha; el estilo `SMART` (por defecto) baja un día inexistente al último del mes (31/02 pasa a 28/02) y `STRICT` lo rechaza (con `uuuu`); atrapar el tipo específico y no `Exception`, y lanzar `IllegalArgumentException` con `throw` dentro del `catch`. Fallos en [seguimiento/fallos.md](seguimiento/fallos.md). Trampa: un `catch (DateTimeException)` antes de un `catch (DateTimeParseException)` no compila (`exception DateTimeParseException has already been caught`). Para planeación: (a) la kata-08 combinó tres cosas nuevas (patrón del formatter, `catch` por el tipo específico y `throw` dentro del `catch`) y tomó mucho más de los 75 min, con pistas hasta el nivel 3 en el `catch`; evaluar partirla o dar el esqueleto del `catch` desde el enunciado; (b) Doris pidió no enmarcar el feedback como "lo que haría un senior" porque está en nivel rookie, y la sección "Rol de Claude y revisión" pide "la alternativa que usaría un senior"; ajustar esa redacción; (c) el Día A se partió con una semana de pausa por el cansancio del trabajo, y el plan seguía en v1.12 al retomar; (d) en este chat no hay Maven (sin acceso a Maven Central): los tests se verificaron con Java simple y Doris confirmó el verde en IntelliJ (3 tests passed); (e) la kata-09 (sesión 13) pide leer un archivo con mínimo 3 tests, pero `@TempDir` se enseña recién en la sesión 19: definir con qué archivos se prueban antes de esa kata (por ejemplo, un archivo de prueba en `src/test/resources`) |
| 2026-10-28 y 2026-11-02 | 13 | Leer archivos: `Path`, `Files.readAllLines`, `BufferedReader` y `try` con recursos | Hecha. Día A el 28 de oct: recuerdo de la kata-08, tema, pausa de comprensión, variación `LecturaMovimientos` (`contarLineas` y `numerar`, 6/6), cierre de tema (6/8) y trampa. El Día B se pasó del 29 de oct al 2 de nov por falta de internet: Busca el error (familia 6, sin pistas), kata-09 (`LectorMovimientos.leerLineas` con `BufferedReader`, `try` con recursos y `while`; 3 tests en verde en IntelliJ sobre archivos fijos en `src/test/resources/sesion13/`: 3 líneas, vacío e inexistente), teach-back e inglés. Aprendido: `Path.of` solo guarda la ruta (no abre ni revisa el archivo) y una ruta relativa se resuelve desde la carpeta de trabajo; `Files.readAllLines` abre, lee todo, cierra y devuelve una `List<String>` (vacía con un archivo vacío, no `null`); `Files.newBufferedReader` + `readLine()`, que consume una línea por llamada y devuelve `null` al final; el patrón leer antes del `while` y al final de cada vuelta; el `try` con recursos cierra el recurso al salir del bloque (también con `return` o con excepción) y no lleva `catch`; `IOException` es comprobada (atraparla o declararla con `throws`), `NoSuchFileException` es su hija, y un `catch` del padre atrapa a las hijas; en un test, la llamada directa necesita `throws IOException` y la que va dentro de la lambda de `assertThatThrownBy` no. Fallos en [seguimiento/fallos.md](seguimiento/fallos.md). Trampa: asignar otro valor a la variable del `try` con recursos no compila (`auto-closeable resource lector may not be assigned`). Para planeación: (a) Doris recordó que en el recuerdo no se exige igualdad de nombres ni textos y no está escrito en el plan; redacción propuesta: "Recuerdo: se evalúa la lógica y el uso correcto de la API de Java (tipos, métodos y excepciones de Java). No cuentan los nombres que elige Doris (variables, clases propias, textos de mensajes), el espaciado ni el formato"; en esta sesión se contó como Error `DateTimeFormatterException` (no existe) en vez de `DateTimeParseException`; (b) la configuración de ejecución Application de IntelliJ usaba la carpeta del proyecto como Working directory y el ejemplo falló con `NoSuchFileException`: se puso `$MODULE_WORKING_DIR$` en la configuración y en la plantilla de Application (los tests JUnit funcionaron sin cambios); agregarlo a Configuración inicial; (c) IntelliJ ejecutó con `jdk-21.0.13.9-hotspot` el 2 de nov y Entorno registra Temurin 21.0.12.1+1: actualizar; (d) la kata-09 con una sola novedad (tests que leen archivos, con esqueleto) salió con pista máxima 0 y dentro de los 75 min |
| 2026-11-02 | 14 | De una línea CSV a una `Transaccion` | Hecha en un solo día. Día A: recuerdo de la kata-09 (correcto, sin Error ni Riesgo), tema, pausa de comprensión, variación `LecturaPagos` (`montoDePago` y `esDeCuenta`, 6/7) y cierre de tema (8/8); trampa T05 en `seguimiento/trampas.md`. Día B: Busca el error (familia 1, sin pistas), kata-10 (`LineaCsv.convertir`: una línea CSV a `Transaccion` con fecha estricta, cuenta, tipo con `valueOf` y monto a 2 decimales con `HALF_EVEN`; toda línea inválida lanza `IllegalArgumentException` con el campo que falló; 9 tests en verde en IntelliJ), teach-back 3/3 e inglés. Aprendido: `split(",")` devuelve un `String[]` (índice desde 0, `length` sin paréntesis, `ArrayIndexOutOfBoundsException` con un índice que no existe, por eso se valida `partes.length != 4` antes de leer); `trim()` devuelve otro `String`; el texto de `split` no es literal, así que `==` da `false` y se usa `equals`; `valueOf` con espacios falla; `scale()`, `setScale(2)` completa con ceros y lanza `ArithmeticException` (`Rounding necessary`) si sobran decimales; `HALF_EVEN` en el empate exacto va al vecino con el último dígito par (`10.125` → `10.12`, `10.135` → `10.14`) y `HALF_UP` siempre sube; `new BigDecimal("abc")` lanza `NumberFormatException`, hija de `IllegalArgumentException`; un `catch` solo atrapa lo que se lanza dentro de su `try`; declarar la variable antes del `try` para usarla después. Fallos en [seguimiento/fallos.md](seguimiento/fallos.md). Para planeación: (a) por la trampa T05, `split(",")` descarta los vacíos del final y `"5/6/2026,0001,DEBITO,10.00,,"` pasa como válida aunque el Formato del CSV dice que las columnas de más son inválidas; decidir si se acepta, si se enseña `split(",", -1)` o si se agrega un caso en la s15; (b) pista máxima 2 en la kata-10: los dos errores del monto repitieron en la misma sesión el fallo de la variación (`new BigDecimal` fuera del `try`) y el de Busca el error (resultado sin asignar); (c) el commit no se puede hacer desde el entorno del chat (sin identidad de Git y sin permiso para borrar los temporales de `.git`): Doris hace los commits desde IntelliJ con el mensaje que da el chat |
| 2026-11-08 | 15 | Cargar el CSV completo y reportar las líneas inválidas + kata de cierre de la Semana 3 | Hecha en un solo día (se pasó del 3 al 8 de nov; Pausa: 4 días hábiles (laboral y personal)). Día A: recuerdo de la kata-10 (un Error: `parse(...)` en el mensaje del `catch`), corrección de la kata-10 por T05 (`split(",", -1)` y test `convertirLineaConMasColumnas`, 10 tests en verde en IntelliJ), tema con `pagos.csv` (`try`/`catch` fuera frente a dentro del bucle, contador de línea, `record` con dos listas), pausa de comprensión (falló: `readLine()` dentro del `try` deja el bucle sin fin; la pregunta de control salió bien), variación `LecturaAbonos` (`totalValidos` y `contar` con `record Conteo`, 3/3 predicciones; pista 3 en `totalValidos`) y cierre de tema (8/8); trampa T06 en `seguimiento/trampas.md`; `transacciones.csv` creado según el Formato del CSV (10 válidas y 5 inválidas). Día B: Busca el error (familia 2; línea y familia sin pistas, salida errada), kata-11 (`CargaTransacciones.cargar` devuelve `ResultadoCarga` con válidas y reporte `"Línea N: " + mensaje`; 3 tests en verde en IntelliJ sobre archivos fijos de `src/test/resources/sesion15/`; pista máxima 2), teach-back 2/2 (la tercera, anulada) e inglés. Aprendido: con el `try`/`catch` dentro del bucle, después del `catch` la ejecución sigue en la misma vuelta y la carga continúa; con el `try` fuera, la primera inválida saca del bucle; `linea = lector.readLine()` va fuera del `try` porque es lo que avanza el bucle; un `catch` vacío basta para saltar una línea; un solo `catch (IllegalArgumentException)` atrapa todo lo que lanza el método de la s14; un `record` devuelve dos resultados; `split(",", -1)` conserva los vacíos del final y el separador de `split` es una regex. Fallos en [seguimiento/fallos.md](seguimiento/fallos.md). Para planeación: (a) el error de T05 (`split` sin `-1`) se convirtió en test, pero no encaja en ninguna familia del catálogo; decidir si se agrega una (por ejemplo, "una API que descarta datos sin avisar"); (b) en el teach-back Claude hizo una pregunta que era un "Atrapa" (qué error atrapa un test), opcional hasta la Fase 1; Doris lo señaló y se anuló: dejar escrito que el teach-back no pregunta qué error atrapa un test antes de la Fase 1; (c) la kata-11 necesitó `public` en `LineaCsv.convertir` para usarlo desde `sesion15_cargar_csv`; decidir si la app de la Semana 4 importa las clases de los packages de las sesiones 14 y 15 o si se reúnen en un package propio; (d) en la variación la indicación de Claude ("dentro del `try` queda una sola línea") fue ambigua y Doris pidió otra explicación: con el esqueleto comentado salió bien |

---

## Changelog

Versiones anteriores a v1.0: ver el historial de git.

| Versión | Fecha | Cambio |
|---|---|---|
| v1.0 | 2026-10-11 | Se separa el chat de Planeación de los chats de tema (sección Organización de los chats). Se agregan el Changelog y la referencia `Plan: vX.Y` en el mensaje de apertura |
| v1.1 | 2026-10-11 | Se agrega la Fase 8 (práctica continua por sprints sobre un ledger de doble partida, con chat propio por sprint) y la fila Sprint en Organización de los chats |
| v1.2 | 2026-10-11 | Las instrucciones del Proyecto se reducen a leer PLAN.md al iniciar; se agrega la sección Rol de Claude y revisión; la copia local del repo prevalece sobre cualquier otra |
| v1.3 | 2026-10-12 | Se registra qué modelo usar por tipo de chat (Sonnet 5.5 en Tema y Sprint; Opus 5.5 en Planeación, cierres de fase y temas difíciles) |
| v1.4 | 2026-10-13 | Registro de fallos en `seguimiento/fallos.md` (índice por concepto con estado, detalle del cierre de tema; los pendientes alimentan recuerdos y cierre semanal); Progreso deja de listar fallos desde la sesión 7. Nueva regla: plantear cada test en un comentario (Caso, Entrada, Comprueba, Por qué) antes de escribirlo, con ejemplo |
| v1.5 | 2026-10-13 | Taller temporal de planteamiento de tests (sesiones 8 a 10, kata con mínimo 3 tests); el "Por qué" se escribe como «Atrapa un método que…»; se permite recuperar sesiones en fin de semana o el mismo día, con fecha real en Progreso |
| v1.6 | 2026-10-13 | Formato de sesión: cada sesión se planea en 2 días de 2 h (Día A: recuerdo, tema de 60 min, cierre de tema; Día B: taller, kata de 70 min, revisión y cierre). El cierre de tema baja a 6–8 líneas. Una semana del plan son 5 sesiones (≈2 semanas reales por ahora); la duración se recalcula al cerrar la Fase 0 |
| v1.7 | 2026-10-13 | La práctica de inglés pasa a un bloque fijo de 5 min en el Día B (la revisión baja a 20 min); no se intercala en el tema ni en la kata |
| v1.8 | 2026-10-14 | Testing: planteamiento de 3 líneas (Entrada / Esperado / Atrapa), catálogo de familias de error, taller de testing con mutantes y rojo verificado (15 min; kata 65), sin test de referencia; progreso medido por pista máxima en fallos.md. Mini-tema de setScale/RoundingMode/divide en la sesión 14 |
| v1.9 | 2026-10-14 | Testing según el volumen de código: planteamiento obligatorio de 2 líneas (Entrada / Esperado), "Atrapa" opcional; taller con mutantes suspendido hasta la Fase 1; nuevo bloque "Busca el error" (5 min) y regla "error real → test"; kata de 75 min. Reglas de testing congeladas hasta el cierre de la Fase 0 salvo bloqueo real |
| v1.10 | 2026-10-17 | Trampa del 1Z0-830 opcional y solo informativa al final del Día A (una, de 2 a 3 min, dentro del margen; sin predicción ni registro en fallos.md; una línea en Progreso) |
| v1.11 | 2026-10-17 | Packages con número de dos dígitos y tema (`sesionNN_tema_corto`), recuerdos en `recuerdos/sesionNN/` e índice de sesiones en el README, que actualiza el chat de tema al cerrar cada sesión |
| v1.12 | 2026-10-17 | Teach-back de exactamente 3 preguntas, una sola por número; la pista de nivel 2 debe aportar un concepto o API nuevo frente al nivel 1 |
| v1.13 | 2026-10-27 | Kata con una sola novedad (lo demás con esqueleto en el enunciado); feedback sin enmarcarse como "senior" y máximo 2 puntos de Estilo; el verde que cuenta es el de IntelliJ; kata-09 con archivos fijos en `src/test/resources/sesion13/`; ejercicio de cierre semanal de máximo 4 partes (2 obligatorias), 25–30 min, con repaso fijo de la solución guardada en el repo |
| v1.14 | 2026-11-02 | Criterio de evaluación del recuerdo y del ejercicio de cierre (lógica y API de Java; no cuentan nombres propios, digitación ni formato); Working directory `$MODULE_WORKING_DIR$` en la plantilla Application de IntelliJ; entorno actualizado a Temurin 21.0.13+9 |
| v1.15 | 2026-11-02 | Formato del CSV fijo para las sesiones 14 a 20 (montos de 3 decimales se redondean con `HALF_EVEN`; toda línea inválida lanza `IllegalArgumentException`). Sesión 14 como un solo tema (CSV + `setScale`/`RoundingMode`, sin `divide`, que sale de la Fase 0). Sesión 15 sin `Files.write`: `try`/`catch` dentro del bucle, número de línea y `record` de resultado. `Files.writeString` pasa a la sesión 19. Actions on Save e imports en 99 en Configuración inicial |
| v1.16 | 2026-11-02 | Las trampas del 1Z0-830 se registran en `seguimiento/trampas.md` (índice y detalle con código, error o salida ejecutados, por qué y corrección); Progreso deja de anotarlas desde la sesión 14. Se reconstruyen y verifican las trampas de las sesiones 10 a 13 |
| v1.17 | 2026-11-02 | Columnas vacías al final del CSV: siguen siendo inválidas; se separa con `split(",", -1)`, que se enseña al inicio de la sesión 15 como corrección de la kata-10 (T05) con su test, y `transacciones.csv` incluye ese caso. Los commits los hace Doris desde IntelliJ con el mensaje que da el chat |
| v1.18 | 2026-11-08 | Familia 8 en el catálogo (la API corrige o descarta sin avisar). Hasta la Fase 1, el teach-back no pregunta qué error atrapa un test. La app de la Fase 0 vive en `dev.doris.app_transacciones`: en la sesión 16 se copian con Refactor → Copy Class las clases de las sesiones 14 y 15 (única excepción a "sin copiar y pegar") y no importa nada de los packages `sesionNN_`. Regla de presentación 4: una estructura pedida en el enunciado se muestra con esqueleto comentado |
| v1.19 | 2026-11-08 | Retrospectiva de la Semana 3. Regla de pausas: con más de 2 días hábiles sin sesión, el chat de Tema pregunta el motivo (categoría, sin detalles) y lo anota en Progreso; el recálculo separa pausas de días de sesión. Regla de presentación 7: lo corregido en la variación con pista 3 o esqueleto se reescribe una vez sin mirar. El ejercicio de cierre de la Semana 3 incluye los pendientes del `try` y de asignar `setScale`/`add` |
| v1.20 | 2026-11-08 | Estructura del repo con un módulo por fase: `fundamentos` cubre las Fases 0 y 1 (la app se refactoriza en su lugar, con tag `fase-0` al cerrar la sesión 20); se agregan `concurrencia` (Fase 4) y `mvc-api` (Fase 6), con nombres tentativos, y las semanas de cada módulo |
| v1.21 | 2026-11-09 | Borrador de la distribución de la Fase 1 (semanas 5 a 7, sesiones 21 a 35) guardado en el plan para no perderlo; se confirma al cerrar la Fase 0. Revisión de las semanas 1 a 4: sin reestructurar; se anotan los temas usados sin sesión propia y los pendientes de OO que la Fase 1 debe cubrir temprano |
| v1.22 | 2026-11-10 | Ejercicio de cierre semanal: 40–45 min con corte firme y Día A de 2 h 45 (desde la sesión 21); en el `.txt` a ciegas no se escriben `package` ni `import` |
| v1.23 | 2026-11-10 | Archivos de práctica: Claude crea en `recuerdos/sesionNN/` el Recuerdo, Busca el error, el Cierre de tema y el Ejercicio de cierre semanal (con el enunciado arriba) con formato fijo; no se muestran solo en el chat, y las respuestas se leen desde la carpeta |
| v1.24 | 2026-11-10 | Catálogo de familias de error: columna "Ejemplos reales" (de `fallos.md`, con su origen), criterio para distinguir la familia 3 de la 4 (qué se compara frente a la dirección o el límite), y el chat de Tema agrega ejemplos reales al cierre (máximo 3 por familia) |

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
| 13 | Leer archivos: `Path`, `Files.readAllLines`, `BufferedReader` y `try` con recursos | Leer un archivo de texto línea por línea |
| 14 | Parseo de CSV: `split` y convertir cada línea en `Transaccion` + mini-tema `BigDecimal`: `setScale`, `RoundingMode` y `divide` (20 min) | Una línea CSV a `Transaccion` con el monto normalizado a 2 decimales, con tests |
| 15 | Escribir archivos (`Files.write`) y reporte de líneas inválidas + kata de cierre de la Semana 3 | Cargar el CSV real, saltar y reportar las líneas inválidas, tests en verde + commit |

### Semana 4: un chat por sesión

Título de cada chat: `Semana 4 - Tema: <tema>`. La sesión 16 reemplaza el recuerdo por el ejercicio de cierre de la Semana 3 (Día A de 2 h 30). La última sesión combina un tema nuevo con la kata de cierre, que es el criterio de salida de la Fase 0. La sesión 21 (Semana 5, Fase 1) lleva el ejercicio de cierre de la Semana 4.

| Sesión | Tema | Entregable al cierre |
|---|---|---|
| 16 | Estructura de una app de consola: `main`, argumentos y separar lectura, cálculo y reporte | Esqueleto de la app que carga el CSV y llama a las partes |
| 17 | Cálculos del reporte: totales por tipo y saldo por cuenta con `Map` y `BigDecimal` | Clase de cálculo con tests |
| 18 | Dar formato al reporte: `String.format`, `printf` y alineación de columnas | Reporte en texto legible, con test sobre el texto generado |
| 19 | Tests con archivos: `@TempDir` y probar la carga de punta a punta | Test que crea un CSV temporal y comprueba el reporte completo |
| 20 | Empaquetar y ejecutar con Maven (`mvn package`, jar ejecutable) + kata de cierre de la Fase 0 | App completa: lee el CSV real, valida, calcula y reporta; tests en verde + commit |

> Si una semana queda corta, el ajuste se hace en la primera sesión de la semana siguiente y el resto se corre un día. Se puede recuperar en fin de semana o hacer dos sesiones el mismo día si no hay cansancio; en Progreso va la fecha real de cada parte.

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

| Día | Bloque | Duración | Qué se hace |
|---|---|---|---|
| A | Recuerdo | 15 min | Reescribir de memoria lo de la sesión anterior (en un `.txt` en blanco dentro de `recuerdos/sesionN/`, sin IDE y sin mirar; no aplica en la primera sesión). En la primera sesión de cada semana del plan, desde la Semana 2, lo reemplaza el ejercicio de cierre semanal (ver abajo) |
| A | Tema nuevo | 60 min | Por qué existe → ejemplo guiado → pausa de comprensión → variación propia |
| A | Cierre de tema | 30 min | Predicción de 6 a 8 líneas con salidas ejecutadas, y su registro en `seguimiento/fallos.md` |
| A | Margen | 15 min | Dudas, pausas y commit |
| B | Busca el error | 5 min | Encontrar el error en un método dado (ver abajo) |
| B | Kata | 75 min | Escribir desde cero, con pistas por niveles. Mínimo 3 tests |
| B | Revisión + teach-back | 20 min | Revisión del código y explicación de las decisiones |
| B | Inglés | 5 min | Práctica "¿Qué dice en...?" con una o dos frases en inglés que hayan salido en la sesión (IDE, terminal, mensajes de error). Solo aquí: no se intercala en el tema ni en la kata |
| B | Cierre | 15 min | Registro de fallos, fila de Progreso, commit y mensaje de apertura de la sesión siguiente |

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
4. **La variación usa únicamente lo ya mostrado.** No introduce una API o un concepto nuevo dentro del ejercicio.
5. **Las predicciones se piden solo sobre lo ya visto.** Si falla, se explica con el mismo ejemplo; si vuelve a fallar, se usa un ejemplo nuevo.
6. **Cierre del tema:** un ejercicio de predicción de 6 a 8 líneas que abarca todo el tema de la sesión. Si lo acierta, el tema quedó interiorizado. El resultado línea por línea se registra en `seguimiento/fallos.md`.

### Pistas por niveles (cuando hay bloqueo en una kata)

1. Pregunta socrática.
2. Concepto o API relevante.
3. Esqueleto parcial.
4. Solución completa, solo si se pide.

### Ejercicio de cierre semanal (desde la Semana 2)

La primera sesión de cada semana del plan, a partir de la Semana 2, reemplaza el bloque de Recuerdo por un mini ejercicio que integra lo visto en la semana que acaba de cerrarse. Las otras 4 sesiones de la semana mantienen el Recuerdo de 15 min. Esta regla es obligatoria.

- **Qué es:** un problema nuevo (no la kata repetida) con los mismos ingredientes de la semana anterior, en el dominio de transacciones. Lo prepara Claude y se resuelve sin pistas.
- **Formato:** a ciegas, en un `.txt` en blanco, sin IDE y sin mirar. Se guarda como `recuerdos/sesionN/EjercicioSemanaK.txt` (N = sesión actual, K = semana que se cierra).
- **Duración:** 30–40 min. El Día A de esa sesión se extiende 30 min (2 h 30).
- **Revisión:** solo sobre lo visto hasta esa semana, con las etiquetas Error / Riesgo / Estilo. Los espacios en blanco y el formato no cuentan, y no se adelantan temas que aún no se han visto.
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

| # | Familia | Cómo se ve en el código | Datos que la atrapan |
|---|---|---|---|
| 1 | No acumula | `total.add(x)` sin reasignar; `total = x` en vez de sumar | Varios elementos con valores distintos |
| 2 | Valor inicial equivocado | Arranca en `null`, en el primer elemento o en 1 | Lista vacía; un solo elemento |
| 3 | Condición invertida o de más | `else` sobrante, `>` en vez de `>=`, `!` olvidado | Elementos que cumplen, que no cumplen y uno justo en el límite |
| 4 | Comparación equivocada | `==` en vez de `equals`; `equals` en vez de `compareTo` | Iguales en valor pero distintos en referencia o escala (`1.0` y `1.00`) |
| 5 | Tipo o precisión | `double` en vez de `BigDecimal` | Montos como `0.10 + 0.20` |
| 6 | Reemplaza en vez de agregar | `put` sobre una clave existente; lista nueva en cada vuelta | Dos elementos con la misma clave |
| 7 | Se salta un elemento | `i = 1`, `i < size() - 1` | Inactiva: se activa cuando se use el `for` con índice |

### Busca el error (5 min)

Al inicio del Día B, antes de la kata:

- Claude muestra un método corto con **un** error de una familia del catálogo, escrito solo con lo ya visto en las sesiones, sin decir dónde está.
- Doris dice en qué línea está, qué devolvería con una entrada concreta y a qué familia pertenece. No se escribe test.
- El resultado se anota en la tabla "Testing por kata" de `seguimiento/fallos.md`.

**Suspendido hasta el inicio de la Fase 1** (se decide en Planeación con los datos de "Testing por kata"): el taller con mutantes y rojo verificado, y el "Atrapa" obligatorio. Las reglas de testing no se vuelven a cambiar antes del cierre de la Fase 0, salvo un bloqueo real.

## Rol de Claude y revisión

- **Revisor y entrenador, no autor.** No escribe la solución de una kata salvo que Doris la pida explícitamente; si hay bloqueo, usa las pistas por niveles.
- **Revisión:** se lee el código directo de la carpeta vinculada (no capturas ni pegados). Cada punto lleva la etiqueta **Error** (falla o resultado incorrecto), **Riesgo** (funciona pero puede fallar en otro caso) o **Estilo** (funciona igual, mejor práctica senior), sin suavizar. Incluye uso idiomático de Java 21 y la alternativa que usaría un senior, con el porqué.
- **No asumir conocimiento:** no se da por sabido nada que no se haya visto en las sesiones.
- Analogías con JS/TS, RxJS y NestJS cuando ayuden. Español neutro/latinoamericano.
- La práctica de inglés va solo en su bloque de 5 min del Día B (ver Formato de sesión); durante la sesión se anotan las frases candidatas sin detenerse.

## Reglas

- Código escrito desde cero: sin copiar y pegar.
- Autocompletado de IA apagado (el autocompletado normal de IntelliJ sí vale).
- Cada kata se cierra con tests en verde.
- Un commit por kata: `kata-NN: descripción`.
- Si hay bloqueo, se deja un comentario con la intención (`// aquí quería...`) en lugar de borrar.
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

### Entorno (verificado el 5 de octubre de 2026)

- JDK: Temurin 21.0.12.1+1 en `C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot`.
- `JAVA_HOME` apunta a esa carpeta, y `where.exe java` muestra una sola ruta.
- IDE: IntelliJ, con su Maven integrado (no hace falta instalar Maven).

### Abrir el proyecto

1. Descomprimir `java-practica.zip` en la carpeta de proyectos.
2. IntelliJ: **File → Open** → seleccionar la carpeta `java-practica` y abrir como proyecto Maven.
3. **File → Project Structure → Project**: SDK = Temurin 21 (`.101`), Language level = 21.
4. Abrir `funcional-katas/src/test/java/dev/doris/funcional/SaludoTest.java` y ejecutarlo con el ícono verde junto a la clase. Debe pasar.
5. Apagar el autocompletado de IA: **Settings → Editor → General → Inline Completion**. Desactivar también los plugins JetBrains AI o Copilot si están instalados.
6. Crear en GitHub el repo vacío `java-practica` (sin README) y conectarlo:

```powershell
git remote add origin https://github.com/DMosqueraL/java-practica.git
git push -u origin main
```

### Estructura del repo

```
java-practica/
├── pom.xml                  (padre: Java 21, JUnit 5, AssertJ)
├── fundamentos/             (activo: Fase 0, semanas 1-4)
├── funcional-katas/         (Fase 2, semanas 8-11; por ahora solo la prueba de humo Saludo)
├── procesador-extractos/    (se agrega en su fase)
├── reactor-fundamentos/     (se agrega en su fase)
├── webflux-api/             (se agrega en su fase)
├── recuerdos/               (recuerdos y ejercicios semanales en .txt, por sesión)
└── seguimiento/             (registro de fallos: fallos.md)
```

El módulo `fundamentos` (Fase 0) se creó el 2026-10-09 con todo lo de las sesiones 0 a 5; `funcional-katas` queda para la Fase 2. Los `.txt` de recuerdo viven en `recuerdos/sesionN/`, fuera de `src/`.

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

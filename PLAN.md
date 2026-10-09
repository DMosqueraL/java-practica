# Plan: Java 21 de cero a producción

Pista para aprender a **escribir** Java desde cero hasta funcional y reactivo. Es independiente de la teoría de la certificación 1Z0-830: esa entrena a leer código; esta, a producirlo.

- **Dedicación:** 2 h diarias, lunes a viernes (10 h/semana).
- **Duración estimada:** ~24 semanas.
- **Dominio común:** transacciones bancarias. El mismo problema evoluciona de imperativo a OO, luego a funcional y luego a reactivo.

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

> Al terminar: nivel profesional sólido escribiendo Java. Lo que viene después se construye con proyectos reales sobre esta base.

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

---

## Sesión de 2 h

| Bloque | Duración | Qué se hace |
|---|---|---|
| Recuerdo | 15 min | Reescribir de memoria lo de la sesión anterior (en un `.txt` en blanco dentro de `recuerdos/sesionN/`, sin IDE y sin mirar; no aplica en la primera sesión). La primera sesión de cada semana, desde la Semana 2, lo reemplaza el ejercicio de cierre semanal (ver abajo) |
| Tema nuevo | 30 min | Por qué existe → ejemplo guiado línea por línea → variación propia |
| Kata | 50 min | Escribir desde cero, con pistas por niveles |
| Revisión + teach-back | 25 min | Revisión del código y explicación de las decisiones |

### Ciclo de cada tema nuevo

1. **Por qué:** qué problema resuelve y cómo sería sin él.
2. **Ejemplo guiado:** Claude lo construye explicando cada línea (con analogías a JS/TS cuando ayuden).
3. **Variación:** Doris escribe algo parecido, pero no igual.
4. **Kata:** Doris escribe desde cero.
5. **Revisión + teach-back.**

A medida que un tema se domina, los pasos 1–3 se acortan y crece el 4.

### Pistas por niveles (cuando hay bloqueo en una kata)

1. Pregunta socrática.
2. Concepto o API relevante.
3. Esqueleto parcial.
4. Solución completa, solo si se pide.

### Ejercicio de cierre semanal (desde la Semana 2)

La primera sesión de cada semana del plan, a partir de la Semana 2, reemplaza el bloque de Recuerdo por un mini ejercicio que integra lo visto en la semana que acaba de cerrarse. Las otras 4 sesiones de la semana mantienen el Recuerdo de 15 min. Esta regla es obligatoria.

- **Qué es:** un problema nuevo (no la kata repetida) con los mismos ingredientes de la semana anterior, en el dominio de transacciones. Lo prepara Claude y se resuelve sin pistas.
- **Formato:** a ciegas, en un `.txt` en blanco, sin IDE y sin mirar. Se guarda como `recuerdos/sesionN/EjercicioSemanaK.txt` (N = sesión actual, K = semana que se cierra).
- **Duración:** 30–40 min. Esa sesión se extiende 30 min (2 h 30 en total).
- **Revisión:** solo sobre lo visto hasta esa semana, con las etiquetas Error / Riesgo / Estilo. Los espacios en blanco y el formato no cuentan, y no se adelantan temas que aún no se han visto.
- **Registro:** en la tabla de Progreso se anota qué ingredientes de la semana fallaron, para reforzarlos.

---

## Reglas

- Código escrito desde cero: sin copiar y pegar.
- Autocompletado de IA apagado (el autocompletado normal de IntelliJ sí vale).
- Cada kata se cierra con tests en verde.
- Un commit por kata: `kata-NN: descripción`.
- Si hay bloqueo, se deja un comentario con la intención (`// aquí quería...`) en lugar de borrar.
- Antes de ejecutar, se escribe en un comentario `//` la salida literal esperada, línea por línea.
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
└── recuerdos/               (recuerdos y ejercicios semanales en .txt, por sesión)
```

El módulo `fundamentos` (Fase 0) se creó el 2026-10-09 con todo lo de las sesiones 0 a 5; `funcional-katas` queda para la Fase 2. Los `.txt` de recuerdo viven en `recuerdos/sesionN/`, fuera de `src/`.

---

## Progreso

| Fecha | Sesión | Tema | Estado |
|---|---|---|---|
| 2026-10-06 | Diagnóstico | Clase, List, for, suma | Hecho (>1 h; fallos: `Arrays.asList`, `==` vs `equals`, for-each) |
| 2026-10-06 | 1 | Diagnóstico ampliado (listas, `equals`, string pool) | Hecha |
| 2026-10-06 | 2 | Tipos, String y equals | Hecha (`equals` en `Transaccion`; `hashCode` pendiente) |
| 2026-10-06 | 3 | List, ArrayList y for-each | Hecha (filtro y suma con `ArrayList`, for-each y `for` clásico) |
| 2026-10-07 | 4 | Enum y record | Hecha. Kata: `record Transaccion` + `enum TipoTransaccion`, filtro con `==`, `sumaMontos` en `Main2`. Aprendido: `equals` compara contenido, `==` referencias; `valueOf` inexistente lanza `IllegalArgumentException` |
| 2026-10-08 | 5 | Primer test con JUnit y kata de cierre | Hecha. Kata: `Transacciones.filtrarPorTipo` y `sumarMontos` con 5 tests (JUnit 5 + AssertJ). Aprendido: `@Test`, `assertThat`, `isEqualTo` (equals) vs `isSameAs` (referencia), `containsExactly`, `isEmpty`; un `println` no se puede testear, un método que devuelve un valor sí |
| | 6 | Set, HashSet y hashCode | Pendiente |

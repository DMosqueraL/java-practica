# java-practica

Pista de práctica para aprender a escribir Java 21 desde cero hasta funcional y reactivo.

Plan completo, reglas, formato de sesión y progreso: [PLAN.md](PLAN.md)

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
| 25+ | **8. Práctica continua (sprints)** | Un sprint semanal sobre un ledger de doble partida |

> Al terminar la Fase 7: nivel profesional sólido escribiendo Java. Lo que viene después se construye en la Fase 8, con un proyecto real que crece por sprints.
>
> Las semanas son "semanas del plan" (5 sesiones cada una). Detalle y duración real en [PLAN.md](PLAN.md).

## Ejecutar

Desde IntelliJ: panel Maven → `java-practica` → Lifecycle → `test`,
o clic derecho sobre una clase de test → Run.

## Sesiones

Código de cada sesión en `fundamentos/src/main/java/dev/doris/` (y sus tests en `src/test/java`, mismo package). Recuerdos y ejercicios a ciegas en `recuerdos/sesionNN/`. Fallos y progreso en testing en [seguimiento/fallos.md](seguimiento/fallos.md).

| Sesión | Tema | Package | Kata |
|---|---|---|---|
| 0–1 | Diagnóstico y diagnóstico ampliado (listas, `equals`, string pool) | [`diagnostico`](fundamentos/src/main/java/dev/doris/diagnostico) | — |
| 2 | Tipos, `String` y `equals` | [`sesion02_tipos_equals`](fundamentos/src/main/java/dev/doris/sesion02_tipos_equals) | — |
| 3 | `List`, `ArrayList` y for-each | [`sesion03_list_foreach`](fundamentos/src/main/java/dev/doris/sesion03_list_foreach) | — |
| 4 | `enum` y `record` | [`sesion04_enum_record`](fundamentos/src/main/java/dev/doris/sesion04_enum_record) | `Transaccion` como `record` con `enum` de tipo |
| 5 | Primer test con JUnit + kata de cierre de la Semana 1 | [`sesion05_junit`](fundamentos/src/main/java/dev/doris/sesion05_junit) | kata-01: filtro y suma de transacciones con tests |
| 6 | `Set`, `HashSet` y `hashCode` | [`sesion06_set_hashcode`](fundamentos/src/main/java/dev/doris/sesion06_set_hashcode) | kata-02: cuentas distintas con `Set` y `hashCode` |
| 7 | Cómo plantear un test + `Map` y `HashMap` | [`sesion07_map`](fundamentos/src/main/java/dev/doris/sesion07_map) | kata-03: agrupar movimientos por cuenta con `Map` |
| 8 | `BigDecimal` para dinero | [`sesion08_bigdecimal`](fundamentos/src/main/java/dev/doris/sesion08_bigdecimal) | kata-04: sumar montos con `BigDecimal` |
| 9 | Excepciones: `throw`, `try`/`catch` y excepciones estándar | [`sesion09_excepciones`](fundamentos/src/main/java/dev/doris/sesion09_excepciones) | kata-05: `Abonos.crear` con `IllegalArgumentException` |
| 10 | Excepciones propias + kata de cierre de la Semana 2 | [`sesion10_excepciones_propias`](fundamentos/src/main/java/dev/doris/sesion10_excepciones_propias) | kata-06: `Movimientos` con excepciones propias, `totalesPorTipo` y `agruparPorCuenta` |

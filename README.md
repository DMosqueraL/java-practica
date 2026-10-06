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

> Al terminar: nivel profesional sólido escribiendo Java. Lo que viene después se construye con proyectos reales sobre esta base.

## Ejecutar

Desde IntelliJ: panel Maven → `java-practica` → Lifecycle → `test`,
o clic derecho sobre una clase de test → Run.

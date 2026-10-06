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

## Sesión de 2 h

| Bloque | Duración | Qué se hace |
|---|---|---|
| Recuerdo | 15 min | Reescribir de memoria lo de la sesión anterior |
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

---

## Reglas

- Código escrito desde cero: sin copiar y pegar.
- Autocompletado de IA apagado (el autocompletado normal de IntelliJ sí vale).
- Cada kata se cierra con tests en verde.
- Un commit por kata: `kata-NN: descripción`.
- Si hay bloqueo, se deja un comentario con la intención (`// aquí quería...`) en lugar de borrar.

---

## Diagnóstico inicial (20–30 min)

Sin IA y sin internet. Se entrega lo logrado, aunque esté incompleto o no compile.

1. Una clase `Transaccion` con tres campos (`id` de tipo `String`, `monto` de tipo `double`, `tipo` de tipo `String`), un constructor y getters.
2. Un `main` que cree una `List` con 5 transacciones.
3. Un `for` que imprima solo las transacciones de tipo `"DEBITO"`.
4. Un método que reciba la lista y retorne la suma de los montos.
5. *(Opcional)* El punto 4 con streams. Se omite si no se sabe.

El resultado define en qué semana de la Fase 0 se arranca.

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
├── funcional-katas/         (activo)
├── procesador-extractos/    (se agrega en su fase)
├── reactor-fundamentos/     (se agrega en su fase)
└── webflux-api/             (se agrega en su fase)
```

Tras el diagnóstico se agrega un módulo `fundamentos` para la Fase 0.

---

## Progreso

| Fecha | Sesión | Tema | Estado |
|---|---|---|---|
| | Diagnóstico | | Pendiente |

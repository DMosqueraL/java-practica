package dev.doris.sesion13_leer_archivos;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static dev.doris.sesion13_leer_archivos.LectorMovimientos.leerLineas;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LectorMovimientosTest {
    // Entrada: La ruta donde se encuentra el archivo tres-lineas.txt, que contiene tres elementos: ["0101;DEBITO;45.00", "0102;CREDITO;300.00", "0103;DEBITO;12.50"]
    // Esperado: Una lista de tipo String con el siguiente orden: [0101;DEBITO;45.00, 0102;CREDITO;300.00, 0103;DEBITO;12.50]
    // Predicción: verde
    @Test
    public void leerLineasDelArchivoCorrectamente() throws IOException {
        Path ruta = Path.of("src/test/resources/sesion13/tres-lineas.txt");

        assertThat(leerLineas(ruta)).containsExactly("0101;DEBITO;45.00", "0102;CREDITO;300.00", "0103;DEBITO;12.50");
    }

    // Entrada: La ruta donde se encuentra el archivo vacio.txt, que está vacío.
    // Esperado: []
    // Predicción: verde
    @Test
    public void leerLineasDeUnArchivoVacio() throws IOException {
        Path ruta = Path.of("src/test/resources/sesion13/vacio.txt");

        assertThat(leerLineas(ruta)).isEmpty();
    }

    // Entrada: La ruta del archivo no-existe.txt, que no existe.
    // Esperado: NoSuchFileException
    // Predicción: verde
    @Test
    public void leerLineasDeUnArchivoInexistente() {
        Path ruta = Path.of("src/test/resources/sesion13/no-existe.txt");
        assertThatThrownBy(() -> leerLineas(ruta))
                .isInstanceOf(NoSuchFileException.class);
    }
}

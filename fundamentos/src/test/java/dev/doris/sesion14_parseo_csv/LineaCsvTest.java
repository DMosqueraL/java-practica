package dev.doris.sesion14_parseo_csv;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static dev.doris.sesion14_parseo_csv.LineaCsv.convertir;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LineaCsvTest {

    // Entrada: La línea sin espacios "5/6/2026,0001,DEBITO,10.50"
    // Esperado: Transaccion[fecha=2026-06-05, cuenta=0001, tipo=DEBITO, monto=10.50]
    // Predicción: verde
    @Test
    public void convertirLineaValidaSinEspacios() {
        String linea1 = "5/6/2026,0001,DEBITO,10.50";

        assertThat(convertir(linea1)).isEqualTo(new Transaccion(LocalDate.of(2026, 6, 5), "0001", TipoTransaccion.DEBITO, new BigDecimal("10.50")));
    }

    // Entrada: La línea con espacios " 05/06/2026 , 0002 , CREDITO , 80.5 "
    // Esperado: Transaccion[fecha=2026-06-05, cuenta=0002, tipo=CREDITO, monto=80.50]
    // Predicción: verde
    @Test
    public void convertirLineaValidaConnEspaciosYFechaDeDosDigitos() {
        String linea1 = " 05/06/2026 , 0002 , CREDITO , 80.5 ";

        assertThat(convertir(linea1)).isEqualTo(new Transaccion(LocalDate.of(2026, 6, 5), "0002", TipoTransaccion.CREDITO, new BigDecimal("80.50")));
    }

    // Entrada: La línea "7/6/2026,0001,DEBITO,10.525"
    // Esperado: Transaccion[fecha=2026-06-07, cuenta=0001, tipo=DEBITO, monto=10.52]
    // Predicción: verde
    @Test
    public void convertirLineaConMontoDeTresDecimalesBaja() {
        String linea1 = "7/6/2026,0001,DEBITO,10.525";

        assertThat(convertir(linea1)).isEqualTo(new Transaccion(LocalDate.of(2026, 6, 7), "0001", TipoTransaccion.DEBITO, new BigDecimal("10.52")));
    }

    // Entrada: La línea "8/6/2026,0003,CREDITO,10.575"
    // Esperado: Transaccion[fecha=2026-06-08, cuenta=0003, tipo=CREDITO, monto=10.58]
    // Predicción: verde
    @Test
    public void convertirLineaConMontoDeTresDecimalesSube() {
        String linea1 = "8/6/2026,0003,CREDITO,10.575";

        assertThat(convertir(linea1)).isEqualTo(new Transaccion(LocalDate.of(2026, 6, 8), "0003", TipoTransaccion.CREDITO, new BigDecimal("10.58")));
    }

    // Entrada: La línea con columna de menos "5/6/2026,0001,DEBITO"
    // Esperado: columnas: 3
    // Predicción: verde
    @Test
    public void convertirLineaConColumnaDeMenos() {
        String linea1 = "5/6/2026,0001,DEBITO";

        assertThatThrownBy(() -> convertir(linea1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("columnas: 3");
    }

    // Entrada: La línea vacía: ""
    // Esperado: columnas: 1
    // Predicción: verde
    @Test
    public void convertirLineaVacia() {
        String linea1 = "";

        assertThatThrownBy(() -> convertir(linea1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("columnas: 1");
    }

    // Entrada: La línea con fecha inexistente "31/6/2026,0001,DEBITO,10.00"
    // Esperado: fecha inválida: 31/6/2026
    // Predicción: verde
    @Test
    public void convertirLineaConFechaInexistente() {
        String linea1 = "31/6/2026,0001,DEBITO,10.00";

        assertThatThrownBy(() -> convertir(linea1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("fecha inválida: 31/6/2026");
    }

    // Entrada: La línea con tipo de transaccion desconocido "5/6/2026,0001,RETIRO,10.00"
    // Esperado: tipo inválido: RETIRO
    // Predicción: verde
    @Test
    public void convertirLineaConTipoDesconocido() {
        String linea1 = "5/6/2026,0001,RETIRO,10.00";

        assertThatThrownBy(() -> convertir(linea1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("tipo inválido: RETIRO");
    }

    // Entrada: La línea con monto no numérico "5/6/2026,0001,DEBITO,abc"
    // Esperado: Monto inválido: abc
    // Predicción: verde
    @Test
    public void convertirLineaConMontoNoNumerico() {
        String linea1 = "5/6/2026,0001,DEBITO,abc";

        assertThatThrownBy(() -> convertir(linea1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Monto inválido: abc");
    }
}

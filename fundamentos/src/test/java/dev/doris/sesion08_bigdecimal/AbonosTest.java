package dev.doris.sesion08_bigdecimal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AbonosTest {
    // Entrada: abonos de 10.50, 0.10 y 0.20 de la cuenta "0001".
    // Esperado: 10.80
    // Atrapa: familia 1 (no acumula). Con total.add(abono.monto()) sin reasignar, daría 0.
    // Predicción: verde
    @Test
    public void sumarMontosSumaTodosLosAbonos() {
        Abono a1 = new Abono("0001", new BigDecimal("10.50"));
        Abono a2 = new Abono("0001", new BigDecimal("0.10"));
        Abono a3 = new Abono("0001", new BigDecimal("0.20"));

        BigDecimal total = Abonos.sumarMontos(List.of(a1, a2, a3));

        assertThat(total).isEqualByComparingTo(new BigDecimal("10.80"));
    }

    // Entrada: lista de abonos vacía (List.of()).
    // Esperado: 0
    // Atrapa: familia 2 (valor inicial equivocado). Si total arranca en null, daría null.
    // Predicción: verde
    @Test
    public void sumarMontosRetornaCeroSiLaListaEstaVacia() {
        BigDecimal total = Abonos.sumarMontos(List.of());

        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }

    // Entrada: abonos de "0.10" y "0.20" de la cuenta "0001" (montos creados con String).
    // Esperado: 0.30
    // Atrapa (opcional): familia 5 (tipo o precisión). Si el método suma con double, daría 0.30000000000000004.
    // Predicción: verde
    @Test
    public void sumarMontosDevuelveTotalExactoConDecimales() {
        Abono a1 = new Abono("0001", new BigDecimal("0.1"));
        Abono a2 = new Abono("0001", new BigDecimal("0.2"));

        BigDecimal total = Abonos.sumarMontos(List.of(a1, a2));

        assertThat(total).isEqualByComparingTo("0.3");
    }

}

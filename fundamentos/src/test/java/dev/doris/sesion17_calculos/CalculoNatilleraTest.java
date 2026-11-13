package dev.doris.sesion17_calculos;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculoNatilleraTest {
    // Entrada: Una lista vacía List.of()
    // Esperado: 2 claves. CUOTA = 0 y RETIRO = 0
    // Predicción: verde
    @Test
    public void calculoNatilleraTotalPorTipoConListaVacia() {
        Map<TipoAporte, BigDecimal> totalPorTipo = CalculoNatillera.totalesPorTipo(List.of());

        assertThat(totalPorTipo).hasSize(2);
        assertThat(totalPorTipo.get(TipoAporte.CUOTA)).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(totalPorTipo.get(TipoAporte.RETIRO)).isEqualByComparingTo(BigDecimal.ZERO);
    }

    // Entrada: CUOTA 50.00 (Pedro), CUOTA 30.00 (Luis), RETIRO 20.25 (Pedro) y RETIRO 10.50 (Marta)
    // Esperado: 3 claves. Pedro = 29.75, Luis = 30.00 y Marta = -10.50
    // Predicción: verde
    @Test
    public void calcularNatilleraSaldoPorSocio() {
        List<Aporte> aportes = List.of(
                new Aporte("Pedro", TipoAporte.CUOTA, new BigDecimal("50.00")),
                new Aporte("Luis", TipoAporte.CUOTA, new BigDecimal("30.00")),
                new Aporte("Pedro", TipoAporte.RETIRO, new BigDecimal("20.25")),
                new Aporte("Marta", TipoAporte.RETIRO, new BigDecimal("10.50"))
        );
        Map<String, BigDecimal> saldoPorSocio = CalculoNatillera.saldoPorSocio(aportes);
        assertThat(saldoPorSocio).hasSize(3);
        assertThat(saldoPorSocio.get("Pedro")).isEqualByComparingTo(new BigDecimal("29.75"));
        assertThat(saldoPorSocio.get("Luis")).isEqualByComparingTo(new BigDecimal("30.00"));
        assertThat(saldoPorSocio.get("Marta")).isEqualByComparingTo(new BigDecimal("-10.50"));
    }
}

package dev.doris.sesion18_formato;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static dev.doris.sesion18_formato.ResumenServicios.armar;
import static org.assertj.core.api.Assertions.assertThat;

public class ResumenServiciosTest {

    // Entrada: List<PagoServicio> pagos con dos pagos: new PagoServicio("Agua", 2, new BigDecimal("45.5")) y new PagoServicio("Gas", 0, BigDecimal.ZERO)
    // Esperado:
    // Servicio   Pagos       Total
    // Agua           2       45.50
    // Gas            0        0.00
    // Servicios: 2
    // Predicción: verde
    @Test
    public void armarListaConDosPagos() {
        List<PagoServicio> pagos = new ArrayList<>();
        pagos.add(new PagoServicio("Agua", 2, new BigDecimal("45.5")));
        pagos.add(new PagoServicio("Gas", 0, BigDecimal.ZERO));

        assertThat(armar(pagos)).isEqualTo("Servicio   Pagos       Total\n" +
                "Agua           2       45.50\n" +
                "Gas            0        0.00\n" +
                "Servicios: 2");
    }
}

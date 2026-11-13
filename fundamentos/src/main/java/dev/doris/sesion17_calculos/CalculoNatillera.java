package dev.doris.sesion17_calculos;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculoNatillera {
    public static Map<TipoAporte, BigDecimal> totalesPorTipo(List<Aporte> aportes) {
        Map<TipoAporte, BigDecimal> totalPorTipo = new HashMap<>();

        for (TipoAporte tipo : TipoAporte.values()) {
            totalPorTipo.put(tipo, BigDecimal.ZERO);
        }

        for (Aporte aporte : aportes) {
            BigDecimal actual = totalPorTipo.get(aporte.tipo());
            totalPorTipo.put(aporte.tipo(), actual.add(aporte.monto()));
        }
        return totalPorTipo;
    }

    public static Map<String, BigDecimal> saldoPorSocio(List<Aporte> aportes) {
        Map<String, BigDecimal> saldoPorSocio = new HashMap<>();

        for (Aporte aporte : aportes) {
            BigDecimal saldo = saldoPorSocio.getOrDefault(aporte.socio(), BigDecimal.ZERO);
            if (aporte.tipo() == TipoAporte.CUOTA) {
                saldo = saldo.add(aporte.monto());
            } else {
                saldo = saldo.subtract(aporte.monto());
            }
            saldoPorSocio.put(aporte.socio(), saldo);
        }
        return saldoPorSocio;
    }
}

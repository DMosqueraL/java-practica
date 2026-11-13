package dev.doris.sesion17_calculos;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Parte1SinFor {
    public static void main(String[] args) {
        // Planilla 1: solo se anota lo que llega
        Map<TipoMovimiento, BigDecimal> planilla1 = new HashMap<>();
        planilla1.put(TipoMovimiento.INGRESO, new BigDecimal("150.75"));
        System.out.println("[1] " + planilla1);
        System.out.println("[2] GASTO: " + planilla1.get(TipoMovimiento.GASTO));

        // Planilla 2: primero las dos filas en cero, después lo que llega
        Map<TipoMovimiento, BigDecimal> planilla2 = new HashMap<>();
        planilla2.put(TipoMovimiento.INGRESO, BigDecimal.ZERO);
        planilla2.put(TipoMovimiento.GASTO, BigDecimal.ZERO);
        planilla2.put(TipoMovimiento.INGRESO, new BigDecimal("150.75"));
        System.out.println("[3] GASTO: " + planilla2.get(TipoMovimiento.GASTO));
    }
}

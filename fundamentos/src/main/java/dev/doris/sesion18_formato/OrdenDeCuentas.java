package dev.doris.sesion18_formato;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class OrdenDeCuentas {
    public static void main(String[] args) {
        Map<String, BigDecimal> conHashMap = new HashMap<>();
        conHashMap.put("0003", new BigDecimal("50.00"));
        conHashMap.put("0001", new BigDecimal("89.75"));
        conHashMap.put("0002", new BigDecimal("-20.50"));
        System.out.println("HashMap: " + conHashMap.keySet());

        Map<String, BigDecimal> conTreeMap = new TreeMap<>();
        conTreeMap.put("0003", new BigDecimal("50.00"));
        conTreeMap.put("0001", new BigDecimal("89.75"));
        conTreeMap.put("0002", new BigDecimal("-20.50"));
        System.out.println("TreeMap: " + conTreeMap.keySet());
        System.out.println("get:     " + conTreeMap.get("0002"));
    }
}

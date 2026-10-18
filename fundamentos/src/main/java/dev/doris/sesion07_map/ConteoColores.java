package dev.doris.sesion07_map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Una lista List.of("rojo", "azul", "rojo", "verde", "azul", "rojo").
 * Un Map<String, Integer> que cuente cuántas veces aparece cada color,
 * con un for-each sobre la lista.
 * Imprime el mapa completo.
 * Con un for-each sobre keySet(), imprime solo los colores que aparecen más de una vez,
 * con el formato rojo -> 3.
 */
public class ConteoColores {
    public static void main(String[] args){
        List<String> colores = List.of("rojo", "azul", "rojo", "verde", "azul", "rojo");

        Map<String, Integer> conteo = new HashMap<>();

        for (String color : colores){
            conteo.put(color, conteo.getOrDefault(color, 0) + 1);
        }
        //Predicción:
        //{rojo=3, azul=2, verde=1} ---> No necesariamente en este orden
        System.out.println(conteo);

        for (String color : conteo.keySet()){
            if (conteo.get(color) > 1){
                //Predicción:
                //rojo -> 3
                //azul -> 2
                System.out.println(color + " -> " + conteo.get(color));
            }
        }
    }
}

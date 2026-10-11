package dev.doris.sesion6;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static dev.doris.sesion6.Cuentas.cuentasDistintas;
import static dev.doris.sesion6.Cuentas.hayCuentasRepetidas;
import static org.assertj.core.api.Assertions.assertThat;

public class CuentasTest {

    //Predicción: verde
    @Test
    public void cuentasDistintasAgrupaPorNumero() {

        Movimiento m1 = new Movimiento("1", new Cuenta("0001", "Doris"), 1100);
        Movimiento m2 = new Movimiento("2", new Cuenta("0001", "Carmen"), 1000);
        Movimiento m3 = new Movimiento("3", new Cuenta("0002", "Luisa"), 100);
        Movimiento m4 = new Movimiento("4", new Cuenta("0002", "Luisa"), 50);

        List<Movimiento> movimientos = List.of(m1, m2, m3, m4);

        Set<Cuenta> cuentas = cuentasDistintas(movimientos);

        assertThat(cuentas).contains(m1.cuenta(), m3.cuenta());
        assertThat(cuentas).hasSize(2);
    }

    //Predicción: verde
    @Test
    public void cuentasDistintasListaVacia() {

        List<Movimiento> movimientos = List.of();

        Set<Cuenta> cuentas = cuentasDistintas(movimientos);

        assertThat(cuentas).isEmpty();
    }

    //Predicción: verde
    @Test
    public void hayCuentasRepetidasTrue() {
        Cuenta c1 = new Cuenta("0001", "Doris");
        Cuenta c2 = new Cuenta("0001", "Carmen");
        Cuenta c3 = new Cuenta("0002", "Luisa");
        Cuenta c4 = new Cuenta("0002", "Luisa");

        List<Cuenta> listaCuentas = List.of(c1, c2, c3, c4);

        assertThat(hayCuentasRepetidas(listaCuentas)).isTrue();
    }

    //Predicción: verde
    @Test
    public void hayCuentasRepetidasFalse() {
        Cuenta c1 = new Cuenta("0001", "Doris");
        Cuenta c2 = new Cuenta("0002", "Carmen");
        Cuenta c3 = new Cuenta("0003", "Luisa");
        Cuenta c4 = new Cuenta("0004", "Luisa");

        List<Cuenta> listaCuentas = List.of(c1, c2, c3, c4);

        assertThat(hayCuentasRepetidas(listaCuentas)).isFalse();
    }

    //Predicción: verde
    @Test
    public void cuentaConHashCodeYEqualsIguales(){

        Cuenta c1 = new Cuenta("0001", "Doris");
        Cuenta c2 = new Cuenta("0001", "Carmen");

        assertThat(c1.hashCode()).isEqualTo(c2.hashCode());
        assertThat(c1).isEqualTo(c2);
    }
}

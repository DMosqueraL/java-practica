package dev.doris.sesion7;

import dev.doris.sesion6.Cuenta;
import dev.doris.sesion6.Movimiento;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static dev.doris.sesion7.Movimientos.agruparPorCuenta;
import static org.assertj.core.api.Assertions.assertThat;

public class MovimientosTest {
    // Caso: 5 movimientos de 2 cuentas, intercalados (no llegan ya juntos por cuenta).
    // Entrada: m1, m3 y m5 con la cuenta "0001"; m2 y m4 con la cuenta "0002".
    //          Orden de la lista: m1, m2, m3, m4, m5.
    // Comprueba: cuántas cuentas hay en el mapa (2) y, para cada cuenta, qué movimientos
    //            quedaron en su lista y en qué orden (0001 -> m1, m3, m5; 0002 -> m2, m4).
    // Por qué: atrapa una agrupación que cree una lista nueva en cada movimiento (se
    //          perderían los anteriores), que mezcle movimientos de cuentas distintas,
    //          que deje movimientos fuera o que cambie el orden en que llegaron.
    // Predicción: verde
    @Test
    public void agruparPorCuentaSeparaLosMovimientosDeCadaCuenta() {
        Cuenta cuenta1 = new Cuenta("0001", "Doris");
        Cuenta cuenta2 = new Cuenta("0002", "Luisa");

        Movimiento m1 = new Movimiento("1", cuenta1, 1100);
        Movimiento m2 = new Movimiento("2", cuenta2, 100);
        Movimiento m3 = new Movimiento("3", cuenta1, 1000);
        Movimiento m4 = new Movimiento("4", cuenta2, 50);
        Movimiento m5 = new Movimiento("5", cuenta1, 300);

        Map<Cuenta, List<Movimiento>> porCuenta = agruparPorCuenta(List.of(m1, m2, m3, m4, m5));

        assertThat(porCuenta).hasSize(2);
        assertThat(porCuenta.get(cuenta1)).containsExactly(m1, m3, m5);
        assertThat(porCuenta.get(cuenta2)).containsExactly(m2, m4);
    }

    // Caso: Lista vacía
    // Entrada: Una lista de movimientos vacía (List.of())
    // Comprueba: El método devuelve un Map<Cuenta, List<Movimiento>> y verifico que sea vacío con isEmpty().
    // Por qué: Atrapa un método que devuelve null cuando recibe una lista de movimientos vacía.
    // Predicción: verde
    @Test
    public void agruparPorCuentaSiSeRecibeUnaListaVacia(){
        Map<Cuenta, List<Movimiento>> grupoCuentas = agruparPorCuenta(List.of());

        assertThat(grupoCuentas).isEmpty();
    }

    // Caso: Todos los movimientos de una misma cuenta
    // Entrada: Una cuenta y varios movimientos (3) para esta cuenta. Movimientos m1, m2, m3 para la cuenta 0001, en este mismo orden los movimientos.
    // Comprueba: El método devuelve un Map<Cuenta, List<Movimiento>> y verifico que el map tiene 1 cuenta (hasSize(1)), y la lista de 0001
    //            contenga exactamente m1, m2 y m3 en ese mismo orden (containsExactly())
    // Por qué: Atrapa un método que reemplaza la lista de la cuenta cada vez que llega un movimiento, y deja solo el último movimiento.
    // Predicción: verde
    @Test
    public void agruparPorCuentasTodosLosMovimientosDeUnaMismaCuenta(){
        Cuenta cuenta1 = new Cuenta("0001", "Doris");

        Movimiento m1 = new Movimiento("1", cuenta1, 1100);
        Movimiento m2 = new Movimiento("2", cuenta1, 100);
        Movimiento m3 = new Movimiento("3", cuenta1, 3000);

        Map<Cuenta, List<Movimiento>> porCuenta = agruparPorCuenta(List.of(m1, m2, m3));

        assertThat(porCuenta.get(cuenta1)).containsExactly(m1, m2, m3);
        assertThat(porCuenta).hasSize(1);
    }

    // Caso: dos cuentas con el mismo número y titulares distintos.
    // Entrada: cuentaA = new Cuenta("0001", "Doris") y cuentaB = new Cuenta("0001", "Carmen")
    //          (dos objetos distintos); m1 con cuentaA, m2 con cuentaB; lista List.of(m1, m2).
    // Comprueba: el Map tiene 1 sola cuenta (hasSize(1)) y la lista de esa cuenta contiene
    //            exactamente m1 y m2 en ese orden (containsExactly).
    // Por qué: atrapa un Map que no compare las cuentas por su número (por titular o por
    //          referencia): quedarían 2 claves, cada una con un solo movimiento.
    // Predicción: verde
    @Test
    public void dosCuentasConElMismoNumeroYTitularesDistintos(){
        Cuenta cuenta1 = new Cuenta("0001", "Doris");
        Cuenta cuenta2 = new Cuenta("0001", "Carmen");

        Movimiento m1 = new Movimiento("1", cuenta1, 1100);
        Movimiento m2 = new Movimiento("2", cuenta2, 4000);

        Map<Cuenta, List<Movimiento>> porCuenta = agruparPorCuenta(List.of(m1, m2));

        assertThat(porCuenta).hasSize(1);
        assertThat(porCuenta.get(cuenta1)).containsExactly(m1, m2);
    }

    // Caso: Consultar una cuenta que no está en el Map.
    // Entrada: cuenta1 = new Cuenta("0001", "Doris") y cuenta2 = new Cuenta("0002", "Carmen")
    //          m1 para cuenta1, m2 para cuenta2 y cuenta3 nunca aparece en los movimientos.
    // Comprueba: containsKey(cuenta3) es false, get(cuenta3) es null y getOrDefault(cuenta3, List.of()) devuelve una lista vacía.
    // Por qué: atrapa un cambio que haga que consultar una cuenta ausente devuelva una lista
    //          vacía o lance una excepción en lugar de null, y quien llama espera null.
    // Predicción: verde
    @Test
    public void consultarUnaCuentaQueNoEstaEnElMap(){
        Cuenta cuenta1 = new Cuenta("0001", "Doris");
        Cuenta cuenta2 = new Cuenta("0002", "Carmen");
        Cuenta cuenta3 = new Cuenta("0003", "Luis");

        Movimiento m1 = new Movimiento("1", cuenta1, 1100);
        Movimiento m2 = new Movimiento("2", cuenta2, 100);
        Movimiento m3 = new Movimiento("3", cuenta1, 1000);
        Movimiento m4 = new Movimiento("4", cuenta2, 50);
        Movimiento m5 = new Movimiento("5", cuenta1, 300);

        Map<Cuenta, List<Movimiento>> porCuenta = agruparPorCuenta(List.of(m1, m2, m3, m4, m5));

        assertThat(porCuenta.containsKey(cuenta3)).isFalse();
        assertThat(porCuenta.get(cuenta3)).isNull();
        assertThat(porCuenta.getOrDefault(cuenta3, List.of())).isEmpty();
    }
}

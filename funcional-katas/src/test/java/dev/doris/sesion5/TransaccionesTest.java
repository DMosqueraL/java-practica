package dev.doris.sesion5;

import org.junit.jupiter.api.Test;

import java.util.List;

import static dev.doris.sesion5.Transacciones.filtrarPorTipo;
import static dev.doris.sesion5.Transacciones.sumarMontos;
import static org.assertj.core.api.Assertions.assertThat;

class TransaccionesTest {
    //Predicción: verde
    @Test
    public void filtrarPorTipoDevuelveSoloLasDelTipoPedido(){

        Transaccion trx1 = new Transaccion("1", 100, TipoTransaccion.CREDITO);
        Transaccion trx2 = new Transaccion("2", 200, TipoTransaccion.DEBITO);
        Transaccion trx3 = new Transaccion("3", 300, TipoTransaccion.DEBITO);
        Transaccion trx4 = new Transaccion("4", 400, TipoTransaccion.CREDITO);

        List<Transaccion> listaTest = List.of(trx1, trx2, trx3, trx4);

        List<Transaccion> listaFinal = filtrarPorTipo(listaTest, TipoTransaccion.DEBITO);

        assertThat(listaFinal).containsExactly(trx2, trx3);
    }

    //Predicción: verde
    @Test
    public void filtrarSinCoincidencias(){

        Transaccion trx1 = new Transaccion("1", 100, TipoTransaccion.CREDITO);
        Transaccion trx2 = new Transaccion("2", 200, TipoTransaccion.CREDITO);

        List<Transaccion> listaTest = List.of(trx1, trx2);

        List<Transaccion> listaFinal = filtrarPorTipo(listaTest, TipoTransaccion.DEBITO);

        assertThat(listaFinal).isEmpty();
    }

    //Predicción: verde
    @Test
    public void filtrarConListaDeEntradaVacia(){

        List<Transaccion> listaTest = List.of();

        List<Transaccion> listaFinal = filtrarPorTipo(listaTest, TipoTransaccion.DEBITO);

        assertThat(listaFinal).isEmpty();
    }

    //Predicción: verde
    @Test
    public void sumarMontosDeUnaListaConocida(){

        Transaccion trx1 = new Transaccion("1", 100, TipoTransaccion.CREDITO);
        Transaccion trx2 = new Transaccion("2", 200, TipoTransaccion.DEBITO);
        Transaccion trx3 = new Transaccion("3", 300, TipoTransaccion.DEBITO);
        Transaccion trx4 = new Transaccion("4", 400, TipoTransaccion.CREDITO);

        List<Transaccion> listaTest = List.of(trx1, trx2, trx3, trx4);

        double total = sumarMontos(listaTest);

        assertThat(total).isEqualTo(1000.0);
    }

    //Predicción: verde
    @Test
    public void sumarMontosDeUnaListaVacia(){

        List<Transaccion> listaTest = List.of();

        double total = sumarMontos(listaTest);

        assertThat(total).isEqualTo(0.0);
    }
}

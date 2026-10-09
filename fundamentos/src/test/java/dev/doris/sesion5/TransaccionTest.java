package dev.doris.sesion5;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransaccionTest {

    // Predicción: verde
    @Test
    public void dosTransaccionesConLosMismosDatosSonIguales(){
        Transaccion trx1 = new Transaccion("1", 100, TipoTransaccion.CREDITO);
        Transaccion trx2 = new Transaccion("1", 100, TipoTransaccion.CREDITO);

        assertThat(trx1).isEqualTo(trx2);
    }

    // Predicción: verde
    @Test
    public void dosTransaccionesConDistintoTipoNoSonIguales(){
        Transaccion trx1 = new Transaccion("1", 100, TipoTransaccion.CREDITO);
        Transaccion trx2 = new Transaccion("1", 100, TipoTransaccion.DEBITO);

        assertThat(trx1).isNotEqualTo(trx2);
    }
}
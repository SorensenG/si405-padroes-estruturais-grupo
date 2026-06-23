package br.unicamp.padroesestruturais.legacy;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.gateway.PaySecureGatewayAdapter;
import br.unicamp.padroesestruturais.legacy.gateway.WalletPayGatewayAdapter;
import br.unicamp.padroesestruturais.legacy.service.CobrancaService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GatewayCobrancaAdapterTest {

    @Test
    void adapterPaySecureDeveAprovarTransacaoDentroDoLimite() {
        PaySecureGatewayAdapter adapter = new PaySecureGatewayAdapter();
        Pedido pedido = new Pedido("PED-010", "Cliente Cartao", "Monitor", 800.0);

        ResultadoCobranca resultado = adapter.cobrar(pedido, 800.0, FormaPagamento.CARTAO_CREDITO);

        assertEquals("APROVADA", resultado.getStatus());
        assertEquals(FormaPagamento.CARTAO_CREDITO, resultado.getFormaPagamento());
        assertNotNull(resultado.getReferencia());
        assertTrue(resultado.getReferencia().startsWith("PSEC-"));
    }

    @Test
    void adapterPaySecureDeveRecusarTransacaoAcimaDoLimite() {
        PaySecureGatewayAdapter adapter = new PaySecureGatewayAdapter();
        Pedido pedido = new Pedido("PED-011", "Cliente Cartao", "Servidor", 15000.0);

        ResultadoCobranca resultado = adapter.cobrar(pedido, 15000.0, FormaPagamento.CARTAO_CREDITO);

        assertEquals("RECUSADA", resultado.getStatus());
    }

    @Test
    void adapterWalletPayDeveAprovarCobrancaDentroDoLimite() {
        WalletPayGatewayAdapter adapter = new WalletPayGatewayAdapter();
        Pedido pedido = new Pedido("PED-012", "Cliente Wallet", "Tablet", 1200.0);

        ResultadoCobranca resultado = adapter.cobrar(pedido, 1200.0, FormaPagamento.CARTEIRA_DIGITAL);

        assertEquals("APROVADA", resultado.getStatus());
        assertEquals(FormaPagamento.CARTEIRA_DIGITAL, resultado.getFormaPagamento());
        assertNotNull(resultado.getReferencia());
        assertTrue(resultado.getReferencia().startsWith("WPAY-"));
    }

    @Test
    void adapterWalletPayDeveRecusarCobrancaAcimaDoLimite() {
        WalletPayGatewayAdapter adapter = new WalletPayGatewayAdapter();
        Pedido pedido = new Pedido("PED-013", "Cliente Wallet", "Servidor", 15000.0);

        ResultadoCobranca resultado = adapter.cobrar(pedido, 15000.0, FormaPagamento.CARTEIRA_DIGITAL);

        assertEquals("RECUSADA", resultado.getStatus());
    }

    @Test
    void serviceDeveCobrarViaCarteiraDigital() {
        CobrancaService service = new CobrancaService();
        Pedido pedido = new Pedido("PED-014", "Cliente Wallet", "Teclado", 350.0);

        ResultadoCobranca resultado = service.cobrar(pedido, FormaPagamento.CARTEIRA_DIGITAL, false, false, false, false);

        assertEquals("APROVADA", resultado.getStatus());
        assertEquals(FormaPagamento.CARTEIRA_DIGITAL, resultado.getFormaPagamento());
        assertTrue(resultado.getReferencia().startsWith("WPAY-"));
    }
}

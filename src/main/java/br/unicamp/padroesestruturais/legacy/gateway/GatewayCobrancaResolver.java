package br.unicamp.padroesestruturais.legacy.gateway;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;

import java.util.EnumMap;
import java.util.Map;

public class GatewayCobrancaResolver {

    private final Map<FormaPagamento, GatewayCobranca> gateways;

    public GatewayCobrancaResolver() {
        this(criarGatewaysPadrao());
    }

    public GatewayCobrancaResolver(Map<FormaPagamento, GatewayCobranca> gateways) {
        this.gateways = new EnumMap<>(gateways);
    }

    public GatewayCobranca resolver(FormaPagamento forma) {
        GatewayCobranca gateway = gateways.get(forma);

        if (gateway == null) {
            throw new IllegalArgumentException("Forma de pagamento nao suportada: " + forma);
        }

        return gateway;
    }

    private static Map<FormaPagamento, GatewayCobranca> criarGatewaysPadrao() {
        Map<FormaPagamento, GatewayCobranca> gateways = new EnumMap<>(FormaPagamento.class);
        GatewayCobranca gatewayInterno = new GatewayPagamentoInternoAdapter();

        gateways.put(FormaPagamento.BOLETO, gatewayInterno);
        gateways.put(FormaPagamento.PIX, gatewayInterno);
        gateways.put(FormaPagamento.CARTAO_CREDITO, new PaySecureGatewayAdapter());
        gateways.put(FormaPagamento.CARTEIRA_DIGITAL, new WalletPayGatewayAdapter());

        return gateways;
    }
}

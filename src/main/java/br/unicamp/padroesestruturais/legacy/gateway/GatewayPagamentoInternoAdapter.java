package br.unicamp.padroesestruturais.legacy.gateway;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;

public class GatewayPagamentoInternoAdapter implements GatewayCobranca {

    private final GatewayPagamentoInterno gateway;

    public GatewayPagamentoInternoAdapter() {
        this(new GatewayPagamentoInterno());
    }

    public GatewayPagamentoInternoAdapter(GatewayPagamentoInterno gateway) {
        this.gateway = gateway;
    }

    @Override
    public ResultadoCobranca cobrar(Pedido pedido, double valor, FormaPagamento forma) {
        if (forma != FormaPagamento.BOLETO && forma != FormaPagamento.PIX) {
            throw new IllegalArgumentException("Gateway interno nao suporta a forma: " + forma);
        }

        return gateway.cobrar(pedido.getId(), pedido.getCliente(), valor, forma);
    }
}

package br.unicamp.padroesestruturais.legacy.gateway;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.externo.ChargeRequest;
import br.unicamp.padroesestruturais.legacy.externo.ChargeResponse;
import br.unicamp.padroesestruturais.legacy.externo.ChargeStatus;
import br.unicamp.padroesestruturais.legacy.externo.WalletPaySDK;

public class WalletPayGatewayAdapter implements GatewayCobranca {

    private final WalletPaySDK walletPaySDK;

    public WalletPayGatewayAdapter() {
        this(new WalletPaySDK());
    }

    public WalletPayGatewayAdapter(WalletPaySDK walletPaySDK) {
        this.walletPaySDK = walletPaySDK;
    }

    @Override
    public ResultadoCobranca cobrar(Pedido pedido, double valor, FormaPagamento forma) {
        if (forma != FormaPagamento.CARTEIRA_DIGITAL) {
            throw new IllegalArgumentException("WalletPay nao suporta a forma: " + forma);
        }

        ChargeRequest request = new ChargeRequest(pedido.getId(), pedido.getCliente(), converterParaCentavos(valor));
        ChargeResponse response = walletPaySDK.charge(request);
        String status = response.getStatus() == ChargeStatus.CONFIRMED ? "APROVADA" : "RECUSADA";

        return new ResultadoCobranca(pedido.getId(), valor, status, response.getWalletTransactionId(), forma);
    }

    private long converterParaCentavos(double valor) {
        return Math.round(valor * 100);
    }
}

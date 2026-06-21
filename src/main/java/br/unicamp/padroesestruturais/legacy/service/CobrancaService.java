package br.unicamp.padroesestruturais.legacy.service;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.gateway.GatewayCobranca;
import br.unicamp.padroesestruturais.legacy.gateway.GatewayCobrancaResolver;

import java.util.ArrayList;
import java.util.List;

public class CobrancaService {

    private static final double TAXA_DESCONTO_FIDELIDADE = 0.05;
    private static final double TAXA_JUROS_PARCELAMENTO = 0.0299;
    private static final double TAXA_OPERACAO_INTERNACIONAL = 0.05;
    private static final double VALOR_SEGURO = 4.90;

    private final GatewayCobrancaResolver gatewayResolver;

    public CobrancaService() {
        this(new GatewayCobrancaResolver());
    }

    public CobrancaService(GatewayCobrancaResolver gatewayResolver) {
        this.gatewayResolver = gatewayResolver;
    }

    public ResultadoCobranca cobrar(Pedido pedido, FormaPagamento forma,
                                     boolean aplicarDescontoFidelidade,
                                     boolean aplicarJurosParcelamento,
                                     boolean aplicarTaxaInternacional,
                                     boolean aplicarSeguro) {

        double valorFinal = calcularValorFinal(pedido.getValorBase(), aplicarDescontoFidelidade,
                aplicarJurosParcelamento, aplicarTaxaInternacional, aplicarSeguro);

        return cobrarComValorFinal(pedido, forma, valorFinal);
    }

    public List<ResultadoCobranca> cobrarEmLote(List<Pedido> pedidos, FormaPagamento forma,
                                                  boolean aplicarDescontoFidelidade,
                                                  boolean aplicarJurosParcelamento,
                                                  boolean aplicarTaxaInternacional,
                                                  boolean aplicarSeguro) {

        List<ResultadoCobranca> resultados = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            resultados.add(cobrar(pedido, forma, aplicarDescontoFidelidade,
                    aplicarJurosParcelamento, aplicarTaxaInternacional, aplicarSeguro));
        }

        return resultados;
    }

    public double calcularValorFinal(double valorBase,
                                      boolean aplicarDescontoFidelidade,
                                      boolean aplicarJurosParcelamento,
                                      boolean aplicarTaxaInternacional,
                                      boolean aplicarSeguro) {

        double valor = valorBase;

        if (aplicarDescontoFidelidade) {
            valor = valor - (valor * TAXA_DESCONTO_FIDELIDADE);
        }

        if (aplicarJurosParcelamento) {
            valor = valor + (valor * TAXA_JUROS_PARCELAMENTO);
        }

        if (aplicarTaxaInternacional) {
            valor = valor + (valor * TAXA_OPERACAO_INTERNACIONAL);
        }

        if (aplicarSeguro) {
            valor = valor + VALOR_SEGURO;
        }

        return valor;
    }

    private ResultadoCobranca cobrarComValorFinal(Pedido pedido, FormaPagamento forma, double valorFinal) {
        GatewayCobranca gateway = gatewayResolver.resolver(forma);
        return gateway.cobrar(pedido, valorFinal, forma);
    }
}

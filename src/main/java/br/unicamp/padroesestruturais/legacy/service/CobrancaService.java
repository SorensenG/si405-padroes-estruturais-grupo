package br.unicamp.padroesestruturais.legacy.service;

import br.unicamp.padroesestruturais.legacy.ajuste.AjusteValor;
import br.unicamp.padroesestruturais.legacy.ajuste.DescontoFidelidadeDecorator;
import br.unicamp.padroesestruturais.legacy.ajuste.JurosParcelamentoDecorator;
import br.unicamp.padroesestruturais.legacy.ajuste.SeguroTransacaoDecorator;
import br.unicamp.padroesestruturais.legacy.ajuste.TaxaOperacaoInternacionalDecorator;
import br.unicamp.padroesestruturais.legacy.ajuste.ValorBaseCobranca;
import br.unicamp.padroesestruturais.legacy.ajuste.ValorCobranca;
import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import br.unicamp.padroesestruturais.legacy.gateway.GatewayCobranca;
import br.unicamp.padroesestruturais.legacy.gateway.GatewayCobrancaResolver;

import java.util.ArrayList;
import java.util.List;

public class CobrancaService {

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

        return cobrar(pedido, forma, criarAjustesLegados(aplicarDescontoFidelidade,
                aplicarJurosParcelamento, aplicarTaxaInternacional, aplicarSeguro));
    }

    public ResultadoCobranca cobrar(Pedido pedido, FormaPagamento forma, List<AjusteValor> ajustes) {
        double valorFinal = calcularValorFinal(pedido.getValorBase(), ajustes);

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

    public List<ResultadoCobranca> cobrarEmLote(List<Pedido> pedidos, FormaPagamento forma,
                                                  List<AjusteValor> ajustes) {

        List<ResultadoCobranca> resultados = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            resultados.add(cobrar(pedido, forma, ajustes));
        }

        return resultados;
    }

    public double calcularValorFinal(double valorBase,
                                      boolean aplicarDescontoFidelidade,
                                      boolean aplicarJurosParcelamento,
                                      boolean aplicarTaxaInternacional,
                                      boolean aplicarSeguro) {

        return calcularValorFinal(valorBase, criarAjustesLegados(aplicarDescontoFidelidade,
                aplicarJurosParcelamento, aplicarTaxaInternacional, aplicarSeguro));
    }

    public double calcularValorFinal(double valorBase, List<AjusteValor> ajustes) {
        ValorCobranca valor = new ValorBaseCobranca(valorBase);

        if (ajustes != null) {
            for (AjusteValor ajuste : ajustes) {
                valor = ajuste.aplicarEm(valor);
            }
        }

        return valor.calcular();
    }

    private ResultadoCobranca cobrarComValorFinal(Pedido pedido, FormaPagamento forma, double valorFinal) {
        GatewayCobranca gateway = gatewayResolver.resolver(forma);
        return gateway.cobrar(pedido, valorFinal, forma);
    }

    private List<AjusteValor> criarAjustesLegados(boolean aplicarDescontoFidelidade,
                                                   boolean aplicarJurosParcelamento,
                                                   boolean aplicarTaxaInternacional,
                                                   boolean aplicarSeguro) {
        List<AjusteValor> ajustes = new ArrayList<>();

        if (aplicarDescontoFidelidade) {
            ajustes.add(DescontoFidelidadeDecorator::new);
        }

        if (aplicarJurosParcelamento) {
            ajustes.add(JurosParcelamentoDecorator::new);
        }

        if (aplicarTaxaInternacional) {
            ajustes.add(TaxaOperacaoInternacionalDecorator::new);
        }

        if (aplicarSeguro) {
            ajustes.add(SeguroTransacaoDecorator::new);
        }

        return ajustes;
    }
}

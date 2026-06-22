package br.unicamp.padroesestruturais.legacy.ajuste;

public class TaxaAntecipacaoRecebiveisDecorator extends AjusteValorDecorator {

    private static final double TAXA_ANTECIPACAO_RECEBIVEIS = 0.015;

    public TaxaAntecipacaoRecebiveisDecorator(ValorCobranca valorDecorado) {
        super(valorDecorado);
    }

    @Override
    public double calcular() {
        double valor = valorDecorado();
        return valor + (valor * TAXA_ANTECIPACAO_RECEBIVEIS);
    }
}

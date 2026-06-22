package br.unicamp.padroesestruturais.legacy.ajuste;

public class TaxaOperacaoInternacionalDecorator extends AjusteValorDecorator {

    private static final double TAXA_OPERACAO_INTERNACIONAL = 0.05;

    public TaxaOperacaoInternacionalDecorator(ValorCobranca valorDecorado) {
        super(valorDecorado);
    }

    @Override
    public double calcular() {
        double valor = valorDecorado();
        return valor + (valor * TAXA_OPERACAO_INTERNACIONAL);
    }
}

package br.unicamp.padroesestruturais.legacy.ajuste;

public class TaxaEmissaoNotaFiscalDecorator extends AjusteValorDecorator {

    private static final double VALOR_EMISSAO_NOTA_FISCAL = 2.50;

    public TaxaEmissaoNotaFiscalDecorator(ValorCobranca valorDecorado) {
        super(valorDecorado);
    }

    @Override
    public double calcular() {
        return valorDecorado() + VALOR_EMISSAO_NOTA_FISCAL;
    }
}

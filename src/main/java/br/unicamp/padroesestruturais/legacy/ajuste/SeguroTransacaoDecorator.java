package br.unicamp.padroesestruturais.legacy.ajuste;

public class SeguroTransacaoDecorator extends AjusteValorDecorator {

    private static final double VALOR_SEGURO = 4.90;

    public SeguroTransacaoDecorator(ValorCobranca valorDecorado) {
        super(valorDecorado);
    }

    @Override
    public double calcular() {
        return valorDecorado() + VALOR_SEGURO;
    }
}

package br.unicamp.padroesestruturais.legacy.ajuste;

public class JurosParcelamentoDecorator extends AjusteValorDecorator {

    private static final double TAXA_JUROS_PARCELAMENTO = 0.0299;

    public JurosParcelamentoDecorator(ValorCobranca valorDecorado) {
        super(valorDecorado);
    }

    @Override
    public double calcular() {
        double valor = valorDecorado();
        return valor + (valor * TAXA_JUROS_PARCELAMENTO);
    }
}

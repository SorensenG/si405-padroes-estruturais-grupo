package br.unicamp.padroesestruturais.legacy.ajuste;

public class DescontoFidelidadeDecorator extends AjusteValorDecorator {

    private static final double TAXA_DESCONTO_FIDELIDADE = 0.05;

    public DescontoFidelidadeDecorator(ValorCobranca valorDecorado) {
        super(valorDecorado);
    }

    @Override
    public double calcular() {
        double valor = valorDecorado();
        return valor - (valor * TAXA_DESCONTO_FIDELIDADE);
    }
}

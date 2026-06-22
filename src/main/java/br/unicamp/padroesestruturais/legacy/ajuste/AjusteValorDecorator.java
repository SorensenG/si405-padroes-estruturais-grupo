package br.unicamp.padroesestruturais.legacy.ajuste;

public abstract class AjusteValorDecorator implements ValorCobranca {

    private final ValorCobranca valorDecorado;

    protected AjusteValorDecorator(ValorCobranca valorDecorado) {
        this.valorDecorado = valorDecorado;
    }

    protected double valorDecorado() {
        return valorDecorado.calcular();
    }
}

package br.unicamp.padroesestruturais.legacy.ajuste;

public class ValorBaseCobranca implements ValorCobranca {

    private final double valorBase;

    public ValorBaseCobranca(double valorBase) {
        this.valorBase = valorBase;
    }

    @Override
    public double calcular() {
        return valorBase;
    }
}

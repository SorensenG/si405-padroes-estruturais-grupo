package br.unicamp.padroesestruturais.legacy;

import br.unicamp.padroesestruturais.legacy.ajuste.AjusteValor;
import br.unicamp.padroesestruturais.legacy.ajuste.DescontoFidelidadeDecorator;
import br.unicamp.padroesestruturais.legacy.ajuste.JurosParcelamentoDecorator;
import br.unicamp.padroesestruturais.legacy.ajuste.SeguroTransacaoDecorator;
import br.unicamp.padroesestruturais.legacy.ajuste.TaxaAntecipacaoRecebiveisDecorator;
import br.unicamp.padroesestruturais.legacy.ajuste.TaxaEmissaoNotaFiscalDecorator;
import br.unicamp.padroesestruturais.legacy.ajuste.TaxaOperacaoInternacionalDecorator;
import br.unicamp.padroesestruturais.legacy.service.CobrancaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AjusteValorDecoratorTest {

    private CobrancaService service;

    @BeforeEach
    void setUp() {
        service = new CobrancaService();
    }

    @Test
    void deveAplicarTaxaDeAntecipacaoDeRecebiveis() {
        List<AjusteValor> ajustes = List.of(TaxaAntecipacaoRecebiveisDecorator::new);

        double valor = service.calcularValorFinal(1000.0, ajustes);

        assertEquals(1015.0, valor, 0.001);
    }

    @Test
    void deveAplicarTaxaDeEmissaoDeNotaFiscal() {
        List<AjusteValor> ajustes = List.of(TaxaEmissaoNotaFiscalDecorator::new);

        double valor = service.calcularValorFinal(1000.0, ajustes);

        assertEquals(1002.50, valor, 0.001);
    }

    @Test
    void deveComporMultiplosDecoratorsNaOrdemInformada() {
        List<AjusteValor> ajustes = Arrays.asList(
                DescontoFidelidadeDecorator::new,
                JurosParcelamentoDecorator::new,
                TaxaOperacaoInternacionalDecorator::new,
                SeguroTransacaoDecorator::new,
                TaxaAntecipacaoRecebiveisDecorator::new,
                TaxaEmissaoNotaFiscalDecorator::new
        );

        double valor = service.calcularValorFinal(1000.0, ajustes);

        double esperado = 1000.0;
        esperado = esperado - (esperado * 0.05);
        esperado = esperado + (esperado * 0.0299);
        esperado = esperado + (esperado * 0.05);
        esperado = esperado + 4.90;
        esperado = esperado + (esperado * 0.015);
        esperado = esperado + 2.50;

        assertEquals(esperado, valor, 0.001);
    }

    @Test
    void devePermitirCombinarDecoratorsEmOrdemDiferente() {
        List<AjusteValor> ajustes = Arrays.asList(
                TaxaEmissaoNotaFiscalDecorator::new,
                DescontoFidelidadeDecorator::new
        );

        double valor = service.calcularValorFinal(1000.0, ajustes);

        double esperado = 1000.0 + 2.50;
        esperado = esperado - (esperado * 0.05);

        assertEquals(esperado, valor, 0.001);
    }
}

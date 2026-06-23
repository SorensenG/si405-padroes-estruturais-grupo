# Sistema de Cobranca Corporativa - Adapter e Decorator

Atividade pratica da disciplina SI405 - Padroes de Projeto de Software, Faculdade de Tecnologia - Unicamp.

Repositorio da solucao: https://github.com/SorensenG/si405-padroes-estruturais-grupo

## Integrantes

| Integrante | RA |
| --- | --- |
| Daniel Aniceto Rosell | 283988 |
| Davie Schimidt Fonseca | 259908 |
| Hugo Strassa | 246710 |
| Gabriel Sorensen M Traina | 283997 |
| Kaue Samuel Oliveira da Silva | 178449 |
| Kauã Henrique da Silva Andrade | 246165 |

## Organizacao do trabalho

O grupo organizou o desenvolvimento entre 18/06/2026 as 14:00 e 25/06/2026. A divisao principal ficou assim:

| Parte | Responsaveis |
| --- | --- |
| Estrutura base de adapters e gateway interno | Daniel, Gabriel |
| Adapter do PaySecureGateway | Davie, Gabriel |
| Integracao do WalletPaySDK e Carteira Digital | Hugo, Gabriel |
| Refatoracao do CobrancaService e selecao centralizada de gateway | Daniel, Davie, Hugo, Gabriel |
| Decorators dos ajustes existentes | Kaue, Gabriel |
| Decorators de antecipacao de recebiveis e emissao de nota fiscal | Kauã, Kaue, Gabriel |
| Testes, revisao e relatorio tecnico | Todos os integrantes |

Os commits foram separados por etapa e usam trailers `Co-authored-by` para registrar a participacao dos integrantes envolvidos em cada parte.

## Solucao implementada

A refatoracao preserva o projeto original e aplica dois padroes estruturais:

- **Adapter**: `CobrancaService` passa a depender da interface `GatewayCobranca`. As integracoes concretas ficam isoladas em adapters para `GatewayPagamentoInterno`, `PaySecureGateway` e `WalletPaySDK`.
- **Decorator**: o calculo de valor final passa a ser composto por objetos `ValorCobranca`, permitindo combinar ajustes sem aumentar a assinatura principal de cobranca.

Tambem foram adicionados:

- forma de pagamento `CARTEIRA_DIGITAL`;
- taxa de antecipacao de recebiveis de 1,5%;
- taxa de emissao de nota fiscal de R$ 2,50;
- testes para adapters, novos decorators e composicoes de ajustes.

## Como executar

Pre-requisitos:

- Java 17 ou superior
- Apache Maven 3.8 ou superior

Compilar e empacotar:

```bash
mvn clean package -q
```

Executar o sistema interativo:

```bash
java -jar target/sistema-cobranca.jar
```

Rodar os testes:

```bash
mvn test
```

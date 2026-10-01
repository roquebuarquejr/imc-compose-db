# Calculadora IMC

Projeto do desafio de 30 dias da Escola Nova Era Tech. O objetivo é praticar **Jetpack Compose**, **Navigation Compose** e **Room** construindo uma calculadora de Índice de Massa Corporal.

## O que o app faz

1. A **splash** apresenta a Calculadora IMC e segue para o formulário.
2. A tela de **entrada** recebe peso (kg) e altura (m) e calcula o IMC.
3. A tela de **resultado** mostra o valor e a classificação.
4. Cada cálculo é salvo no **Room**, com a data.
5. O **histórico** lista os cálculos, do mais recente para o mais antigo, e permite excluir um registro.

O IMC é `peso / altura²`. A classificação está em `BmiCalculator`:

| IMC | Classificação |
| --- | --- |
| menor que 18,5 | Abaixo do peso |
| 18,5 a 24,9 | Normal |
| 25 a 29,9 | Sobrepeso |
| 30 a 34,9 | Obesidade grau I |
| 35 a 39,9 | Obesidade grau II |
| 40 ou mais | Obesidade grau III |

A navegação entre as telas usa o Navigation Compose. O histórico é lido e gravado direto no DAO, com funções `suspend`, sem `Flow` e sem `ViewModel`.

## Tecnologias

- Kotlin 2.2
- Jetpack Compose e Material 3
- Material Icons
- Navigation Compose 2.9
- Room 2.8, com KSP
- minSdk 24

## Como rodar

Abra o projeto no Android Studio e execute o módulo `app`.

## Estrutura

```
app/src/main/java/br/com/escolanovaeratech/imc_compose_db/
├── MainActivity.kt
├── domain/BmiCalculator.kt    # cálculo, formatação e classificação
├── data/BmiResult.kt          # peso, altura e IMC de um cálculo
└── ui/
    ├── ImcApp.kt
    ├── splash/SplashScreen.kt
    ├── input/InputScreen.kt
    ├── result/ResultScreen.kt
    ├── history/HistoryScreen.kt
    └── theme/
```

As quatro telas já existem e, neste ponto, mostram só um texto. A interface, o grafo de navegação e o banco ficam para serem implementados no desafio.

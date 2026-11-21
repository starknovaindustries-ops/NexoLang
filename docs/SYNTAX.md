# Sintaxe inicial da NexoLang

A sintaxe abaixo corresponde aos recursos aceitos pelo Lexer e Parser iniciais. Ela poderá evoluir antes da versão 1.0.

## Estrutura

Todo programa começa com `INICIO` e termina com `FIM`. Cada comando ocupa sua própria linha.

```nex
INICIO
    MOSTRE "Olá, mundo!"
FIM
```

## Variáveis

`GUARDE` declara uma variável:

```nex
INICIO
    GUARDE nome = "NexoLang"
    GUARDE versao = 0.1
    MOSTRE nome
    MOSTRE versao
FIM
```

## Expressões matemáticas

Operadores disponíveis: `+`, `-`, `*`, `/`. Parênteses alteram a precedência.

```nex
INICIO
    GUARDE resultado = (10 + 5) * 2
    MOSTRE resultado
    MOSTRE -resultado
FIM
```

A precedência é: parênteses, sinais unários, multiplicação/divisão e adição/subtração.

## Regras atuais

- Palavras-chave em maiúsculas.
- Textos entre aspas duplas e sem quebra de linha.
- Identificadores começam com letra ou sublinhado.
- Números inteiros e decimais usam ponto.
- Comandos em linhas separadas.
- Não pode haver conteúdo depois de `FIM`.

## Ainda não implementado

Condições, laços, funções, reatribuição, coleções, módulos, comentários, escape em strings e execução pelo interpretador.

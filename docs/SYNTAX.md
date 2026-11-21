# Sintaxe inicial da NexoLang

Este documento registra a proposta inicial da sintaxe. Ela poderá mudar antes da versão 1.0.

## Estrutura de um programa

Todo programa começa com `INICIO` e termina com `FIM`.

```nex
INICIO
    MOSTRE "Olá!"
FIM
```

## Saída

`MOSTRE` exibe um valor:

```nex
INICIO
    MOSTRE "Olá, mundo!"
    MOSTRE 42
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

## Convenções provisórias

- Palavras-chave são escritas em maiúsculas.
- Texto usa aspas duplas.
- Cada instrução ocupa uma linha.
- O arquivo-fonte usa a extensão `.nex`.

## Recursos ainda não especificados

Condições, laços, funções, coleções, módulos, tratamento de erros e biblioteca padrão serão definidos em etapas futuras.

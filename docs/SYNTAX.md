# Sintaxe NexoLang 0.2

Esta página documenta os recursos presentes no Lexer, Parser e Interpreter atuais.

## Estrutura e saída

~~~nex
INICIO
    MOSTRE "Olá, NexoOS!"
FIM
~~~

## Variáveis e reatribuição

GUARDE declara uma variável. Depois, use nome = expressão para alterar seu valor.

~~~nex
INICIO
    GUARDE pontos = 10
    pontos = pontos + 5
    MOSTRE pontos
FIM
~~~

Atribuir a uma variável ainda não declarada ou declarar o mesmo nome duas vezes gera erro.

## Booleanos e comparações

Valores: VERDADEIRO e FALSO.

Comparadores numéricos: >, >=, <, <=, ==, !=.

## Operadores lógicos

- E: verdadeiro quando os dois lados são verdadeiros.
- OU: verdadeiro quando pelo menos um lado é verdadeiro.
- NAO: inverte um booleano.

Os operadores E e OU usam curto-circuito.

## Condicionais

Todo bloco condicional termina com FIMSE. SENAO é opcional.

~~~nex
INICIO
    GUARDE idade = 20
    SE idade >= 18 E VERDADEIRO ENTAO
        MOSTRE "Maior de idade"
    SENAO
        MOSTRE "Menor de idade"
    FIMSE
FIM
~~~

## Precedência

Da maior para a menor: parênteses, operadores unários, multiplicação/divisão, adição/subtração, comparações, igualdade, E, OU.

## Limitações atuais

Os comparadores relacionais aceitam números. Igualdade compara números por valor e outros tipos por igualdade. Ainda não existem laços, funções, escopos locais, listas, mapas, entrada do usuário ou comentários.

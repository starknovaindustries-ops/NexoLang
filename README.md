# NexoLang

NexoLang é uma linguagem de programação de propósito geral em desenvolvimento, criada para aplicações, sistemas e programas.

> Status: Runtime 0.2 em desenvolvimento. Lexer, AST, Parser e Interpreter possuem suporte inicial a condicionais, lógica e reatribuição.

## Exemplo

~~~nex
INICIO
    GUARDE idade = 17
    SE idade >= 18 ENTAO
        MOSTRE "Acesso liberado"
    SENAO
        MOSTRE "Acesso negado"
    FIMSE
    idade = idade + 1
    MOSTRE idade
FIM
~~~

## Arquitetura

1. Lexer — converte o texto em tokens.
2. Parser — valida a sintaxe e cria a AST.
3. AST — representa expressões e comandos.
4. Interpreter — executa os programas.
5. Ambiente — armazena as variáveis.

## Recursos da versão 0.2

- Declaração GUARDE e reatribuição nome = expressão.
- Saída com MOSTRE.
- Booleanos VERDADEIRO e FALSO.
- Condicionais SE ... ENTAO ... SENAO ... FIMSE.
- Comparações >, >=, <, <=, ==, !=.
- Operadores lógicos E, OU, NAO, com curto-circuito.
- Operações matemáticas e concatenação de textos.
- Erros léxicos, sintáticos e de execução com localização.

## Executar

Requisitos: JDK 17 e Gradle.

~~~bash
gradle run --args="examples/runtime-02.nex"
gradle test
~~~

O GitHub Actions executa os testes e o exemplo Runtime 0.2 em cada push para main e em pull requests.

## Integração Android / NexoOS

O núcleo do Lexer, Parser e Interpreter é Kotlin/JVM e foi organizado sem dependência de APIs exclusivas do Android. A integração Android ainda precisa ser feita e validada dentro do projeto NexoOS-App.

## Próximas etapas

Consulte docs/ROADMAP.md.

## Licença

A licença ainda será definida. Até lá, todos os direitos permanecem reservados.

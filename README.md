# NexoLang

**NexoLang** é uma linguagem de programação de propósito geral em desenvolvimento, criada para aplicações, sistemas e programas.

> Status: versão 0.1 em construção. Lexer, AST, Parser e Interpreter iniciais implementados.

## Objetivos

- Sintaxe própria em português.
- Interpretador próprio.
- Implementação inicial do interpretador em Kotlin/JVM.
- Arquivos de código com extensão `.nex`.
- Evolução para aplicações, ferramentas e integração com o NexoOS.

## Exemplo

```nex
INICIO
    GUARDE nome = "NexoLang"
    GUARDE total = (10 + 5) * 2
    MOSTRE "Olá, " + nome
    MOSTRE total
FIM
```

## Arquitetura

1. **Lexer** — transforma o texto em tokens.
2. **Parser** — valida a estrutura e a precedência das expressões.
3. **AST** — representa expressões e comandos em uma árvore.
4. **Interpreter** — executa comandos e avalia expressões.
5. **Ambiente** — armazena variáveis durante a execução.

## Recursos implementados

- Comandos `MOSTRE` e `GUARDE`.
- Variáveis durante a execução do programa.
- Números inteiros e decimais (representados internamente como Double).
- Operações `+`, `-`, `*`, `/`, sinais unários e parênteses.
- Concatenação de dois textos com `+`.
- Erros para variável não declarada, declaração duplicada, tipo inválido e divisão por zero.
- Mensagens de erro léxico, sintático e de execução com linha e coluna.

## Executar um arquivo

Requisitos: JDK 17 e Gradle instalado.

```bash
gradle run --args="examples/hello.nex"
```

O comando executa o programa NexoLang.

## Ainda não implementado

Condições, laços, funções, reatribuição, coleções, módulos, entrada do usuário, comentários e biblioteca padrão.

## Licença

A licença ainda será definida. Até lá, todos os direitos permanecem reservados; não presuma que o projeto esteja sob MIT.

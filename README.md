# NexoLang

**NexoLang** é uma linguagem de programação de propósito geral em desenvolvimento, criada para aplicações, sistemas e programas.

> Status: versão 0.1 em construção. Lexer, AST e Parser iniciais implementados; execução de programas ainda não implementada.

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
    MOSTRE nome
    MOSTRE total
FIM
```

## Arquitetura

1. **Lexer** — transforma o texto em tokens.
2. **Parser** — valida a estrutura e a precedência das expressões.
3. **AST** — representa expressões e comandos em uma árvore.
4. **Interpreter** — próxima etapa: executar os comandos.
5. **Runtime** — ambiente de execução e valores.

## Implementação atual

- Tokens para palavras-chave, identificadores, números, textos, operadores e parênteses.
- Comandos `MOSTRE` e `GUARDE`.
- Expressões com `+`, `-`, `*`, `/`, sinais unários e parênteses.
- Mensagens de erro léxico e sintático com linha e coluna.
- O programa principal analisa um arquivo e imprime a AST; ainda não executa o código NexoLang.

## Executar

Requisitos: JDK 17 e Gradle instalado.

```bash
gradle run --args="examples/hello.nex"
```

## Roadmap

Consulte [docs/ROADMAP.md](docs/ROADMAP.md).

## Licença

A licença ainda será definida. Até lá, todos os direitos permanecem reservados; não presuma que o projeto esteja sob MIT.

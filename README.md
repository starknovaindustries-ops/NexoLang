# NexoLang

**NexoLang** é uma linguagem de programação de propósito geral em desenvolvimento, criada para aplicações, sistemas e programas.

> Status: planejamento e especificação inicial — versão 0.1 em construção.

## Objetivos

- Sintaxe própria em português.
- Interpretador próprio.
- Implementação inicial do interpretador em Kotlin/JVM.
- Arquivos de código com extensão `.nex`.
- Evolução para aplicações, ferramentas e integração com o NexoOS.

## Primeiro exemplo

```nex
INICIO
    MOSTRE "Olá, NexoOS!"
    GUARDE nome = "NexoLang"
    MOSTRE nome
FIM
```

## Arquitetura planejada

1. **Lexer** — transforma o texto em tokens.
2. **Parser** — verifica a sintaxe e constrói a estrutura do programa.
3. **AST** — representa o código em uma árvore sintática.
4. **Interpreter** — executa os comandos.
5. **Runtime** — fornece o ambiente de execução e os valores.

## Estrutura do projeto

```text
NexoLang/
├── docs/
│   ├── ROADMAP.md
│   └── SYNTAX.md
├── examples/
│   └── hello.nex
├── .gitignore
└── README.md
```

## Roadmap

Consulte [docs/ROADMAP.md](docs/ROADMAP.md).

## Licença

A licença ainda será definida. Até lá, todos os direitos permanecem reservados; não presuma que o projeto esteja sob MIT.

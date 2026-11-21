package nexolang

import java.io.File

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("NexoLang 0.1.0 — analisador léxico e sintático")
        println("Uso: gradle run --args=\"caminho/arquivo.nex\"")
        return
    }

    val file = File(args[0])
    if (!file.isFile) {
        System.err.println("Arquivo não encontrado: ${file.path}")
        return
    }

    try {
        val tokens = Lexer(file.readText()).scanTokens()
        val program = Parser(tokens).parse()
        println("Análise sintática concluída.")
        println("AST:")
        println(program)
    } catch (error: LexicalException) {
        System.err.println(error.message)
    } catch (error: ParseException) {
        System.err.println(error.message)
    }
}

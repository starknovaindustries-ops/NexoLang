package nexolang

import java.io.File

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("NexoLang 0.1.0 — analisador léxico inicial")
        println("Uso: ./gradlew run --args=\"caminho/arquivo.nex\"")
        return
    }

    val file = File(args[0])
    if (!file.isFile) {
        System.err.println("Arquivo não encontrado: ${file.path}")
        return
    }

    try {
        val source = file.readText()
        val tokens = Lexer(source).scanTokens()
        tokens.forEach(::println)
    } catch (error: LexicalException) {
        System.err.println(error.message)
    }
}

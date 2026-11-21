package nexolang

enum class TokenType {
    // Estrutura
    INICIO,
    FIM,
    NEWLINE,
    EOF,

    // Comandos
    MOSTRE,
    GUARDE,

    // Valores e identificadores
    IDENTIFIER,
    NUMBER,
    STRING,

    // Operadores e pontuação
    PLUS,
    MINUS,
    STAR,
    SLASH,
    EQUAL,
    LEFT_PAREN,
    RIGHT_PAREN
}

data class Token(
    val type: TokenType,
    val lexeme: String,
    val line: Int,
    val column: Int
) {
    override fun toString(): String =
        "$type('$lexeme') at $line:$column"
}

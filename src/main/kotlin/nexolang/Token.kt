package nexolang

enum class TokenType {
    INICIO, FIM, NEWLINE, EOF,
    MOSTRE, GUARDE, SE, ENTAO, SENAO, FIMSE,
    E, OU, NAO, VERDADEIRO, FALSO,
    IDENTIFIER, NUMBER, STRING,
    PLUS, MINUS, STAR, SLASH,
    EQUAL, EQUAL_EQUAL, BANG_EQUAL,
    GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,
    LEFT_PAREN, RIGHT_PAREN
}

data class Token(
    val type: TokenType,
    val lexeme: String,
    val line: Int,
    val column: Int
) {
    override fun toString(): String = "$type('$lexeme') at $line:$column"
}

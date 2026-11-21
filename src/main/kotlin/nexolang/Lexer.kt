package nexolang

class LexicalException(
    message: String,
    val lineNumber: Int,
    val columnNumber: Int
) : RuntimeException("Erro léxico em $lineNumber:$columnNumber: $message")

/**
 * Converte o código-fonte NexoLang em uma sequência de tokens.
 *
 * Nesta primeira versão, cada comando deve ocupar sua própria linha.
 */
class Lexer(private val source: String) {
    private val tokens = mutableListOf<Token>()
    private var start = 0
    private var current = 0
    private var line = 1
    private var column = 1
    private var startLine = 1
    private var startColumn = 1

    private val keywords = mapOf(
        "INICIO" to TokenType.INICIO,
        "FIM" to TokenType.FIM,
        "MOSTRE" to TokenType.MOSTRE,
        "GUARDE" to TokenType.GUARDE
    )

    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            start = current
            startLine = line
            startColumn = column
            scanToken()
        }

        tokens += Token(TokenType.EOF, "", line, column)
        return tokens.toList()
    }

    private fun scanToken() {
        when (val char = advance()) {
            ' ', '\t' -> Unit
            '\r' -> {
                if (peek() == '\n') advance()
                addToken(TokenType.NEWLINE)
                line++
                column = 1
            }
            '\n' -> {
                addToken(TokenType.NEWLINE)
                line++
                column = 1
            }
            '+' -> addToken(TokenType.PLUS)
            '-' -> addToken(TokenType.MINUS)
            '*' -> addToken(TokenType.STAR)
            '/' -> addToken(TokenType.SLASH)
            '=' -> addToken(TokenType.EQUAL)
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '"' -> scanString()
            else -> when {
                char.isDigit() -> scanNumber()
                char.isLetter() || char == '_' -> scanIdentifier()
                else -> throw error("Caractere inesperado '$char'")
            }
        }
    }

    private fun scanString() {
        while (!isAtEnd() && peek() != '"') {
            if (peek() == '\n' || peek() == '\r') {
                throw error("Texto não pode atravessar linhas nesta versão")
            }
            advance()
        }

        if (isAtEnd()) throw error("Texto sem aspas de fechamento")

        advance() // aspas finais
        val value = source.substring(start + 1, current - 1)
        tokens += Token(TokenType.STRING, value, startLine, startColumn)
    }

    private fun scanNumber() {
        while (peek().isDigit()) advance()

        if (peek() == '.' && peekNext().isDigit()) {
            advance()
            while (peek().isDigit()) advance()
        }

        addToken(TokenType.NUMBER)
    }

    private fun scanIdentifier() {
        while (peek().isLetterOrDigit() || peek() == '_') advance()

        val text = source.substring(start, current)
        val type = keywords[text] ?: TokenType.IDENTIFIER
        tokens += Token(type, text, startLine, startColumn)
    }

    private fun advance(): Char {
        val char = source[current++]
        column++
        return char
    }

    private fun addToken(type: TokenType) {
        tokens += Token(type, source.substring(start, current), startLine, startColumn)
    }

    private fun peek(): Char =
        if (isAtEnd()) '\u0000' else source[current]

    private fun peekNext(): Char =
        if (current + 1 >= source.length) '\u0000' else source[current + 1]

    private fun isAtEnd(): Boolean = current >= source.length

    private fun error(message: String) =
        LexicalException(message, startLine, startColumn)
}

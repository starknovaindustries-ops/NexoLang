package nexolang

class ParseException(
    message: String,
    val lineNumber: Int,
    val columnNumber: Int
) : RuntimeException("Erro sintático em $lineNumber:$columnNumber: $message")

/** Analisa tokens e constrói a AST da versão inicial da NexoLang. */
class Parser(private val tokens: List<Token>) {
    private var current = 0

    fun parse(): Program {
        skipNewlines()
        consume(TokenType.INICIO, "O programa deve começar com INICIO.")
        skipNewlines()
        val statements = mutableListOf<Stmt>()

        while (!check(TokenType.FIM) && !check(TokenType.EOF)) {
            statements += declaration()
            if (!check(TokenType.FIM) && !check(TokenType.EOF)) {
                consume(TokenType.NEWLINE, "Esperada uma quebra de linha após o comando.")
                skipNewlines()
            }
        }

        consume(TokenType.FIM, "Esperado FIM para encerrar o programa.")
        skipNewlines()
        consume(TokenType.EOF, "Não pode haver conteúdo após FIM.")
        return Program(statements)
    }

    private fun declaration(): Stmt {
        if (match(TokenType.GUARDE)) {
            val name = consume(TokenType.IDENTIFIER, "Esperado o nome da variável.")
            consume(TokenType.EQUAL, "Esperado '=' após o nome da variável.")
            return Stmt.Declare(name, expression())
        }
        if (match(TokenType.MOSTRE)) return Stmt.Show(expression())
        throw error(peek(), "Comando não reconhecido. Use MOSTRE ou GUARDE.")
    }

    private fun expression(): Expr = addition()

    private fun addition(): Expr {
        var expr = multiplication()
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            val operator = previous()
            expr = Expr.Binary(expr, operator, multiplication())
        }
        return expr
    }

    private fun multiplication(): Expr {
        var expr = unary()
        while (match(TokenType.STAR, TokenType.SLASH)) {
            val operator = previous()
            expr = Expr.Binary(expr, operator, unary())
        }
        return expr
    }

    private fun unary(): Expr {
        if (match(TokenType.MINUS, TokenType.PLUS)) {
            val operator = previous()
            return Expr.Unary(operator, unary())
        }
        return primary()
    }

    private fun primary(): Expr {
        if (match(TokenType.NUMBER)) {
            val token = previous()
            val value = token.lexeme.toDoubleOrNull()
                ?: throw error(token, "Número inválido.")
            return Expr.Literal(value)
        }
        if (match(TokenType.STRING)) return Expr.Literal(previous().lexeme)
        if (match(TokenType.IDENTIFIER)) return Expr.Variable(previous())
        if (match(TokenType.LEFT_PAREN)) {
            val expr = expression()
            consume(TokenType.RIGHT_PAREN, "Esperado ')' após a expressão.")
            return Expr.Grouping(expr)
        }
        throw error(peek(), "Esperada uma expressão.")
    }

    private fun skipNewlines() {
        while (match(TokenType.NEWLINE)) Unit
    }

    private fun consume(type: TokenType, message: String): Token {
        if (check(type)) return advance()
        throw error(peek(), message)
    }

    private fun match(vararg types: TokenType): Boolean {
        if (types.any(::check)) {
            advance()
            return true
        }
        return false
    }

    private fun check(type: TokenType): Boolean =
        if (isAtEnd()) type == TokenType.EOF else peek().type == type

    private fun advance(): Token {
        if (!isAtEnd()) current++
        return previous()
    }

    private fun isAtEnd(): Boolean = peek().type == TokenType.EOF
    private fun peek(): Token = tokens[current]
    private fun previous(): Token = tokens[current - 1]

    private fun error(token: Token, message: String) =
        ParseException(message, token.line, token.column)
}

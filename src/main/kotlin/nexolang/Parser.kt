package nexolang

class ParseException(
    message: String,
    val lineNumber: Int,
    val columnNumber: Int
) : RuntimeException("Erro sintático em $lineNumber:$columnNumber: $message")

/** Parser da sintaxe NexoLang 0.2. */
class Parser(private val tokens: List<Token>) {
    private var current = 0

    fun parse(): Program {
        skipNewlines()
        consume(TokenType.INICIO, "O programa deve começar com INICIO.")
        skipNewlines()
        val statements = parseStatements(setOf(TokenType.FIM, TokenType.EOF))
        consume(TokenType.FIM, "Esperado FIM para encerrar o programa.")
        skipNewlines()
        consume(TokenType.EOF, "Não pode haver conteúdo após FIM.")
        return Program(statements)
    }

    private fun parseStatements(stops: Set<TokenType>): List<Stmt> {
        val statements = mutableListOf<Stmt>()
        while (!stops.any(::check) && !check(TokenType.EOF)) {
            statements += statement()
            if (!stops.any(::check) && !check(TokenType.EOF)) {
                consume(TokenType.NEWLINE, "Esperada uma quebra de linha após o comando.")
                skipNewlines()
            }
        }
        return statements
    }

    private fun statement(): Stmt {
        if (match(TokenType.GUARDE)) {
            val name = consume(TokenType.IDENTIFIER, "Esperado o nome da variável.")
            consume(TokenType.EQUAL, "Esperado '=' após o nome da variável.")
            return Stmt.Declare(name, expression())
        }
        if (match(TokenType.MOSTRE)) return Stmt.Show(expression())
        if (match(TokenType.SE)) return ifStatement()
        if (check(TokenType.IDENTIFIER) && checkNext(TokenType.EQUAL)) {
            val name = advance()
            advance()
            return Stmt.Assign(name, expression())
        }
        throw error(peek(), "Comando não reconhecido. Use GUARDE, MOSTRE, SE ou atribuição.")
    }

    private fun ifStatement(): Stmt {
        val condition = expression()
        consume(TokenType.ENTAO, "Esperado ENTAO após a condição.")
        consume(TokenType.NEWLINE, "Esperada quebra de linha após ENTAO.")
        skipNewlines()

        val thenBranch = parseStatements(setOf(TokenType.SENAO, TokenType.FIMSE))
        val elseBranch = if (match(TokenType.SENAO)) {
            consume(TokenType.NEWLINE, "Esperada quebra de linha após SENAO.")
            skipNewlines()
            parseStatements(setOf(TokenType.FIMSE))
        } else emptyList()

        consume(TokenType.FIMSE, "Esperado FIMSE para encerrar o bloco SE.")
        return Stmt.If(condition, thenBranch, elseBranch)
    }

    private fun expression(): Expr = logicalOr()

    private fun logicalOr(): Expr {
        var expr = logicalAnd()
        while (match(TokenType.OU)) {
            val operator = previous()
            expr = Expr.Binary(expr, operator, logicalAnd())
        }
        return expr
    }

    private fun logicalAnd(): Expr {
        var expr = equality()
        while (match(TokenType.E)) {
            val operator = previous()
            expr = Expr.Binary(expr, operator, equality())
        }
        return expr
    }

    private fun equality(): Expr {
        var expr = comparison()
        while (match(TokenType.EQUAL_EQUAL, TokenType.BANG_EQUAL)) {
            val operator = previous()
            expr = Expr.Binary(expr, operator, comparison())
        }
        return expr
    }

    private fun comparison(): Expr {
        var expr = addition()
        while (match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL)) {
            val operator = previous()
            expr = Expr.Binary(expr, operator, addition())
        }
        return expr
    }

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
        if (match(TokenType.MINUS, TokenType.PLUS, TokenType.NAO)) {
            val operator = previous()
            return Expr.Unary(operator, unary())
        }
        return primary()
    }

    private fun primary(): Expr {
        if (match(TokenType.NUMBER)) {
            val token = previous()
            val value = token.lexeme.toDoubleOrNull() ?: throw error(token, "Número inválido.")
            return Expr.Literal(value)
        }
        if (match(TokenType.STRING)) return Expr.Literal(previous().lexeme)
        if (match(TokenType.VERDADEIRO)) return Expr.Literal(true)
        if (match(TokenType.FALSO)) return Expr.Literal(false)
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

    private fun checkNext(type: TokenType): Boolean =
        current + 1 < tokens.size && tokens[current + 1].type == type

    private fun advance(): Token {
        if (!isAtEnd()) current++
        return previous()
    }

    private fun isAtEnd(): Boolean = peek().type == TokenType.EOF
    private fun peek(): Token = tokens[current]
    private fun previous(): Token = tokens[current - 1]
    private fun error(token: Token, message: String) = ParseException(message, token.line, token.column)
}

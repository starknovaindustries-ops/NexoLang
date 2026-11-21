package nexolang

class RuntimeExceptionNexo(
    message: String,
    val lineNumber: Int,
    val columnNumber: Int
) : RuntimeException("Erro de execução em $lineNumber:$columnNumber: $message")

/** Executa programas NexoLang 0.2. */
class Interpreter(
    private val output: (String) -> Unit = ::println
) {
    private val environment = linkedMapOf<String, Any>()

    fun execute(program: Program) {
        environment.clear()
        executeStatements(program.statements)
    }

    private fun executeStatements(statements: List<Stmt>) {
        for (statement in statements) executeStatement(statement)
    }

    private fun executeStatement(statement: Stmt) {
        when (statement) {
            is Stmt.Declare -> {
                val name = statement.name.lexeme
                if (environment.containsKey(name)) {
                    throw runtimeError(statement.name, "A variável '$name' já foi declarada.")
                }
                environment[name] = evaluate(statement.initializer)
            }
            is Stmt.Assign -> {
                val name = statement.name.lexeme
                if (!environment.containsKey(name)) {
                    throw runtimeError(statement.name, "A variável '$name' não foi declarada.")
                }
                environment[name] = evaluate(statement.value)
            }
            is Stmt.Show -> output(formatValue(evaluate(statement.expression)))
            is Stmt.If -> {
                val condition = requireBoolean(evaluate(statement.condition), tokenOf(statement.condition))
                if (condition) executeStatements(statement.thenBranch)
                else executeStatements(statement.elseBranch)
            }
        }
    }

    private fun evaluate(expression: Expr): Any =
        when (expression) {
            is Expr.Literal -> expression.value
            is Expr.Variable -> environment[expression.name.lexeme]
                ?: throw runtimeError(expression.name, "A variável '${expression.name.lexeme}' não foi declarada.")
            is Expr.Grouping -> evaluate(expression.expression)
            is Expr.Unary -> {
                val value = evaluate(expression.right)
                when (expression.operator.type) {
                    TokenType.MINUS -> -requireNumber(value, expression.operator)
                    TokenType.PLUS -> requireNumber(value, expression.operator)
                    TokenType.NAO -> !requireBoolean(value, expression.operator)
                    else -> throw runtimeError(expression.operator, "Operador unário inválido.")
                }
            }
            is Expr.Binary -> evaluateBinary(expression)
        }

    private fun evaluateBinary(expression: Expr.Binary): Any {
        val operator = expression.operator
        val left = evaluate(expression.left)

        // Operadores lógicos usam curto-circuito.
        if (operator.type == TokenType.OU) {
            val leftBool = requireBoolean(left, operator)
            return leftBool || requireBoolean(evaluate(expression.right), operator)
        }
        if (operator.type == TokenType.E) {
            val leftBool = requireBoolean(left, operator)
            return leftBool && requireBoolean(evaluate(expression.right), operator)
        }

        val right = evaluate(expression.right)
        if (operator.type == TokenType.EQUAL_EQUAL) return valuesEqual(left, right)
        if (operator.type == TokenType.BANG_EQUAL) return !valuesEqual(left, right)

        if (operator.type == TokenType.PLUS && left is String && right is String) {
            return left + right
        }

        val a = requireNumber(left, operator)
        val b = requireNumber(right, operator)
        return when (operator.type) {
            TokenType.PLUS -> a + b
            TokenType.MINUS -> a - b
            TokenType.STAR -> a * b
            TokenType.SLASH -> {
                if (b == 0.0) throw runtimeError(operator, "Divisão por zero.")
                a / b
            }
            TokenType.GREATER -> a > b
            TokenType.GREATER_EQUAL -> a >= b
            TokenType.LESS -> a < b
            TokenType.LESS_EQUAL -> a <= b
            else -> throw runtimeError(operator, "Operador binário inválido.")
        }
    }

    private fun valuesEqual(left: Any, right: Any): Boolean {
        if (left is Number && right is Number) return left.toDouble() == right.toDouble()
        return left == right
    }

    private fun requireNumber(value: Any, token: Token): Double =
        (value as? Number)?.toDouble()
            ?: throw runtimeError(token, "Operação matemática exige valores numéricos.")

    private fun requireBoolean(value: Any, token: Token): Boolean =
        value as? Boolean
            ?: throw runtimeError(token, "A condição exige VERDADEIRO ou FALSO.")

    private fun tokenOf(expression: Expr): Token = when (expression) {
        is Expr.Variable -> expression.name
        is Expr.Unary -> expression.operator
        is Expr.Binary -> expression.operator
        is Expr.Literal -> Token(TokenType.VERDADEIRO, expression.value.toString(), 1, 1)
        is Expr.Grouping -> tokenOf(expression.expression)
    }

    private fun formatValue(value: Any): String = when (value) {
        is Double -> if (value.isFinite() && value % 1.0 == 0.0) value.toLong().toString() else value.toString()
        is Boolean -> if (value) "VERDADEIRO" else "FALSO"
        else -> value.toString()
    }

    private fun runtimeError(token: Token, message: String) =
        RuntimeExceptionNexo(message, token.line, token.column)
}

package nexolang

class RuntimeExceptionNexo(
    message: String,
    val lineNumber: Int,
    val columnNumber: Int
) : RuntimeException("Erro de execução em $lineNumber:$columnNumber: $message")

/** Executa a AST: declarações, saída, variáveis e operações aritméticas. */
class Interpreter(
    private val output: (String) -> Unit = ::println
) {
    private val environment = linkedMapOf<String, Any>()

    fun execute(program: Program) {
        environment.clear()
        for (statement in program.statements) executeStatement(statement)
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
            is Stmt.Show -> output(formatValue(evaluate(statement.expression)))
        }
    }

    private fun evaluate(expression: Expr): Any =
        when (expression) {
            is Expr.Literal -> expression.value
            is Expr.Variable -> environment[expression.name.lexeme]
                ?: throw runtimeError(expression.name, "A variável '${expression.name.lexeme}' não foi declarada.")
            is Expr.Grouping -> evaluate(expression.expression)
            is Expr.Unary -> {
                val number = requireNumber(evaluate(expression.right), expression.operator)
                when (expression.operator.type) {
                    TokenType.MINUS -> -number
                    TokenType.PLUS -> number
                    else -> throw runtimeError(expression.operator, "Operador unário inválido.")
                }
            }
            is Expr.Binary -> evaluateBinary(expression)
        }

    private fun evaluateBinary(expression: Expr.Binary): Any {
        val left = evaluate(expression.left)
        val right = evaluate(expression.right)

        if (expression.operator.type == TokenType.PLUS && left is String && right is String) {
            return left + right
        }

        val a = requireNumber(left, expression.operator)
        val b = requireNumber(right, expression.operator)
        return when (expression.operator.type) {
            TokenType.PLUS -> a + b
            TokenType.MINUS -> a - b
            TokenType.STAR -> a * b
            TokenType.SLASH -> {
                if (b == 0.0) throw runtimeError(expression.operator, "Divisão por zero.")
                a / b
            }
            else -> throw runtimeError(expression.operator, "Operador binário inválido.")
        }
    }

    private fun requireNumber(value: Any, token: Token): Double =
        (value as? Number)?.toDouble()
            ?: throw runtimeError(token, "Operação matemática exige valores numéricos.")

    private fun formatValue(value: Any): String =
        if (value is Double && value.isFinite() && value % 1.0 == 0.0) {
            value.toLong().toString()
        } else value.toString()

    private fun runtimeError(token: Token, message: String) =
        RuntimeExceptionNexo(message, token.line, token.column)
}

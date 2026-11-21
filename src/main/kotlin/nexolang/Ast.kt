package nexolang

sealed interface Expr {
    data class Literal(val value: Any) : Expr
    data class Variable(val name: Token) : Expr
    data class Unary(val operator: Token, val right: Expr) : Expr
    data class Binary(val left: Expr, val operator: Token, val right: Expr) : Expr
    data class Grouping(val expression: Expr) : Expr
}

sealed interface Stmt {
    data class Show(val expression: Expr) : Stmt
    data class Declare(val name: Token, val initializer: Expr) : Stmt
}

data class Program(val statements: List<Stmt>)

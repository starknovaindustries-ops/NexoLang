package nexolang

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class InterpreterTest {
    private fun runProgram(source: String): List<String> {
        val output = mutableListOf<String>()
        val program = Parser(Lexer(source).scanTokens()).parse()
        Interpreter { output += it }.execute(program)
        return output
    }

    @Test
    fun arithmeticAndVariables() {
        assertEquals(listOf("30"), runProgram("INICIO\nGUARDE total = (10 + 5) * 2\nMOSTRE total\nFIM"))
    }

    @Test
    fun ifElseAndReassignment() {
        val source = """INICIO
GUARDE idade = 17
SE idade >= 18 ENTAO
MOSTRE "maior"
SENAO
MOSTRE "menor"
FIMSE
idade = idade + 1
MOSTRE idade
FIM"""
        assertEquals(listOf("menor", "18"), runProgram(source))
    }

    @Test
    fun booleanOperators() {
        val source = """INICIO
MOSTRE VERDADEIRO E NAO FALSO
MOSTRE FALSO OU (2 > 3)
FIM"""
        assertEquals(listOf("true", "false"), runProgram(source))
    }

    @Test
    fun assignmentToUnknownVariableFails() {
        val error = assertFailsWith<RuntimeExceptionNexo> {
            runProgram("INICIO\nx = 3\nFIM")
        }
        assertEquals(2, error.lineNumber)
    }

    @Test
    fun divisionByZeroFails() {
        assertFailsWith<RuntimeExceptionNexo> {
            runProgram("INICIO\nMOSTRE 10 / 0\nFIM")
        }
    }
}

package main.kotlin.arrayandlist.medium

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 *
 * Example 1:
 * - Input: n = 3
 * - Output: ["((()))","(()())","(())()","()(())","()()()"]
 *
 * Example 2:
 * - Input: n = 1
 * - Output: ["()"]
 *
 * Constraints:
 * - 1 <= n <= 8
 */
abstract class GenerateParentheses {
    abstract fun generateParenthesis(n: Int): List<String>

    @ParameterizedTest(name = "The parentheses generated for {0} should be {1}")
    @MethodSource("getData")
    fun test(n: Int, expectedValue: List<String>) {
        val result = generateParenthesis(n)
        Assertions.assertTrue(expectedValue equalsIgnoreOrder result)
    }

    companion object {
        @JvmStatic
        fun getData(): List<Array<Any>> {
            return listOf(
                arrayOf(3, listOf("((()))","(()())","(())()","()(())","()()()")),
                arrayOf(2, listOf("(())", "()()")),
                arrayOf(1, listOf("()")),
            )
        }
    }

    private infix fun <T> List<T>.equalsIgnoreOrder(other: List<T>) = this.size == other.size && this.toSet() == other.toSet()
}

class GenerateParenthesesImpl: GenerateParentheses() {
    override fun generateParenthesis(n: Int): List<String> {
        val result = mutableListOf<String>()
        generateParenthesis(n, result)
        return result
    }

    private fun generateParenthesis(n: Int,
                                    result: MutableList<String>,
                                    open: Int = 0,
                                    close: Int = 0,
                                    prefix: String = "") {
        if ((open + close) == n * 2) {
            result.add(prefix)
            return
        }

        // Add open parenthesis to them all if possible
        // It is only possible to open parenthesis if the number of opened parenthesis < n
        if (open < n) {
            generateParenthesis(n,result, open + 1, close, "$prefix(")
        }

        // Add close parenthesis to them all if possible
        if (close < open) {
            generateParenthesis(n, result, open, close + 1, "$prefix)")
        }
    }
}
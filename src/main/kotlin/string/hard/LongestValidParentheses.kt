package main.kotlin.string.hard

import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

/**
 * Given a string containing just the characters '(' and ')', return the length of the longest valid (well-formed)
 * parentheses substring.
 * Example 1:
 * - Input: s = "(()"
 * - Output: 2
 * Explanation: The longest valid parentheses substring is "()".
 *
 * Example 2:
 * - Input: s = ")()())"
 * - Output: 4
 * - Explanation: The longest valid parentheses substring is "()()".
 *
 * Example 3:
 * - Input: s = ""
 * - Output: 0
 *
 * Constraints:
 * - 0 <= s.length <= 3 * 10^4
 * - s[i] is '(', or ')'.
 */
abstract class LongestValidParentheses {

    abstract fun longestValidParentheses(s: String): Int

    @ParameterizedTest(name = "The longest valid parenthesis of {0} is {1}")
    @ArgumentsSource(TestDataArgumentProvider::class)
    fun test(input: String, expectedValue: Int) {
        val result = longestValidParentheses(input)
        Assertions.assertEquals(expectedValue, result)
    }

    class TestDataArgumentProvider: ArgumentsProvider{
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of("(()", 2),
                Arguments.of(")()())", 4),
                Arguments.of("", 0),
                Arguments.of("())(())", 4),
            )
        }
    }
}

/**
 * Two pass solution
 * Source: https://www.youtube.com/watch?v=vURq_xYGr-k
 * 1. Go to from left to right
 * 1.1 Count the number of open and close parenthesis. Once they reach to balance, then count the parenthesis
 * -> Stop condition: When there is more right parenthesis than left parenthesis. Not open or close parenthesis
 * can solve this situation -> Restart the counting
 * 2. Go to from right to left
 * Same, but the stop condition is when there is more left parenthesis than right parenthesis
 */
class LongestValidParenthesisImpl: LongestValidParentheses() {
    override fun longestValidParentheses(s: String): Int {
        // Left to right
        var left = 0
        var right = 0
        var max = 0

        for (i in s.indices) {
            if (s[i] == '(') {
                left++
            } else {
                right++
            }

            if (left == right) {
                if (left * 2 > max) {
                    max = left * 2
                }
            } else if (right > left) {
                left = 0
                right = 0
            }
        }

        // Right to left
        left = 0
        right = 0
        for (i in s.indices.reversed()) {
            if (s[i] == '(') {
                left++
            } else {
                right++
            }

            if (left == right) {
                if (left * 2 > max) {
                    max = left * 2
                }
            } else if (left > right) {
                left = 0
                right = 0
            }
        }

        return max
    }
}
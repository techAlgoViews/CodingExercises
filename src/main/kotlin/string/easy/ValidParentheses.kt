package main.kotlin.string.easy

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Valid parentheses
 *
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input
 * string is valid.
 *
 * An input string is valid if:
 *  - Open brackets must be closed by the same type of brackets.
 *  - Open brackets must be closed in the correct order.
 *  - Every close bracket has a corresponding open bracket of the same type.
 * Example 1:
 *      Input: s = "()"
 *      Output: true
 *
 * Example 2:
 *      Input: s = "()[]{}"
 *      Output: true
 *
 * Example 3:
 *      Input: s = "(]"
 *      Output: false
 * Constraints:
 *  1 <= s.length <= 104
 *  s consists of parentheses only '()[]{}'.
 */
abstract class ValidParentheses {
    abstract fun isValid(s: String): Boolean

    @Test
    fun test1() {
        // Given
        val input = "()"

        // When
        val result = isValid(input)

        // Then
        assertTrue(result)
    }

    @Test
    fun test2() {
        // Given
        val input = "()[]{}"

        // When
        val result = isValid(input)

        // Then
        assertTrue(result)
    }

    @Test
    fun test3() {
        // Given
        val input = "(]"

        // When
        val result = isValid(input)

        // Then
        assertFalse(result)
    }
}

class ValidParenthesesImpl: ValidParentheses() {

    /**
     * Initial through
     * 1. Create a stack
     * 2. Loop through the String
     * - Per each one of the left side parentheses
     *   - Add it to the stack
     * - Per each one of the right side parentheses
     *   - Extract the item from the stack
     *   - Check if it is valid
     *      -> If so, continue
     *      -> If not, return false
     * 3. Return if stack is empty
     * We cannot use a hashmap because the order need to be perserved
     *
     * Corner case:
     * - Empty string -> Return true
     * - Only right side -> Return false
     */
    // ()   -> Valid
    // ((   -> Not valid
    // ()[] -> Valid
    // ([)] -> Not valid

    // Example:
    // ( { } )
    // ^       -> stack: (
    //   ^     -> stack: ({
    //     ^   -> stack: (
    //       ^ -> stack:
    override fun isValid(s: String): Boolean {
        val myStack = ArrayDeque<Char>()
        s.forEach { item ->
            when (item) {
                '(', '[', '{' -> {
                    myStack.addFirst(item)
                }
                ')', ']', '}' -> {
                    val top = myStack.removeFirstOrNull()
                    if (!areCompliment(top, item)) {
                        return false
                    }
                }
            }
        }

        return myStack.isEmpty()
    }

    private fun areCompliment(c1: Char?, c2: Char): Boolean {
        when (c1) {
            '(' -> return c2 == ')'
            '[' -> return c2 == ']'
            '{' -> return c2 == '}'
        }
        return false
    }
}
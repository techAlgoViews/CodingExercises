package main.kotlin.string.medium

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations
 * that the number could represent. Return the answer in any order.
 * A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not
 * map to any letters.
 *
 * Example 1:
 *  Input: digits = "23"
 *  Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * Example 2:
 *  Input: digits = ""
 *  Output: []
 *
 * Example 3:
 *  Input: digits = "2"
 *  Output: ["a","b","c"]
 *
 * Constraints:
 *  0 <= digits.length <= 4
 *  digits[i] is a digit in the range ['2', '9'].
 */
abstract class LetterCombinationsOfAPhoneNumber {

    abstract fun letterCombinations(digits: String): List<String>

    @ParameterizedTest(name = "The letter combination of {0} should be {1}")
    @MethodSource("getData")
    fun test(input: String, expectedValue: List<String>) {
        val result = letterCombinations(input)
        assertTrue(expectedValue.containsAll(result))
        assertTrue(result.containsAll(expectedValue))
    }

    companion object {
        @JvmStatic
        fun getData(): List<Array<Any>> {
            return listOf(
                arrayOf("23", listOf("ad","ae","af","bd","be","bf","cd","ce","cf")),
                arrayOf("", emptyList<String>()),
                arrayOf("2", listOf("a", "b", "c")),
            )
        }
    }
}

class LetterCombinationsOfAPhoneNumberImp: LetterCombinationsOfAPhoneNumber() {
    private fun getLetters(symbol: Char) = when(symbol) {
        '2' -> "abc"
        '3' -> "def"
        '4' -> "ghi"
        '5' -> "jkl"
        '6' -> "mno"
        '7' -> "pqrs"
        '8' -> "tuv"
        '9' -> "wxyz"
        else -> ""
    }

    /**
     * Optimization:
     * - Use a map structure instead of map function
     *  - Instead of using a list of strings, returns one single string
     * - Use String builder instead of appending strings one by one
     */
    override fun letterCombinations(digits: String): List<String> {
        val result = mutableListOf<String>()
        if (digits.isEmpty()) return result

        fillLetterCombinations(digits, result)
        return result
    }

    private fun fillLetterCombinations(
        digits: String,
        result: MutableList<String>,
        index: Int = 0,
        prefix: String = ""
    ) {
        if (index == digits.length) {
            result.add(prefix)
            return
        }

        for (letter in getLetters(digits[index])) {
            fillLetterCombinations(digits, result, index + 1, prefix + letter)
        }
    }
}
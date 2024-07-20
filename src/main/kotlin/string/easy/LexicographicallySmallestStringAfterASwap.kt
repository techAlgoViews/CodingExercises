package main.kotlin.string.easy

import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.StringBuilder

/**
 * Given a string s containing only digits, return the lexicographically smallest string
 * that can be obtained after swapping adjacent digits in s with the same parity at most once.
 * Digits have the same parity if both are odd or both are even. For example, 5 and 9, as well as 2 and 4,
 * have the same parity, while 6 and 9 do not.
 *
 * Example 1:
 *   Input: s = "45320"
 *   Output: "43520"
 *   Explanation: s[1] == '5' and s[2] == '3' both have the same parity, and swapping them results in the
 *   lexicographically smallest string.
 *
 * Example 2:
 *   Input: s = "001"
 *   Output: "001"
 *   Explanation: There is no need to perform a swap because s is already the lexicographically smallest.
 * Constraints:
 *  2 <= s.length <= 100
 *  s consists only of digits.
 */
abstract class LexicographicallySmallestStringAfterASwap {

    abstract fun getSmallestString(s: String): String

    @Test
    fun test1() {
        // Given
        val input = "45320"

        // When
        val result = getSmallestString(input)

        // Then
        assertEquals("43520", result)
    }

    @Test
    fun test2() {
        // Given
        val input = "001"

        // When
        val result = getSmallestString(input)

        // Then
        assertEquals("001", result)
    }
}

class LexicographicallySmallestStringAfterASwapImp: LexicographicallySmallestStringAfterASwap() {

    /**
     * Initial thoughts
     *  43520 is lexicographically smaller than 45320 because the digit representation it is smaller.
     *
     *  The algo can go around all the letters, check if the next letter has the same parity, change it and see
     *  if it is smaller. Or simply check if it is smaller.
     *
     *  This needs to be go from beginning to the end because the first letter is better than the others
     */
    override fun getSmallestString(s: String): String {
        val sb = StringBuilder()
        for (i in 0 until s.length - 1) {
            if (s[i].hasSameParity(s[i+1]) && s[i].isBigger(s[i+1])) {
                sb.append(s[i+1])
                sb.append(s[i])
                sb.append(s.substring(i+2))
                return sb.toString()
            } else {
                sb.append(s[i])
            }
        }
        sb.append(s[s.length-1])
        return sb.toString()
    }

    private fun Char.hasSameParity(anotherChar: Char): Boolean {
        return this.digitToInt() % 2 == anotherChar.digitToInt() % 2
    }

    private fun Char.isBigger(anotherChar: Char): Boolean {
        return this.digitToInt() > anotherChar.digitToInt()
    }
 }

class LexicographicallySmallestStringAfterASwapOptim: LexicographicallySmallestStringAfterASwap() {

    /**
     * Looking at the previous solution, since the algo needs to convert all the char to int anyway
     * it would be optimal if it is only do it once
     */
    override fun getSmallestString(s: String): String {
        val sb = StringBuilder()
        var previousDigit = s[0].digitToInt()
        for (i in 1 until s.length) {
            val currentDigit = s[i].digitToInt()
            if ((previousDigit % 2 == currentDigit % 2) && previousDigit > currentDigit) {
                sb.append(currentDigit)
                sb.append(previousDigit)
                sb.append(s.substring(i+1))
                return sb.toString()
            } else {
                sb.append(previousDigit)
                previousDigit = currentDigit
            }
        }
        sb.append(s[s.length-1])
        return sb.toString()
    }
}
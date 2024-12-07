package main.kotlin.string.medium

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * Longest palindromic substring
 *
 * Given a string s, return the longest palindromic substring in s.
 *
 * Example 1:
 *      Input: s = "babad"
 *      Output: "bab"
 *      Explanation: "aba" is also a valid answer.
 *
 * Example 2:
 *      Input: s = "cbbd"
 *      Output: "bb"
 *
 * Constraints:
 *      1 <= s.length <= 1000
 *      s consist of only digits and English letters.
 */
abstract class LongestPalindromicSubstring {

    abstract fun longestPalindrome(s: String): String

    @ParameterizedTest(name = "The longest palindromic substring of {0} should be {1}")
    @MethodSource("getData")
    fun test(input: String, expectedValue: String) {
        val result = longestPalindrome(input)
        assertEquals(expectedValue, result)
    }

    companion object {
        @JvmStatic
        fun getData(): List<Array<Any>> {
            return listOf(
                arrayOf("babad", "bab"),
                arrayOf("cbbd", "bb"),
                arrayOf("bb", "bb"),
                arrayOf("abbbaddaa", "abbba")
            )
        }
    }
}

class LongestPalindromicSubstringOptim: LongestPalindromicSubstring() {

    /**
     * An optimization over the previous solution is using the property of palindrome directly
     * For "abba", if "bb" is palindrome, then to check if "abba" is palindrome, only the initial
     * "a" and the last "a" needs to be checked.
     *
     * "abbbaddaa": "abbba"
     *
     * String        [ b a b d]
     * Init              ^
     * Left Pointer.   ^
     * Right pointer       ^
     *
     */
    override fun longestPalindrome(s: String): String {
        // 1. Init the values
        var leftPointer = 0
        var rightPointer = 0

        for (i in s.indices) {
            val (leftPointerTmp, rightPointerTmp) = getLongestPalindrome(s, i)
            if ((rightPointerTmp - leftPointerTmp) > (rightPointer - leftPointer)) {
                leftPointer = leftPointerTmp
                rightPointer = rightPointerTmp
            }
        }

        // Check the values. Substring does not include the rightest char
        return s.substring(leftPointer, rightPointer + 1)
    }

    private fun getLongestPalindrome(s: String, initialPosition: Int): Pair<Int, Int> {
        // Calculate for the current char
        val (leftPointerSingle, rightPointerSingle) = getLongestPalindrome(s, initialPosition, initialPosition)
        // Calculate for the current char and the previous char
        val (leftPointerDouble, rightPointerDouble) = if (initialPosition > 0 && s[initialPosition] == s[initialPosition - 1]) {
            getLongestPalindrome(s, initialPosition - 1, initialPosition)
        } else {
            Pair(initialPosition, initialPosition)
        }

        if ((rightPointerDouble - leftPointerDouble) > (rightPointerSingle - leftPointerSingle)) {
            return Pair(leftPointerDouble, rightPointerDouble)
        } else {
            return Pair(leftPointerSingle, rightPointerSingle)
        }
    }

    private fun getLongestPalindrome(s: String, leftPointer: Int, rightPointer: Int): Pair<Int, Int> {
        var nextLeftPointer = leftPointer
        var nextRightPointer = rightPointer

        // While left char exists and right char exists and they are the same
        // confirm and update the next values
        while (nextLeftPointer - 1 >= 0 && nextRightPointer + 1 < s.length &&
            s[nextLeftPointer - 1] == s[nextRightPointer + 1]) {
            nextLeftPointer--
            nextRightPointer++
        }

        return Pair(nextLeftPointer, nextRightPointer)
    }
}
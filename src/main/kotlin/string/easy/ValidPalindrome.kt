package main.kotlin.string.easy

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Valid Palindrome
 *
 * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing
 * all non-alphanumeric characters, it reads the same forward and backward. Alphanumeric characters include
 * letters and numbers.
 * Given a string s, return true if it is a palindrome, or false otherwise.
 *
 * Example 1:
 *      Input: s = "A man, a plan, a canal: Panama"
 *      Output: true
 *      Explanation: "amanaplanacanalpanama" is a palindrome.
 *
 * Example 2:
 *      Input: s = "race a car"
 *      Output: false
 *      Explanation: "raceacar" is not a palindrome.
 *
 * Example 3:
 *      Input: s = " "
 *      Output: true
 *      Explanation: s is an empty string "" after removing non-alphanumeric characters.
 *      Since an empty string reads the same forward and backward, it is a palindrome.
 *
 * Constraints:
 *      1 <= s.length <= 2 * 10^5
 *      s consists only of printable ASCII characters.
 */
abstract class ValidPalindrome {

    abstract fun isPalindrome(s: String): Boolean

    @Test
    fun test1() {
        // Given
        val s = "A man, a plan, a canal: Panama"

        // When
        val result = isPalindrome(s)

        // Then
        assertTrue(result)
    }

    @Test
    fun test2() {
        // Given
        val s = "race a car"

        // When
        val result = isPalindrome(s)

        // Then
        assertFalse(result)
    }

    @Test
    fun test3() {
        // Given
        val s = " "

        // When
        val result = isPalindrome(s)

        // Then
        assertTrue(result)
    }

}
class ValidPalindromeImpl: ValidPalindrome() {

    /**
     * Initial thoughts
     *
     * We can going through the list and strip all the non-alphanumeric chars
     * append them, and then run them again
     *
     * A better way is going through the list once, skip all non-alphanumeric chars
     *
     */
    override fun isPalindrome(s: String): Boolean {
        // Init the data
        var left = 0
        var right = s.lastIndex

        // 2. Loop
        while (left < right) {
            // Advance left
            while (left < right && !s[left].isLetterOrDigit()) {
                left++
            }

            // Advance right
            while (left < right && !s[right].isLetterOrDigit()) {
                right--
            }


            if (s[left].lowercaseChar() != s[right].lowercaseChar()) {
                return false
            }

            left++
            right--
        }

        // 3. Return default value
        return true
    }
}
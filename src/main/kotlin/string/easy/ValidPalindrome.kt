package main.kotlin.string.easy

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

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
        val s = "race car"

        // When
        val result = isPalindrome(s)

        // Then
        assertTrue(result)
    }

    @Test
    fun test4() {
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
     * We can go through the list and strip all the non-alphanumeric chars
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

class ValidPalindromeSimpleSol: ValidPalindrome() {
    override fun isPalindrome(s: String): Boolean {
        // 1. Removing all the symbols
        val sb = StringBuilder()
        
        for (char in s) {
            if (char.isLetterOrDigit()) {
                sb.append(char)
            }
        }

        // 2. return the lower case of all the letter
        val cleanS = sb.toString().lowercase(Locale.ENGLISH)

        // 3. Using two pointers
        var leftPointer = 0
        var rightPointer = cleanS.lastIndex

        while (leftPointer < rightPointer) {
            if (cleanS[leftPointer] != cleanS[rightPointer]) {
                return false
            }

            leftPointer++
            rightPointer--
        }

        return true
    }
}

class ValidPalindromeOptimal: ValidPalindrome() {
    override fun isPalindrome(s: String): Boolean {
        var leftPointer = 0
        var rightPointer = s.lastIndex

        while (leftPointer <= rightPointer) {
            val leftChar = s[leftPointer]
            val rightChar = s[rightPointer]

            if (!leftChar.isLetterOrDigit()) {
                leftPointer++
            } else if (!rightChar.isLetterOrDigit()) {
                rightPointer--
            } else if (leftChar.isLetterOrDigit() && rightChar.isLetterOrDigit()) {
                if (leftChar.lowercaseChar() != rightChar.lowercaseChar()) return false
                leftPointer++
                rightPointer--
            }
        }

        return true
    }
}
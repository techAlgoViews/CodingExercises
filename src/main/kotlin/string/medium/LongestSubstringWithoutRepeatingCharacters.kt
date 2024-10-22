package main.kotlin.string.medium

import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Integer.max

/**
 * Longest substring without repeating characters
 *
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 *      Input: s = "abcabcbb"
 *      Output: 3
 *      Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 *      Input: s = "bbbbb"
 *      Output: 1
 *      Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 *      Input: s = "pwwkew"
 *      Output: 3
 *      Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Constraints:
 *      0 <= s.length <= 5 * 104
 *      s consists of English letters, digits, symbols and spaces.
 */
abstract class LongestSubstringWithoutRepeatingCharacters {

    abstract fun lengthOfLongestSubstring(s: String): Int

    @Test
    fun test1() {
        // Given
        val input = "abcabcbb"

        // When
        val result = lengthOfLongestSubstring(input)

        // Then
        assertEquals(3, result)
    }

    @Test
    fun test2() {
        // Given
        val input = "bbbbb"

        // When
        val result = lengthOfLongestSubstring(input)

        // Then
        assertEquals(1, result)
    }

    @Test
    fun test3() {
        // Given
        val input = "pwwkew"

        // When
        val result = lengthOfLongestSubstring(input)

        // Then
        assertEquals(3, result)
    }

    @Test
    fun test4() {
        // Given
        val input = " "

        // When
        val result = lengthOfLongestSubstring(input)

        // Then
        assertEquals(1, result)
    }

    @Test
    fun test5() {
        // Given
        val input = "   "

        // When
        val result = lengthOfLongestSubstring(input)

        // Then
        assertEquals(1, result)
    }

}

class LongestSubstringWithoutRepeatingCharactersImpl: LongestSubstringWithoutRepeatingCharacters() {

    /**
     * Initial though
     *
     * have 2 pointers
     *  -> Left = 0
     *  -> Right = 0
     * 1 hash set
     * - value: seen char
     * longest = 0
     *
     *  Loop
     *  while (right < s.length)
     *    check if s[right] exists in the hashset
     *     -> If so,
     *          - increase left until right
     *              - every time, remove s[left] from the hashmap
     *          - add s[right] in the hashset
     *     -> If not
     *          -> add s[right] in the hashset
     *          -> check if right - left + 1 is longer than longest
     *              -> if so, update longest
     *  return longest
     */
    override fun lengthOfLongestSubstring(s: String): Int {
        var firstPointer = 0
        var secondPointer = 0
        var max = 0

        val hashSet = HashSet<Char>()

        while (secondPointer < s.length) {
            if (!hashSet.contains(s[secondPointer])) {
                hashSet.add(s[secondPointer])
                secondPointer++
                max = max(hashSet.size, max)
            } else {
                hashSet.remove(s[firstPointer])
                firstPointer++
            }
        }
        return max
    }
}

class LongestSubstringWithoutRepeatingCharactersOptim: LongestSubstringWithoutRepeatingCharacters() {

    /**
     * Optimization over the existing algorithm. Instead of using a hashset of all the chars in the string
     * Use an array of 128 chars, where it stores the last know position
     *
     * For each char, if the last position exists and it is bigger than start, then
     * move start to the next position to that position
     *
     * pwwkew = 3
     *
     * start = 2
     * [p = 0, w = 5, k = 3, e = 4]
     * max length = 2
     * Complexity:
     * - Time: O(n)
     * - Space: O(1)
     */
    override fun lengthOfLongestSubstring(s: String): Int {
        // 1. Init the variables
        var start = 0
        val positions = Array<Int>(128){ -1 }
        var maxLength = 0

        // 2. Loop
        s.forEachIndexed { index, c ->
            if (positions[c.code] >= start) {
                start = positions[c.code] + 1
            }
            positions[c.code] = index

            maxLength = max(maxLength, index - start + 1)
        }

        // Return the result
        return maxLength
    }
}
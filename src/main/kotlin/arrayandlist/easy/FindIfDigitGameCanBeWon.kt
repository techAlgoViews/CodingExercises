package main.kotlin.arrayandlist.easy

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * You are given an array of positive integers nums.
 * Alice and Bob are playing a game. In the game, Alice can choose either all single-digit numbers or all
 * double-digit numbers from nums, and the rest of the numbers are given to Bob. Alice wins if the sum of
 * her numbers is strictly greater than the sum of Bob's numbers.
 *
 * Return true if Alice can win this game, otherwise, return false.
 *
 * Example 1:
 *   Input: nums = [1,2,3,4,10]
 *   Output: false
 *   Explanation:
 *   Alice cannot win by choosing either single-digit or double-digit numbers.
 *
 * Example 2:
 *   Input: nums = [1,2,3,4,5,14]
 *   Output: true
 *   Explanation:
 *   Alice can win by choosing single-digit numbers which have a sum equal to 15.
 *
 * Example 3:
 *   Input: nums = [5,5,5,25]
 *   Output: true
 *   Explanation:
 *   Alice can win by choosing double-digit numbers which have a sum equal to 25.
 *
 * Constraints
 *  1 <= nums.length <= 100
 *  1 <= nums[i] <= 99
 */
abstract class FindIfDigitGameCanBeWon {

    abstract fun canAliceWin(nums: IntArray): Boolean

    @Test
    fun test1() {
        // Given
        val nums = intArrayOf(1, 2, 3, 4, 10)

        // When
        val result = canAliceWin(nums)

        // Then
        assertFalse(result)
    }

    @Test
    fun test2() {
        // Given
        val nums = intArrayOf(1, 2, 3, 4, 5, 14)

        // When
        val result = canAliceWin(nums)

        // Then
        assertTrue(result)
    }

    @Test
    fun test3() {
        // Given
        val nums = intArrayOf(5, 5, 5, 25)

        // When
        val result = canAliceWin(nums)

        // Then
        assertTrue(result)
    }
    
}

class FindIfDigitGameCanBeWonImpl: FindIfDigitGameCanBeWon() {
    override fun canAliceWin(nums: IntArray): Boolean {
        var singleDigit = 0
        var doubleDigit = 0
        nums.forEach {
            if (it <= 9) singleDigit += it
            else doubleDigit += it
        }
        if (singleDigit > doubleDigit || doubleDigit > singleDigit) return true;
        return false
    }

}
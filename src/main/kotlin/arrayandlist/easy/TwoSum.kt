package main.kotlin.arrayandlist.easy

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Two sum
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up
 * to target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * You can return the answer in any order.
 *
 * Example 1:
 *      Input: nums = [2,7,11,15], target = 9
 *      Output: [0,1]
 *      Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
 *
 * Example 2:
 *      Input: nums = [3,2,4], target = 6
 *      Output: [1,2]
 *
 * Example 3:
 *      Input: nums = [3,3], target = 6
 *      Output: [0,1]
 */
abstract class TwoSum {
    abstract fun twoSum(nums: IntArray, target: Int): IntArray

    @Test
    fun test1() {
        // Given
        val input = intArrayOf(2, 7, 11, 15)
        val target = 9

        // When
        val result = twoSum(input, target)

        // Then
        assertEquals(2, result.size)
        assertTrue(result.contains(0))
        assertTrue(result.contains(1))
    }

    @Test
    fun test2() {
        // Given
        val input = intArrayOf(3, 2, 4)
        val target = 6

        // When
        val result = twoSum(input, target)

        // Then
        assertEquals(2, result.size)
        assertTrue(result.contains(1))
        assertTrue(result.contains(2))
    }


    @Test
    fun test3() {
        // Given
        val input = intArrayOf(3, 3)
        val target = 6

        // When
        val result = twoSum(input, target)

        // Then
        assertEquals(2, result.size)
        assertTrue(result.contains(0))
        assertTrue(result.contains(1))
    }
}

class TwoSumImpl: TwoSum() {
    /**
     * Initial thought
     * - Normal cases
     *  - The list could contain negative numbers
     *  - The target could be negative
     *  Algorithm:
     *  - Use a hashMap of missing numbers
     *  - 1. Init hash map of Pair (Int, Int) where
     *      - key = complement NEEDED
     *      - value = complement position
     *  - 2. Loop (over the list)
     *      - If the key with the number[i] exists
     *          - return value and i
     *      - else
     *          - add new entry
     *              - key = target - number[i]
     *              - value = i
     * - 3. return empty list
     * - Corner cases
     *  - The list is empty -> return empty list
     */
    override fun twoSum(nums: IntArray, target: Int): IntArray {
        val myHashMap = HashMap<Int, Int>()
        nums.forEachIndexed{ index, item ->
            if (myHashMap.contains(item)) {
                return intArrayOf(myHashMap[item]!!, index)
            } else {
                myHashMap[target - item] = index
            }
        }

        return intArrayOf()
    }
}
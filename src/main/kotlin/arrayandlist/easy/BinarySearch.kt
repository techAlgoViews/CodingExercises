package main.kotlin.arrayandlist.easy

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Given an array of integers nums which is sorted in ascending order, and an integer target, write a function
 * to search target in nums. If target exists, then return its index. Otherwise, return -1.
 * You must write an algorithm with O(log n) runtime complexity.
 *
 * Example 1:
 *  Input: nums = [-1,0,3,5,9,12], target = 9
 *  Output: 4
 *  Explanation: 9 exists in nums and its index is 4
 *
 * Example 2:
 *  Input: nums = [-1,0,3,5,9,12], target = 2
 *  Output: -1
 *  Explanation: 2 does not exist in nums so return -1
 *
 * Constraints:
 *  - 1 <= nums.length <= 10^4
 *  - 10^4 < nums[i], target < 10^4
 *  - All the integers in nums are unique.
 *  - nums is sorted in ascending order.
 */
abstract class BinarySearch {
    abstract fun search(nums: IntArray, target: Int): Int

    @Test
    fun test1() {
        // Given
        val nums = intArrayOf(-1, 0, 3, 5, 9, 12)
        val target = 9

        // When
        val result = search(nums, target)

        // Then
        assertEquals(4, result)
    }

    @Test
    fun test2() {
        // Given
        val nums = intArrayOf(-1, 0, 3, 5, 9, 12)
        val target = 2

        // When
        val result = search(nums, target)

        // Then
        assertEquals(-1, result)
    }
}

class BinarySearchImpl: BinarySearch() {
    override fun search(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.size - 1

        while (left <= right) {
            val middle = left + (right - left) / 2
            if (nums[middle] == target) {
                return middle
            }

            if (nums[middle] > target) {
                right = middle - 1
            } else {
                left = middle + 1
            }
        }
        return -1
    }
}

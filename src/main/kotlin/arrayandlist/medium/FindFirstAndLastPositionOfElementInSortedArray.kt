package main.kotlin.arrayandlist.medium

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

/**
 * Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a
 * given target value.
 *
 * If target is not found in the array, return [-1, -1].
 *
 * You must write an algorithm with O(log n) runtime complexity.
 * Example 1:
 * - Input: nums = [5,7,7,8,8,10], target = 8
 * - Output: [3,4]
 *
 * Example 2:
 * - Input: nums = [5,7,7,8,8,10], target = 6
 * - Output: [-1,-1]
 *
 * Example 3:
 * - Input: nums = [], target = 0
 * - Output: [-1,-1]
 *
 * Constraints:
 * - 0 <= nums.length <= 105
 * - -10^9 <= nums[i] <= 10^9
 * - nums is a non-decreasing array.
 * - -10^9 <= target <= 10^9
 */
abstract class FindFirstAndLastPositionOfElementInSortedArray {

    abstract fun searchRange(nums: IntArray, target: Int): IntArray

    @ParameterizedTest(name = "The range of {1} in {0} should be {2}")
    @ArgumentsSource(TestDataArgumentProvider::class)
    fun test(nums: IntArray, target: Int, expectedValue: IntArray) {
        val result = searchRange(nums, target)
        Assertions.assertTrue(expectedValue contentEquals result)
    }

    class TestDataArgumentProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf(5,7,7,8,8,10), 8, intArrayOf(3, 4)),
                Arguments.of(intArrayOf(5,7,7,8,8,8,10,12,13,14), 8, intArrayOf(3, 5)),
                Arguments.of(intArrayOf(5,7,7,8,8,10), 6, intArrayOf(-1, -1)),
                Arguments.of(intArrayOf(), 0, intArrayOf(-1, -1)),
                Arguments.of(intArrayOf(8, 8, 8, 8, 8, 8), 8, intArrayOf(0, 5)),
            )
        }
    }
}

class FindFirstAndLastPositionOfElementInSortedArrayImpl: FindFirstAndLastPositionOfElementInSortedArray() {

    override fun searchRange(nums: IntArray, target: Int): IntArray {
        // Binary search for the target
        var left = 0
        var right = nums.size - 1

        while (left <= right) {
            val middlePos = left + (right - left) / 2

            if (nums[middlePos] == target) {
                // Expand the check to left and right
                val leftmost = findLeftmost(nums, target, middlePos)
                val rightmost = findRightmost(nums, target, middlePos)
                return intArrayOf(leftmost, rightmost)

            } else if (nums[middlePos] > target) {
                right = middlePos - 1
            } else {
                left = middlePos + 1
            }
        }

        return intArrayOf(-1, -1)
    }

    private fun findLeftmost(nums: IntArray, target: Int, currentPos: Int): Int {
        var leftmost = currentPos
        while ((leftmost - 1 >= 0) && nums[leftmost - 1] == target) {
            leftmost--
        }

        return leftmost
    }

    private fun findRightmost(nums: IntArray, target: Int, currentPos: Int): Int {
        var rightmost = currentPos
        while ((rightmost + 1 < nums.size) && nums[rightmost + 1] == target) {
            rightmost++
        }

        return rightmost
    }
}
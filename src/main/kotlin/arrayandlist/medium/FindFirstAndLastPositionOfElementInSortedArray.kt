package main.kotlin.arrayandlist.medium

import junit.framework.Assert.assertTrue
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
        assertTrue(expectedValue contentEquals result)
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

    /**
     * Initial thoughts
     * Basically there is an array of non-decreasing with repeated numbers
     * The result needs to be log(n), so it is a binary search.
     *
     * We can start searching for the number, then check for the left and right
     * extension
     *
     */
    override fun searchRange(nums: IntArray, target: Int): IntArray {
        // Binary search for the left number
        var left = 0
        var right = nums.size - 1

        while (left <= right) {
            val middlePos = left + (right - left) / 2

            if (nums[middlePos] == target) {
                // Check if there is anything on the left
                val leftest = findLeftest(nums, target, middlePos)
                val rightest = findRightest(nums, target, middlePos)
                return intArrayOf(leftest, rightest)

            } else if (nums[middlePos] > target) {
                right = middlePos - 1
            } else {
                left = middlePos + 1
            }
        }

        return intArrayOf(-1, -1)
    }

    private fun findLeftest(nums: IntArray, target: Int, currentPos: Int): Int {
        var leftest = currentPos
        while ((leftest - 1 >= 0) && nums[leftest - 1] == target) {
            leftest--
        }

        return leftest
    }

    private fun findRightest(nums: IntArray, target: Int, currentPos: Int): Int {
        var rightest = currentPos
        while ((rightest + 1 < nums.size) && nums[rightest + 1] == target) {
            rightest++
        }

        return rightest
    }
}
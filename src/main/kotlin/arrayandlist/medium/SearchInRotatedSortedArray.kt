package main.kotlin.arrayandlist.medium

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

/**
 *
 * There is an integer array nums sorted in ascending order (with distinct values).
 *
 * Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length)
 * such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
 * For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].
 *
 * Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums,
 * or -1 if it is not in nums.
 *
 * You must write an algorithm with O(log n) runtime complexity.
 *
 * Example 1:
 * - Input: nums = [4,5,6,7,0,1,2], target = 0
 * - Output: 4
 *
 * Example 2:
 * - Input: nums = [4,5,6,7,0,1,2], target = 3
 * - Output: -1
 *
 * Example 3:
 * - Input: nums = [1], target = 0
 * - Output: -1
 *
 * Constraints:
 * - 1 <= nums.length <= 5000
 * - -10^4 <= nums[i] <= 10^4
 * - All values of nums are unique.
 * - nums is an ascending array that is possibly rotated.
 * - -10^4 <= target <= 10^4
 */
abstract class SearchInRotatedSortedArray {

    abstract fun search(nums: IntArray, target: Int): Int

    @ParameterizedTest(name = "The position of {1} in {0} should be {2}")
    @ArgumentsSource(TestDataArgumentProvider::class)
    fun test(nums: IntArray, target: Int, expectedValue: Int) {
        val result = search(nums, target)
        Assertions.assertEquals(expectedValue, result)
    }


    class TestDataArgumentProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(listOf(4, 5, 6, 7, 0, 1, 2).toIntArray(), 0, 4),
                Arguments.of(listOf(4, 5, 6, 7, 0, 1, 2).toIntArray(), 3, -1),
                Arguments.of(listOf(1).toIntArray(), 0, -1),
            )
        }
    }
}

class SearchInRotatedSortedArrayImp: SearchInRotatedSortedArray() {

    /**
     * Initial thought
     * [4,5,6,7,0,1,2]. Searching for 0
     *
     *  8 |
     *  7 |             x
     *  6 |         x
     *  5 |     x
     *  4 |  x
     *  3 |
     *  2 |                          x
     *  1 |                      x
     *  0 | __  __  __  __  _x_  __  __
     *      0   1   2   3   4    5   6
     *
     * Binary search:
     *  1. Take the number in the middle.
     *  - If it is the right number -> Return the position
     *  2. Find the cliff
     *  - If the number on the leftest is bigger than the number in the middle
     *      -> The cut is between the leftest to the middle
     *      -> The array only contains numbers that are bigger than the leftest and smaller than the middle
     *          -> If number > leftest OR number < middle -> Take first half
     *          -> Otherwise take the second half
     *  - If the number on the leftest is smaller than the number in the middle
     *      -> The cut is on the right half
     *      -> The array contains the number that are bigger than leftest but smaller than the middle
     *          -> If number > leftest AND number < middle (if number between leftest and middle) -> Take first half
     *          -> otherwise take the second half
     */
    override fun search(nums: IntArray, target: Int): Int {
        // Binary search
        var leftestPos = 0
        var rightestPos = nums.size - 1

        // Start loop
        while (leftestPos <= rightestPos) {
            val middle = leftestPos + (rightestPos - leftestPos) / 2
            if (nums[middle] == target) return middle

            // If the leftest is bigger than the number in the middle
            if (nums[leftestPos] > nums[middle]) {
                if (target >= nums[leftestPos] || target < nums[middle]) {
                    rightestPos = middle - 1
                } else {
                    leftestPos = middle + 1
                }
            } else {
                if (target >= nums[leftestPos] && target < nums[middle]) {
                    rightestPos = middle - 1
                } else {
                    leftestPos = middle + 1
                }
            }
        }

        // The number cannot be found
        return -1
    }
}
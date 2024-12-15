package main.kotlin.arrayandlist.medium

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * Container with most water
 * You are given an integer array height of length n. There are n vertical lines drawn such that the two
 * endpoints of the ith line are (i, 0) and (i, height[i]).
 *
 * Find two lines that together with the x-axis form a container, such that the container contains the
 * most water.
 *
 * Return the maximum amount of water a container can store.
 * Notice that you may not slant the container.
 * Example 1:
 *      Input: height = [1,8,6,2,5,4,8,3,7]
 *      Output: 49
 *      Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case,
 *      the max area of water (blue section) the container can contain is 49.
 *
 * Example 2:
 *      Input: height = [1,1]
 *      Output: 1
 *
 * Constraints:
 *      n == height.length
 *      2 <= n <= 10^5
 *      0 <= height[i] <= 10^4
 */

abstract class ContainerWithMostWater {

    abstract fun maxArea(height: IntArray): Int

    @ParameterizedTest(name = "The container with most water of {0} should be {1}")
    @MethodSource("getData")
    fun test(array1: IntArray, expectedValue: Int) {
        val result = maxArea(array1)
        assertEquals(expectedValue, result,)
    }

    companion object {

        @JvmStatic
        fun getData(): List<Array<Any>> {
            return listOf(
                arrayOf(intArrayOf(1,8,6,2,5,4,8,3,7), 49),
                arrayOf(intArrayOf(1, 1), 1),
            )
        }
    }
}

class ContainerWithMostWaterImpl : ContainerWithMostWater() {
    override fun maxArea(height: IntArray): Int {
        var max = 0
        var leftPointer = 0
        var rightPointer = height.size - 1
        while(leftPointer < rightPointer) {
            var tmpMax = rightPointer - leftPointer
            if (height[leftPointer] > height[rightPointer]) {
                tmpMax *= height[rightPointer]
                rightPointer--
            } else {
                tmpMax *= height[leftPointer]
                leftPointer++
            }
            if (tmpMax > max) max = tmpMax
        }
        return max

    }
}
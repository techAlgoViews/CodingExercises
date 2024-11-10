package main.kotlin.arrayandlist.hard

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
 *
 * The overall run time complexity should be O(log (m+n)).
 *
 * Example 1:
 *      Input: nums1 = [1,3], nums2 = [2]
 *      Output: 2.00000
 *      Explanation: merged array = [1,2,3] and median is 2.
 *
 * Example 2:
 *      Input: nums1 = [1,2], nums2 = [3,4]
 *      Output: 2.50000
 *      Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
 *
 * Constraints:
 *      nums1.length == m
 *      nums2.length == n
 *      0 <= m <= 1000
 *      0 <= n <= 1000
 *      1 <= m + n <= 2000
 *      -10^6 <= nums1[i], nums2[i] <= 10^6
 */
abstract class MedianOfTwoSortedArrays() {

    abstract fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double

    @ParameterizedTest(name = "The median of {0} and {1} should be {2}")
    @MethodSource("getData")
    fun test(array1: IntArray, array2: IntArray, expectedValue: Double) {
        val result = findMedianSortedArrays(array1, array2)
        assertEquals(expectedValue, result, 1.0)
    }

    companion object {

        @JvmStatic
        fun getData(): List<Array<Any>> {
            return listOf(
                arrayOf(intArrayOf(1, 3), intArrayOf(2), 2.0),
                arrayOf(intArrayOf(1, 2), intArrayOf(3, 4), 2.5),
            )
        }
    }
}

class MedianOfTwoSortedArraysImpl: MedianOfTwoSortedArrays() {

    /**
     * Initial thoughts
     *
     * Based on the requirement of O(log(n + m)), it tells us that it is something similar to the binary search
     *
     * Due to that nums1 and nums2 are sorted, the key is search for the right element on every iteration
     * - Every step we need to get the half of n and half of m
     * Example:
     * - num1 = [1, 2, 3, 4], m = 4
     * - num2 = [1, 4, 5, 6, 7], n = 5
     *
     * Computing the half of each list
     *
     * 1. compute the half...
     *
     * https://www.youtube.com/watch?v=q6IEA26hvXc
     *
     */
    override fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        // Precondition: ensure nums1 is the shorter array
        if (nums1.size > nums2.size) {
            return findMedianSortedArrays(nums2, nums1)
        }

        // Step 1: Set the variables
        val m = nums1.size
        val n = nums2.size
        val totalLength = m + n
        val isOdd = totalLength % 2 == 1
        val half = (totalLength + 1) / 2

        var left = 0
        var right = m

        // Step 2: Binary search
        while (left <= right) {
            // Binary search on the shorter array
            val partition1 = (left + right) / 2
            val partition2 = half - partition1

            // Get left and right elements for both arrays
            val left1 = if (partition1 == 0) Double.NEGATIVE_INFINITY else nums1[partition1 - 1].toDouble()
            val right1 = if (partition1 == m) Double.POSITIVE_INFINITY else nums1[partition1].toDouble()
            val left2 = if (partition2 == 0) Double.NEGATIVE_INFINITY else nums2[partition2 - 1].toDouble()
            val right2 = if (partition2 == n) Double.POSITIVE_INFINITY else nums2[partition2].toDouble()

            // Check if we found the correct partition
            if (left1 <= right2 && left2 <= right1) {
                // If total length is odd
                return if (isOdd) {
                    maxOf(left1, left2)
                } else {
                    // If total length is even
                    (maxOf(left1, left2) + minOf(right1, right2)) / 2
                }
            }
            // Step 3: If the Adjust the binary search
            else if (left1 > right2) {
                right = partition1 - 1
            } else {
                left = partition1 + 1
            }
        }

        throw IllegalArgumentException("Input arrays are not sorted")
    }
}

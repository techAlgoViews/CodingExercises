package main.kotlin.math.easy

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Climbing Stairs
 *
 * You are climbing a staircase. It takes n steps to reach the top.
 *
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 * Example 1:
 *      Input: n = 2
 *      Output: 2
 *      Explanation: There are two ways to climb to the top.
 *          1. 1 step + 1 step
 *          2. 2 steps
 * Example 2:
 *      Input: n = 3
 *      Output: 3
 *      Explanation: There are three ways to climb to the top.
 *          1. 1 step + 1 step + 1 step
 *          2. 1 step + 2 steps
 *          3. 2 steps + 1 step
 *
 * Constraints:
 *      1 <= n <= 45
 */
abstract class ClimbingStairs {

    abstract fun climbStairs(n: Int): Int

    @Test
    fun test1() {
        // Given
        val n = 2

        // When
        val result = climbStairs(n)

        // Then
        assertEquals(2, result)
    }

    @Test
    fun test2() {
        // Given
        val n = 3

        // When
        val result = climbStairs(n)

        // Then
        assertEquals(3, result)
    }

    @Test
    fun test3() {
        // Given
        val n = 4

        // When
        val result = climbStairs(n)

        // Then
        assertEquals(5, result)
    }

    @Test
    fun test4() {
        // Given
        val n = 5

        // When
        val result = climbStairs(n)

        // Then
        assertEquals(8, result)
    }
}

class ClimbingStairsImpl: ClimbingStairs() {
    /**
     * Initial thoughts
     *
     * This is a typical DP problem
     *
     * Base case
     *  - climbStairs(1) = 1
     *  - climbStairs(2) = 2
     *
     * Main logic
     * - climbStairs(n) = climStairs(n - 1) + climbStairs(n - 2)
     *
     * Table: Per each step, store the possible value
     */
    override fun climbStairs(n: Int): Int {
        // Corner case
        if (n == 1) return 1
        if (n == 2) return 2

        // 1. Init the values
        // The cache has size n + 1 to accommodate the debugging
        // since the index starts with 0 always
        val cache = IntArray(n + 1)

        // 2. Set up the base cases
        cache[1] = 1
        cache[2] = 2

        // 3. Loop through
        for (i in 3 .. n) {
            val steps = cache[i - 1] + cache[i - 2]
            cache[i] = steps
        }

        // 4. Return the result
        return cache[n]
    }
}
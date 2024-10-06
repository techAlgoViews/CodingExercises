package main.kotlin.math.easy

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Happy number
 *
 * Write an algorithm to determine if a number n is happy.
 *
 * A happy number is a number defined by the following process:
 * Starting with any positive integer, replace the number by the sum of the squares of its digits.
 * Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle
 * which does not include 1.
 * Those numbers for which this process ends in 1 are happy.
 * Return true if n is a happy number, and false if not.
 *
 * Example 1:
 *  Input: n = 19
 *  Output: true
 *  Explanation:
 *      1^2 + 9^2 = 82
 *      8^2 + 2^2 = 68
 *      6^2 + 8^2 = 100
 *      1^2 + 0^2 + 0^2 = 1
 *
 * Example 2:
 *      Input: n = 2
 *      Output: false
 *
 * Constraints:
 *  1 <= n <= 2^31 - 1
 */
abstract class HappyNumber {

    abstract fun isHappy(n: Int): Boolean

    @Test
    fun test1() {
        // Given
        val input = 19

        // When
        val result = isHappy(input)

        // Then
        assertTrue(result)
    }

    @Test
    fun test2() {
        // Given
        val input = 2

        // When
        val result = isHappy(input)

        // Then
        assertFalse(result)
    }

    @Test
    fun test3() {
        // Given
        val input = 42

        // When
        val result = isHappy(input)

        // Then
        assertFalse(result)
    }

    @Test
    fun test4() {
        // Given
        val input = 10

        // When
        val result = isHappy(input)

        // Then
        assertTrue(result)
    }
}

class HappyNumberImpl: HappyNumber() {
    /**
     * Initial thought
     * To avoid the cycle, we can create a hash set to store all the number that has happened
     *
     * To loop, until the number is < 10, get the rest of the number and then, divide the number
     * into 10
     *
     * 1. Init variable
     * - Create an empty hash set
     * - result = 0
     *
     * 2. Loop
     * until n < 10
     * - result = result + (n rem 10)^2
     * - n = n / 10
     * end loop
     *
     * - result = result + n^2
     *
     * 3. Return result
     * if result = 1
     *    return true
     * if hash set contains result
     *      - return false
     *
     * add result to hash set
     * call the method with the result and the hashset
     *
     * In this case, we are going to need to remember the hashset
     * -> create another method which accepts the hashset as paramter
     */
    override fun isHappy(n: Int): Boolean {
        return isHappy(n, hashSetOf())
    }

    private fun isHappy(n: Int, prevResult: HashSet<Int>): Boolean {
        // Init
        var result = 0
        var number = n

        // Loop
        while (number > 0) {
            result += (number % 10) * (number % 10)
            number /= 10
        }

        // Check result
        if (result == 1) {
            return true
        }

        // We entered a loop
        if (prevResult.contains(result)) {
            return false
        }

        prevResult.add(result)
        return isHappy(result, prevResult)
    }
}

class HappyNumberBest: HappyNumber() {

    override fun isHappy(n: Int): Boolean {
        val prev = HashSet<Int>()
        var current = n
        var next = -1
        while (next != 1 && !prev.contains(current)) {
            next = getNext(current)
            prev.add(current)
            current = next
        }
        return next == 1
    }

    private fun getNext(n: Int): Int {
        var rem = n
        var result = 0
        var digit = 0
        while (rem > 0) {
            digit = rem % 10
            result += digit * digit
            rem /= 10
        }

        return result
    }
}

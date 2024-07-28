package main.kotlin.math.medium

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.sqrt

/**
 * You are given 2 positive integers l and r. For any number x, all positive divisors of x except x are called
 * the proper divisors of x.
 *
 * A number is called special if it has exactly 2 proper divisors. For example:
 *     The number 4 is special because it has proper divisors 1 and 2.
 *     The number 6 is not special because it has proper divisors 1, 2, and 3.
 *     Return the count of numbers in the range [l, r] that are not special.
 *
 * Example 1:
 *     Input: l = 5, r = 7
 *     Output: 3
 * Explanation:
 *     There are no special numbers in the range [5, 7].
 *
 * Example 2:
 *     Input: l = 4, r = 16
 *     Output: 11
 * Explanation:
 *     The special numbers in the range [4, 16] are 4 and 9.
 *
 * Constraints:
 *      1 <= l <= r <= 10^9
 */
abstract class FindTheCountOfNumbersWhichAreNotSpecial {

    abstract  fun nonSpecialCount(l: Int, r: Int): Int

    @Test
    fun test1() {
        // Given
        val l = 5
        val r = 7

        // When
        val result = nonSpecialCount(l, r)

        // Then
        assertEquals(3, result)
    }

    @Test
    fun test2() {
        // Given
        val l = 4
        val r = 16

        // When
        val result = nonSpecialCount(l, r)

        // Then
        assertEquals(11, result)
    }
}

class FindTheCountOfNumbersWhichAreNotSpecialSieveOfEratosthenes: FindTheCountOfNumbersWhichAreNotSpecial() {
    override fun nonSpecialCount(l: Int, r: Int): Int {
        // Get the upper bound
        val limit = sqrt(r.toDouble())

        // Check all the prime numbers
        val isPrime = calculatePrimeNumbers(limit.toInt())

        // Count the special numbers
        val specialCount = (2 .. limit.toInt())
            .filter { isPrime[it] }.count { it * it in l..r }

        // return total numbers - special count
        return (r - l + 1) - specialCount
    }

    /**
     * Find the prime numbers from 0 to limit
     * 0 and 1 are prime number by default
     */
    private fun calculatePrimeNumbers(limit: Int): BooleanArray {
        // Calculate prime numbers using Sieve of Eratosthenes
        // https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
        val isPrime = BooleanArray(limit + 1){ true }

        (2 .. limit).forEach { n ->
            if (isPrime[n]) {
                (n + n .. limit step n).forEach { isPrime[it] = false }
            }
        }


        return isPrime
    }
}


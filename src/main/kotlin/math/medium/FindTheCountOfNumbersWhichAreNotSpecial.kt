package main.kotlin.math.medium

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.sqrt

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

        val isPrime = calculatePrimeNumbers(limit.toInt())

        var specialCount = 0
        for (i in 2 .. limit.toInt()) {
            if (isPrime[i]) {
                val square = i * i
                if (square in l .. r) {
                    specialCount++
                }
            }
        }

        val totalCount = r - l + 1
        return totalCount - specialCount
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


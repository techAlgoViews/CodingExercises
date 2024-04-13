package main.kotlin.arrayandlist.easy

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Fizz Buzz
 *
 * Given an integer n, return a string array answer (1-indexed) where:
 *      answer[i] == "FizzBuzz" if i is divisible by 3 and 5.
 *      answer[i] == "Fizz" if i is divisible by 3.
 *      answer[i] == "Buzz" if i is divisible by 5.
 *      answer[i] == i (as a string) if none of the above conditions are true.
 *
 * Example 1:
 *      Input: n = 3
 *      Output: ["1","2","Fizz"]
 *
 * Example 2:
 *      Input: n = 5
 *      Output: ["1","2","Fizz","4","Buzz"]
 *
 * Example 3:
 *      Input: n = 15
 *      Output: ["1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14","FizzBuzz"]
 *
 * Constraints:
 *      1 <= n <= 10^4
 */
abstract class FizzBuzz {

    abstract fun fizzBuzz(n: Int): List<String>

    @Test
    fun test1() {
        // Given
        val n = 3

        // When
        val result = fizzBuzz(n)

        // Then
        assertEquals(listOf("1", "2", "Fizz"), result)
    }

    @Test
    fun test2() {
        // Given
        val n = 5

        // When
        val result = fizzBuzz(n)

        // Then
        assertEquals(listOf("1", "2", "Fizz", "4", "Buzz"), result)
    }

    @Test
    fun test3() {
        // Given
        val n = 15

        // When
        val result = fizzBuzz(n)

        // Then
        assertEquals(listOf("1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14","FizzBuzz"), result)
    }

}

class FizzBuzzImpl : FizzBuzz() {

    override fun fizzBuzz(n: Int): List<String> {
        // 1. Create the basic data
        val result = mutableListOf<String>()

        // 2. Loop through (from 1 to n, both inclusive)
        (1..n).forEach {
            when (true) {
                (it % 15 == 0) -> result.add("FizzBuzz")
                (it % 5 == 0) -> result.add("Buzz")
                (it % 3 == 0) -> result.add("Fizz")
                else -> result.add(it.toString())
            }
        }

        // 3. Return result
        return result
    }
}

class FizzBuzzOneLine: FizzBuzz() {

    override fun fizzBuzz(n: Int): List<String> {
        // Create the content and loop through
        return IntArray(n) { it + 1 }.map {
            when (true) {
                (it % 15 == 0) -> "FizzBuzz"
                (it % 5 == 0) -> "Buzz"
                (it % 3 == 0) -> "Fizz"
                else -> it.toString()
            }
        }
    }
}

class FizzBuzzAppend: FizzBuzz() {

    override fun fizzBuzz(n: Int): List<String> {
        // Create the content and loop through
        return Array(n) { "" }.mapIndexed { index, _ ->
            val sb = StringBuilder()

            if ((index + 1) % 3 == 0) {
                sb.append("Fizz")
            }

            if ((index + 1) % 5 == 0) {
                sb.append("Buzz")
            }

            if (sb.isEmpty()) {
                sb.append(index + 1)
            }

            sb.toString()
        }
    }
}
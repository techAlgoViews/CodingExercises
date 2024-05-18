package main.kotlin.string.easy

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * First letter to appear twice
 *
 * Given a string str, create a function that returns the first repeating character.
 * If such character doesn't exist, return 'a'.
 *
 * Example 1:
 *      Input: str = "inside code"
 *      Output: 'i'
 *
 * Example 2:
 *      Input: str = "programming"
 *      Output: 'r'
 *
 * Example 3:
 *      Input: str = "abcd"
 *      Output: 'null'
 *
 * Example 4:
 *      Input: str = "abba"
 *      Output: 'b'
 */
abstract class FirstLetterToAppearTwice {

    abstract fun firstLetterToAppearTwice(s: String): Char

    @Test
    fun test1() {
        // Given
        val str = "inside code"

        // When
        val result = firstLetterToAppearTwice(str)

        // Then
        assertEquals('i', result)
    }

    @Test
    fun test2() {
        // Given
        val str = "programming"

        // When
        val result = firstLetterToAppearTwice(str)

        // Then
        assertEquals('r', result)
    }

    @Test
    fun test3() {
        // Given
        val str = "abcd"

        // When
        val result = firstLetterToAppearTwice(str)

        // Then
        assertEquals('a', result)
    }

    @Test
    fun test4() {
        // Given
        val str = "abba"

        // When
        val result = firstLetterToAppearTwice(str)

        // Then
        assertEquals('b', result)
    }

    @Test
    fun test5() {
        // Given
        val str = "abccbaacz"

        // When
        val result = firstLetterToAppearTwice(str)

        // Then
        assertEquals('c', result)
    }

    @Test
    fun test6() {
        // Given
        val str = "abcdd"

        // When
        val result = firstLetterToAppearTwice(str)

        // Then
        assertEquals('d', result)
    }

}

class FirstLetterToAppearTwiceImpl: FirstLetterToAppearTwice() {

    /**
     * Initial thoughts
     *
     * Using a hashSet
     *
     * Complexity:
     * - Time: O(n)
     * - Space: O(n)
     *
     */
    override fun firstLetterToAppearTwice(s: String): Char {
        // 1. Init the variable
        val hashSet = HashSet<Char>()

        // 2. Loop
        for (ch in s) {
            if (hashSet.contains(ch)) {
                return ch
            } else {
                hashSet.add(ch)
            }
        }

        return 'a'
    }
}

class FirstLetterToAppearTwiceOptimSpace: FirstLetterToAppearTwice() {

    /**
     * Instead of using a hash set, use an array of chars
     */
    override fun firstLetterToAppearTwice(s: String): Char {
        // 1. Init the variable
        val myArray = Array<Boolean>(26) { false }

        // 2. Repeat
       for (ch in s) {
            if (ch != ' ') {
                if (myArray[ch - 'a']) {
                    return ch
                } else {
                    myArray[ch - 'a'] = true
                }
            }
        }

        return 'a'
    }
}

class FirstLetterToAppearTwiceOptimized: FirstLetterToAppearTwice() {
    /**
     * Input: s = "abccbaacz
     * a { }               -> {'a'} true
     * b { 'a' }           -> {'a','b'} true
     * c { 'a', 'b'}       -> {'a', 'b', 'c'} true
     * c { 'a', 'b', 'c' } -> {'a', 'b', 'c'} false
     */
    override fun firstLetterToAppearTwice(s: String): Char {
        // 1. Init the variable
        val hashSet = HashSet<Char>()

        // 2. Loop
        for (ch in s) {
            if (!hashSet.add(ch)) {
                return ch
            }
        }

        return 'a'
    }

}
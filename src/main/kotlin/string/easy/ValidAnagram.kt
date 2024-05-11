package main.kotlin.string.easy

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

/**
 * Valid anagram
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.

 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
 *  typically using all the original letters exactly once.
 */
abstract class ValidAnagram {

    abstract fun isValid(s: String, t: String): Boolean

    @Test
    fun test1() {
        // Given
        val original = "anagram"
        val check = "nagaram"

        // When
        val result = isValid(original, check)

        // Then
        assertTrue(result)
    }

    @Test
    fun test2() {
        // Given
        val original = "rat"
        val check = "cat"

        // When
        val result = isValid(original, check)

        // Then
        assertFalse(result)
    }

    @Test
    fun test3() {
        // Given
        val original = "CNBC"
        val check = "BCCN"

        // When
        val result = isValid(original, check)

        // Then
        assertTrue(result)
    }

    @Test
    fun test4() {
        // Given
        val original = "CNaBC"
        val check = "BCaCN"

        // When
        val result = isValid(original, check)

        // Then
        assertTrue(result)
    }
}

class ValidAnagramImpl: ValidAnagram() {

    /**
     * Optimal solution for when the parameter are only lower case
     */
    override fun isValid(s: String, t: String): Boolean {
        // Corner case check
        if (s.length != t.length) {
            return false
        }

        // Create the map of 26 chars, since there are only 26 chars in English
        val map = IntArray(128)

        // Build the map with the chars
        s.toCharArray().forEach {
            map[it.code] ++
        }

        // Check the chars against the map
        t.toCharArray().forEach {
            map[it.code] --
        }

        // Check the result
        map.forEach{
            if (it != 0) return false
        }
        return true
    }
}

class ValidAnagramOptim: ValidAnagram() {

    override fun isValid(s: String, t:String): Boolean {
        // Corner case
        if (s.length != t.length) {
            return false
        }

        val charHashMap = HashMap<Char, Int>()

        // Build the char
        s.toCharArray().forEach {
            charHashMap[it] = charHashMap.getOrDefault(it, 0) + 1
        }

        // Check the result
        t.toCharArray().forEach {char ->
            var value = charHashMap.getOrDefault(char, 0)
            charHashMap[char] = --value
        }

        return charHashMap.filter {(_, v) ->
            v != 0
        }.isEmpty()
    }
}
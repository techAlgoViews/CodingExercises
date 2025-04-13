package main.kotlin.arrayandlist.easy

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * Longest common prefix
 *
 * Write a function to find the longest common prefix string amongst an array of strings.
 *
 * If there is no common prefix, return an empty string "".
 *
 * Example 1:
 *      Input: strs = ["flower","flow","flight"]
 *      Output: "fl"
 *
 * Example 2:
 *      Input: strs = ["dog","racecar","car"]
 *      Output: ""
 *      Explanation: There is no common prefix among the input strings.
 *
 * Constraints:
 *      1 <= strs.length <= 200
 *      0 <= strs[i].length <= 200
 *      strs[i] consists of only lowercase English letters.
 */

abstract class LongestCommonPrefix {

    abstract fun longestCommonPrefix(strs: Array<String>): String

    @ParameterizedTest(name = "The longest common prefix of {0} should be {1}")
    @MethodSource("getData")
    fun test(array1: Array<String>, expectedValue: String) {
        val result = longestCommonPrefix(array1)
        Assertions.assertEquals(expectedValue, result,)
    }

    companion object {

        @JvmStatic
        fun getData(): List<Array<Any>> {
            return listOf(
                arrayOf(arrayOf("flower","flow","flight"), "fl"),
                arrayOf(arrayOf("dog","racecar","car"), ""),
            )
        }
    }
}

class LongestCommonPrefixImpl: LongestCommonPrefix() {

    override fun longestCommonPrefix(strs: Array<String>): String {
        if (strs.isEmpty()) {
            return ""
        }

        if (strs.size == 1) {
            return strs[0]
        }

        // Find the shortest element
        var shortestStringSize = Integer.MAX_VALUE
        var shortestString = ""
        strs.forEachIndexed{_, element ->
            if (element.length < shortestStringSize) {
                shortestStringSize = element.length
                shortestString = element
            }}

        for (charPosition in shortestString.length downTo 0) {
            val prefix = shortestString.substring(0, charPosition)
            if (samePrefix(strs, prefix)) {
                return prefix
            }
        }

        return ""
    }

    private fun samePrefix(strs: Array<String>, prefix: String): Boolean {
        for (string in strs) {
            if (prefix != string.substring(0, prefix.length)) {
                return false
            }
        }

        return true
    }
}
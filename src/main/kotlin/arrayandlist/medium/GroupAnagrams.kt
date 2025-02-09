package main.kotlin.arrayandlist.medium

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.collections.Map.Entry

/**
 * Group anagram
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
 *
 * typically using all the original letters exactly once.
 * Example 1:
 *      Input: strs = ["eat","tea","tan","ate","nat","bat"]
 *      Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * Example 2:
 *      Input: strs = [""]
 *      Output: [[""]]
 * Example 3:
 *      Input: strs = ["a"]
 *      Output: [["a"]]
 */

abstract class GroupAnagrams() {

    abstract fun groupAnagrams(strs: Array<String>): List<List<String>>

    @ParameterizedTest(name = "The group anagrams of {0} is {1}")
    @MethodSource("getData")
    fun test(strs: Array<String>, expectedValue: List<List<String>>) {
        val result = groupAnagrams(strs)
        Assertions.assertEquals(expectedValue.size, result.size)
        result.forEach {
            expectedValue.contains(it.toSet().toList())
        }
        expectedValue.forEach {
            result.contains(it.toSet().toList())
        }
    }

    companion object {
        @JvmStatic
        fun getData(): List<Array<Any>> {
            return listOf(
                arrayOf(arrayOf("bdddddddddd","bbbbbbbbbbc"),
                    listOf(listOf("bbbbbbbbbbc"),listOf("bdddddddddd"))),
                arrayOf(arrayOf("eat","tea","tan","ate","nat","bat"),
                    listOf(listOf("bat"),listOf("nat", "tan"), listOf("ate", "eat", "tea"))),
            )
        }
    }
}

class GroupAnagramsImpl: GroupAnagrams() {
    // Video: https://youtube.com/shorts/vLBOIcEESVU
    override fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val group = HashMap<String, ArrayList<String>>()

        // Build the hashmap
        strs.forEach {
            val hash = createHash(it)
            val myList = group.getOrPut(hash) {
                arrayListOf()
            }
            myList.add(it)
        }

        // Return the content
        return group.map(Entry<String, ArrayList<String>>::value)
    }

    private fun createHash(word: String): String {
        val myKeys = CharArray(26)
        word.forEach {
            myKeys[it - 'a']++
        }
        return String(myKeys)
    }
}
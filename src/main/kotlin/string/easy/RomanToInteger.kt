package main.kotlin.string.easy

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Roman to integer
 *
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 *
 *  Symbol       Value
 *   I             1
 *   V             5
 *   X             10
 *   L             50
 *   C             100
 *   D             500
 *   M             1000
 *
 * For example, 2 is written as II in Roman numeral, just two ones added together. 12 is written as XII,
 * which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.
 *
 * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four
 * is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it
 * making four. The same principle applies to the number nine, which is written as IX. There are six instances
 * where subtraction is used:
 *
 * I can be placed before V (5) and X (10) to make 4 and 9.
 * X can be placed before L (50) and C (100) to make 40 and 90.
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * Given a roman numeral, convert it to an integer.
 *
 * Example 1:
 *      Input: s = "III"
 *      Output: 3
 *      Explanation: III = 3.
 *
 * Example 2:
 *      Input: s = "LVIII"
 *      Output: 58
 *      Explanation: L = 50, V= 5, III = 3.
 *
 * Example 3:
 *      Input: s = "MCMXCIV"
 *      Output: 1994
 *      Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 *
 * Constraints:
 *      1 <= s.length <= 15
 *      s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
 *      It is guaranteed that s is a valid roman numeral in the range [1, 3999].
 */
abstract class RomanToInteger {

    abstract fun romanToInt(s: String): Int

    @Test
    fun test1() {
        // Given
        val input = "III"

        // When
        val result = romanToInt(input)

        // Then
        assertEquals(3, result)
    }

    @Test
    fun test2() {
        // Given
        val input = "LVIII"

        // When
        val result = romanToInt(input)

        // Then
        assertEquals(58, result)
    }

    @Test
    fun test3() {
        // Given
        val input = "IV"

        // When
        val result = romanToInt(input)

        // Then
        assertEquals(4, result)
    }

    @Test
    fun test4() {
        // Given
        val input = "MCMXCIV"

        // When
        val result = romanToInt(input)

        // Then
        assertEquals(1994, result)
    }
}

class RomanToIntegerImpl: RomanToInteger() {

    /** IV III VI
     * Initial though
     * 1. Build a dictionary of roman numbers
     * init previous element as -1
     * 2. Loop (from the first element to the last element)
     *    Get the current element
     *    If the current element is smaller or equals than the previous element
     *      add the previous element to the result
     *      assign the value of the current element to the previous element
     *     else
     *      extract from the current element the value of the previous element
     *      add it to the result
     *      assign MAX value to the previous element (it is not very good)
     *  3. return the result
     *
     *  Improvement
     *   M CM X C IV
     *  Going through the list
     *      check the value of the previous element
     *      if it is bigger than the current element, add current element
     *      if it smaller than the current element, add the current element, and extra 2 * value of the previous element
     * So we just have to track one element at the time
     *
     * Small optimization -> Have a variable to keep the value of the previous element
     *
     */
    override fun romanToInt(s: String): Int {
        // 1. Init the value
        var previousValue = 0
        var result = 0
        val dict = IntArray(26)
        dict['I' - 'A'] = 1
        dict['V' - 'A'] = 5
        dict['X' - 'A'] = 10
        dict['L' - 'A'] = 50
        dict['C' - 'A'] = 100
        dict['D' - 'A'] = 500
        dict['M' - 'A'] = 1000

//           X I V = 14
//Pointer    i
//PV = 1
//CV = 5
//Result 0 + 10 + 1 + 5 - (1 * 2) = 14

        // 2. Loop
        for (element in s) {
            // Get the current value corresponding to the roman number
            val currentValue = dict[element - 'A']
            // Add the current value to the result
            result += currentValue
            // If the current number is bigger than the previous number, we have
            // added the previous number wrongly. Instead of remove it, we have added it
            // so we have to extract it twice
            if (currentValue > previousValue) {
                result -= previousValue * 2
            }
            previousValue = currentValue
        }

        // 3. Show result
        return result
    }
}
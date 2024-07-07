package main.kotlin.arrayandlist.easy

import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Integer.max
import java.lang.Integer.min

/**
 * Best time to buy and sell stocks
 *
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day
 * in the future to sell that stock.
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 * Example 1:
 *      Input: prices = [7,1,5,3,6,4]
 *      Output: 5
 *      Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 *      Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
 *
 * Example 2:
 *      Input: prices = [7,6,4,3,1]
 *      Output: 0
 *      Explanation: In this case, no transactions are done and the max profit = 0.
 *
 * Constraints:
 *      1 <= prices.length <= 10^5
 *      0 <= prices[i] <= 10^4
 */
abstract class BestTimeToBuyAndSellStock {

    abstract fun maxProfit(prices: IntArray): Int

    @Test
    fun test1() {
        // Given
        val prices = intArrayOf(7, 1, 5, 3, 6, 4)

        // When
        val result = maxProfit(prices)

        // Then
        assertEquals(5, result)
    }

    @Test
    fun test2() {
        // Given
        val prices = intArrayOf(7,6,4,3,1)

        // When
        val result = maxProfit(prices)

        // Then
        assertEquals(0, result)
    }
}

class BestTimeToBuyAndSellStockImpl: BestTimeToBuyAndSellStock() {

    /**
     * Initial thought
     * Find the biggest difference between a number of position i to a number of position i + n, where n > 0
     * brute force:
     * - Init list of results
     * - Loop (for every element of position i)
     *  - Loop (for every element behind that element (position j))
     *  - add the difference between prices[j] and prices[i] to the list of results
     * - Find the max number on the list of results
     * Complexity:
     * Time: O(n^2)
     * Space: O(n) where n is the list of results
     *
     * ie [7, 1, 5, 3, 6, 4]
     * Max(Difference [7, 1, 5, 3, 6, 4]) = max[[7, 1], [7, 1, 5], [7, 1, 5, 3], [7, 1, 5, 3, 6], [7, 1, 5, 3, 6, 4]]
     * = max([7, 1], [7, 5], [7, 3], [7, 6], [7, 4]) = max(-6, -2, -4, -1, -3) = max(-1). Which is less than 0 -> Not useful
     * So the logic here is
     * For every item in the position i
     * - Max((array[i] - array[i + 1]), (array[i] - array[i + 2]), ..., (array[i] - array[array.length -1]))
     * - If max < 0 -> Return 0
     * Optimization 1:
     * - If the array[i] > array[i + 1] -> Do not include it on the max
     * 1. Init
     *  max = 0
     * 2. Loop (for every item in the list with pos i)
     *      - Loop (from position i + 1 till end of the list (pos j))
     *       - if (array[i] > array[j]) continue
     *       - max = max(max, array[i] - array[j])
     * 3. Return max
     *
     * Alternative 1
     * Loop (per each item on the position i)
     *  find the biggest number from position i + 1 to the end of the array
     *  max = max(max, array[i] - max(subarray[i, array.length -1])
     *  The complexity is still O(n^2)
     */
    override fun maxProfit(prices: IntArray): Int {
        var max = 0
        prices.forEachIndexed { index, item ->
            var maxSubarray = 0
            for (i in index + 1 until prices.size) {
                maxSubarray = max(maxSubarray, prices[i])
            }
            max = max(max, maxSubarray - item)
        }

        return max
    }
}

class BestTimeToBuyAndSellStockTwoPointer: BestTimeToBuyAndSellStock() {

    /**
     * Optimization (More efficient)
     * The idea is to look for a smaller number while checking for the biggest profit
     * - Smaller number if (current number - next number) < 0
     * - Biggest profit if (current number - next number) = max
     *
     * We need to have a variable to store the max
     * If a smaller number is found, then the next thing to check is that smaller number
     * The idea is having two pointers, like a sliding windows. Left and right moves to find
     * the maximum profit, until a number smaller than left is found. Then switch the windows
     * [7, 1, 5, 3, 6, 4]
     * 1. Init variables
     * left pos = 0
     * right pos = 1
     * max = 0
     * 2. Create windows
     * [7, 1]
     * The difference between them is negative -> Switch windows
     * left pos = 1
     * right pos = left pos + 1 = 2
     * window = [1, 5] -> max = 4
     * right pos = right pos + 1 -> window = [1, 3]. Difference = 2. max = 4
     * right pos = right pos + 1 -> window = [1, 6]. Difference = 5. max = 5
     * right pos = right pos + 1 -> window = [1, 4]. Difference = 3. max = 5
     *
     * return max
     */
    override fun maxProfit(prices: IntArray): Int {
        // 1. init
        var max = 0
        var left = 0
        var right = 1
        // 2. Loop
        while (right < prices.size) {
            val difference = prices[right] - prices[left]
            if (difference < 0) {
                left = right
            } else {
                max = max(max, difference)
            }
            right++
        }
        // 3. Return value
        return max
    }
}

class BestTimeToBuyAndSellStockSubset: BestTimeToBuyAndSellStock() {
    /**
     * The idea is finding the minimum price before today and check if the difference between that
     * minimum and the price today is bigger than the maximum price so far.
     */
    override fun maxProfit(prices: IntArray): Int {
        var minPrice = prices[0]
        var maxProfit = 0
        for (index in 1 until prices.size) {
            val profitSoFar = prices[index] - minPrice
            maxProfit = max(maxProfit, profitSoFar)
            minPrice = min(minPrice, prices[index])
        }
        return maxProfit
    }

}
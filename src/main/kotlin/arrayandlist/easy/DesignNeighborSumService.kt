package main.kotlin.arrayandlist.easy

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * You are given a n x n 2D array grid containing distinct elements in the range [0, n^2 - 1].
 *
 * Implement the neighborSum class:
 * - neighborSum(int [][]grid) initializes the object.
 * - int adjacentSum(int value) returns the sum of elements which are adjacent neighbors of value, that is either to
 * the top, left, right, or bottom of value in grid.
 * - int diagonalSum(int value) returns the sum of elements which are diagonal neighbors of value, that is either to
 * the top-left, top-right, bottom-left, or bottom-right of value in grid.
 *
 * Example 1:
 *  Input:
 *      ["neighborSum", "adjacentSum", "adjacentSum", "diagonalSum", "diagonalSum"]
 *      [[[[0, 1, 2], [3, 4, 5], [6, 7, 8]]], [1], [4], [4], [8]]
 *  Output: [null, 6, 16, 16, 4]
 *  Explanation:
 *      The adjacent neighbors of 1 are 0, 2, and 4.
 *      The adjacent neighbors of 4 are 1, 3, 5, and 7.
 *      The diagonal neighbors of 4 are 0, 2, 6, and 8.
 *      The diagonal neighbor of 8 is 4.
 *
 * Example 2:
 *  Input:
 *      ["neighborSum", "adjacentSum", "diagonalSum"]
 *      [[[[1, 2, 0, 3], [4, 7, 15, 6], [8, 9, 10, 11], [12, 13, 14, 5]]], [15], [9]]
 *  Output: [null, 23, 45]
 *  Explanation:
 *      The adjacent neighbors of 15 are 0, 10, 7, and 6.
 *      The diagonal neighbors of 9 are 4, 12, 14, and 15.
 *
 *  Constraints:
 *  - 3 <= n == grid.length == grid[0].length <= 10
 *  - 0 <= grid[i][j] <= n^2 - 1
 *  - All grid[i][j] are distinct.
 *  - value in adjacentSum and diagonalSum will be in the range [0, n^2 - 1].
 *  - At most 2 * n^2 calls will be made to adjacentSum and diagonalSum.
 */
abstract class NeighborSum(grid: Array<IntArray>) {

    abstract fun adjacentSum(value: Int): Int
    abstract fun diagonalSum(value: Int): Int
}

class NeighborSumImp(private val grid: Array<IntArray>): NeighborSum(grid) {
    /**
     * Initial thoughts
     *
     * The value is a random number. It could be anywhere in the grid. So the first thing to do is actually
     * find the value.
     *
     * The value is in a position x,y. So for adjacentSum, the algo needs to
     * 1. Init the sum to 0
     * 2. If possible, find the value at top of the target value, and add it to the sum (x, y-1)
     * 3. If possible, find the value at left of the target value, and add it to the sum (x-1, y)
     * 4. If possible, find the value at right of the target value, and add it to the sum (x+1, y)
     * 5. If possible, find the value at bottom of the target value, and add it to the sum (x, y+1)
     *
     * Helping methods
     * - Find Position: Given a value, find the position of that value on x, y
     * - Find value: Given a position, find hte value of that position. If it is outside of the grid, return 0
     */
    override fun adjacentSum(value: Int): Int {
        // 1. Find the position of the value
        val (x, y) = findValue(value) // 4 = (1, 1)

        // 2. Add the values
        var sum = 0
        // Top
        sum += findValueAtPosition(x, y - 1) // grid[0][1] = 1

        // Left
        sum += findValueAtPosition(x - 1, y) // grid[1][0] = 3

        // Right
        sum += findValueAtPosition(x + 1, y) // grid[1][2] = 5

        // Bottom
        sum += findValueAtPosition(x, y + 1) // grid[2][1] = 7

        return sum
    }

    /**
     * Find the position of the value. Note first I find y then I find the x. This is because of
     * array = [3, 4, 5] -> array[0] = 3, array[1] = 4, array[2] = 5
     *
     * grid = [[0, 1, 2]      grid [0] = [0, 1, 2]    grid[0][1] = 1
     *         [3, 4, 5],  -> grid [1] = [3, 4, 5] -> pos = (x, y)
     *         [6, 7, 8]]     grid [2] = [6, 7, 8]        = (1, 0)
     *   y
     * x -- 0 - 1 - 2
     *   |
     *   0  0   1   2
     *   |
     *   1  3   4   5
     *   |
     *   2  6   7   8
     */
    private fun findValue(value: Int): Pair<Int, Int> {
        grid.forEachIndexed { y, ints ->
            ints.forEachIndexed { x, item ->
                if (item == value) {
                    return Pair(x, y)
                }
            }
        }

        return Pair(-1, -1)
    }

    private fun findValueAtPosition(x: Int, y: Int): Int {
        // If the position cannot be found, return 0
        if (x < 0 || y < 0 || x >= grid.size || y >= grid.size ) return 0
        grid.forEachIndexed { posY, ints ->
            ints.forEachIndexed { posX, item ->
                if (posX == x && posY == y ) {
                    return item
                }
            }
        }

        return 0
    }

    /**
     * Same but the positions are different
     * 1. Sum the value at top left (x-1, y-1)
     * 2. Sum the value at top right (x+1, y-1)
     * 3. Sum the value at bottom left (x-1, y+1)
     * 4. Sum the value at bottom right (x+1, y+1)
     */
    override fun diagonalSum(value: Int): Int {
        // 1. Find the position of the value
        val (x, y) = findValue(value)

        // 2. Add the values
        var sum = 0
        // Top left
        sum += findValueAtPosition(x-1, y - 1)

        // Top right
        sum += findValueAtPosition(x+1, y-1)

        // Bottom left
        sum += findValueAtPosition(x-1, y+1)

        // Bottom right
        sum += findValueAtPosition(x+1, y + 1)

        return sum
    }
}

class NeighborSumTest() {

    @Test
    fun test1() {
        // Given
        val grid = arrayOf(intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8))
        val neighborSum = NeighborSumImp(grid)

        // When
        val adjSum = neighborSum.adjacentSum(1)

        // Then
        assertEquals(6, adjSum)
    }

    @Test
    fun test2() {
        // Given
        val grid = arrayOf(intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8))
        val neighborSum = NeighborSumImp(grid)

        // When
        val adjSum = neighborSum.adjacentSum(4)

        // Then
        assertEquals(16, adjSum)
    }

    @Test
    fun test3() {
        // Given
        val grid = arrayOf(intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8))
        val neighborSum = NeighborSumImp(grid)

        // When
        val adjSum = neighborSum.diagonalSum(4)

        // Then
        assertEquals(16, adjSum)
    }

    @Test
    fun test() {
        // Given
        val grid = arrayOf(intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8))
        val neighborSum = NeighborSumImp(grid)

        // When
        val adjSum = neighborSum.diagonalSum(4)

        // Then
        assertEquals(16, adjSum)
    }

}
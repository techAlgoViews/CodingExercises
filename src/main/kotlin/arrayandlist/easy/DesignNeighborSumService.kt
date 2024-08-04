package main.kotlin.arrayandlist.easy

import org.junit.Assert.assertEquals
import org.junit.Test

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
        val (x, y) = findValue(value)

        // 2. Add the values
        var sum = 0
        // Top
        sum += findValueAtPosition(x, y - 1)

        // Left
        sum += findValueAtPosition(x-1, y)

        // Right
        sum += findValueAtPosition(x+1, y)

        // Bottom
        sum += findValueAtPosition(x, y + 1)

        return sum
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

    private fun findValue(value: Int): Pair<Int, Int> {
        grid.forEachIndexed { x, ints ->
            ints.forEachIndexed { y, item ->
                if (item == value) {
                    return Pair(x, y)
                }
            }
        }

        return Pair(-1, -1)
    }

    private fun findValueAtPosition(x: Int, y: Int): Int {
        // If the position cannot be found, return 0
        if (x < 0 || y < 0) return 0
        grid.forEachIndexed { posX, ints ->
            ints.forEachIndexed { posY, item ->
                if (posX == x && posY == y ) {
                    return item
                }
            }
        }

        return 0
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
package main.kotlin.arrayandlist.easy

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Toeplitz Matrix
 *
 * Given an m x n matrix, return true if the matrix is Toeplitz. Otherwise, return false.
 *
 * A matrix is Toeplitz if every diagonal from top-left to bottom-right has the same elements.
 *
 * Example 1:
 *      Input: matrix = [[1,2,3,4],[5,1,2,3],[9,5,1,2]]
 *      Output: true
 *      Explanation:
 *      In the above grid, the diagonals are:
 *          "[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]".
 *          In each diagonal all elements are the same, so the answer is True.
 *
 * Example 2:
 *      Input: matrix = [[1,2],[2,2]]
 *      Output: false
 *      Explanation:
 *      The diagonal "[1, 2]" has different elements.
 *
 * Constraints:
 *      m == matrix.length
 *      n == matrix[i].length
 *      1 <= m, n <= 20
 *      0 <= matrix[i][j] <= 99
 *
 * Follow up:
 *      What if the matrix is stored on disk, and the memory is limited such that you can only load at most one
 *          row of the matrix into the memory at once?
 *      What if the matrix is so large that you can only load up a partial row into the memory at once?
 *
 */
abstract class ToeplitzMatrix {

    abstract fun isToeplitzMatrix(matrix: Array<IntArray>): Boolean

    @Test
    fun test1() {
        // Given
        val matrix = arrayOf(intArrayOf(1,2,3,4), intArrayOf(5,1,2,3), intArrayOf(9,5,1,2))

        // When
        val result = isToeplitzMatrix(matrix)

        // Then
        assertTrue(result)
    }

    @Test
    fun test2() {
        // Given
        val matrix = arrayOf(intArrayOf(1,2), intArrayOf(2,2))

        // When
        val result = isToeplitzMatrix(matrix)

        // Then
        assertFalse(result)
    }
}


class ToeplitzMatrixImpl: ToeplitzMatrix() {

    /**
     * initial thought
     * We need to check, for each row, if the item i+1, j+1 is the same
     *
     * and this goes from the first to the n-2 row
     * and from first to m - 2 column
     *
     */
    override fun isToeplitzMatrix(matrix: Array<IntArray>): Boolean {
        for (i in 0 until matrix.size - 1) {
            val row = matrix[i]
            for (j in 0 until row.size - 1) {
                if (matrix[i][j] != matrix[i+1][j+1]) {
                    return false
                }
            }
        }

        return true
    }
}
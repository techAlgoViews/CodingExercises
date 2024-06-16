package main.kotlin.tree.easy

import org.junit.Assert.assertEquals
import org.junit.Test
import main.kotlin.tree.TreeNode
import tree.toBinaryTree
import tree.toList

/**
 * Invert binary tree
 *
 * Given the root of a binary tree, invert the tree, and return its root.
 *
 * Example 1:
 *      Input: root = [4,2,7,1,3,6,9]
 *      Output: [4,7,2,9,6,3,1]
 *
 * Example 2:
 *      Input: root = [2,1,3]
 *      Output: [2,3,1]
 *
 * Example 3:
 *      Input: root = []
 *      Output: []
 *
 * Constraints:
 *      The number of nodes in the tree is in the range [0, 100].
 *      -100 <= Node.val <= 100
 */
abstract class InvertBinaryTree {

    abstract fun invertTree(root: TreeNode?): TreeNode?

    @Test
    fun test1() {
        // Given
        val list = listOf(4,2,7,1,3,6,9)
        val root = list.toBinaryTree()

        // When
        val result = invertTree(root)

        // Then
        val expected = listOf(4,7,2,9,6,3,1)
        assertEquals(expected, result.toList())
    }

    @Test
    fun test2() {
        // Given
        val list = listOf(2, 1, 3)
        val root = list.toBinaryTree()

        // When
        val result = invertTree(root)

        // Then
        val expected = listOf(2, 3, 1)
        assertEquals(expected, result.toList())
    }
}

class InvertBinaryTreeImpl: InvertBinaryTree() {

    /**
     * Initial thoughts
     *
     * To invert a binary tree
     *
     * [4,2,7,1,3,6,9]
     *
     *                  4
     *                2  7
     *              1 3 6 9
     * to
     * [4,7,2,9,6,3,1]
     *
     *                   4
     *                7    2
     *              9  6  3  1
     *
     * Complexity:
     * - Time: O(n) where n is the total number of nodes
     * - Space: O(h), where h is the height of the tree
     */
    override fun invertTree(root: TreeNode?): TreeNode? {
        // Corner case
        if (root == null) return null

        // Reverse left and right
        val tmp = root.left
        root.left = root.right
        root.right = tmp

        // Invert left
        invertTree(root.left)

        // Invert right
        invertTree(root.right)

        return root
    }

}
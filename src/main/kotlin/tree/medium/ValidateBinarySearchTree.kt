package main.kotlin.tree.medium

import main.kotlin.tree.TreeNode
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import tree.toBinaryTree

/**
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 *
 * A valid BST is defined as follows:
 *      The left subtree of a node contains only nodes with keys less than the node's key.
 *      The right subtree of a node contains only nodes with keys greater than the node's key.
 *      Both the left and right subtrees must also be binary search trees.
 * Example 1:
 *      Input: root = [2,1,3]
 *      Output: true
 *
 * Example 2:
 *      Input: root = [5,1,4,null,null,3,6]
 *      Output: false
 *      Explanation: The root node's value is 5 but its right child's value is 4.
 *
 * Constraints:
 *      The number of nodes in the tree is in the range [1, 10^4].
 *      -2^31 <= Node.val <= 2^31 - 1
 */
abstract class ValidateBinarySearchTree {

    abstract fun isValidBST(root: TreeNode?): Boolean

    @Test
    fun test1() {
        // Given
        val list = listOf(2, 1, 3)
        val root = list.toBinaryTree()

        // When
        val result = isValidBST(root)

        // Then
        assertTrue(result)
    }

    @Test
    fun test2() {
        // Given
        val list = listOf(5,1,4,null,null,3,6)
        val root = list.toBinaryTree()

        // When
        val result = isValidBST(root)

        // Then
        assertFalse(result)
    }

    @Test
    fun test3() {
        // Given
        val list = listOf(2, 2, 2)
        val root = list.toBinaryTree()

        // When
        val result = isValidBST(root)

        // Then
        assertFalse(result)
    }

    @Test
    fun test4() {
        // Given
        val list = listOf(2147483647)
        val root = list.toBinaryTree()

        // When
        val result = isValidBST(root)

        // Then
        assertTrue(result)
    }
}

class ValidateBinarySearchTreeImpl: ValidateBinarySearchTree() {

    /**
     * Initial thoughts
     *
     * A tree is valid if all the node on the left subtree is smaller than the node's value
     * A tree is valid if all the node on the right subtree is bigger than the node's value
     *
     * What happens with the equal cases?
     * - if all the nodes on the left subtree is equal than the node's value, is it valid?
     * - If all the nodes on the right subtree is equal than the node's value, is it valid?
     *
     * At the same time, we need to keep the value for the parent node
     *
     * Recursively:
     * A binary tree is valid if
     * - The value of the node on the left is smaller than the current node's value
     * - The value of the node on the right is bigger than the current node's value
     * - All the subtree on the left have a value between the min and current node's value
     * - All the subtree on the right have a value between the current node's value and the max value
     *
     * Initially the min value is Integer.min, the max value is Integer.max
     *
     * Corner case
     * - if the node is null, then it is valid
     *
     * Complexity:
     * - Time: O(n) where n is the number of nodes
     * - Space: O(h), where h is the depth of the tree
     */
    override fun isValidBST(root: TreeNode?): Boolean {
        // Corner case
        // This is not necessary because it is included in the next function
//        if (root == null) return true

        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE)
    }

    private fun isValidBST(node: TreeNode?, minValue: Long, maxValue: Long): Boolean {
        if (node == null) return true

        if (node.`val` <= minValue || node.`val` >= maxValue) {
            return false
        }

        return isValidBST(node.left, minValue, node.`val`.toLong()) &&
                isValidBST(node.right, node.`val`.toLong(), maxValue)
    }
}
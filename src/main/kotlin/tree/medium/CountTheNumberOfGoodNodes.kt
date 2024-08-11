package main.kotlin.tree.medium

import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * 3249 Count the Number of Good Nodes
 *
 * There is an undirected tree with n nodes labeled from 0 to n - 1, and rooted at node 0. You are
 * given a 2D integer array edges of length n - 1, where edges[i] = [ai, bi] indicates that there is
 * an edge between nodes ai and bi in the tree.
 *
 * A node is good if all the subtrees rooted at its children have the same size.
 * Return the number of good nodes in the given tree.
 * A subtree of treeName is a tree consisting of a node in treeName and all of its descendants.
 *
 * Example 1:
 *      Input: edges = [[0,1],[0,2],[1,3],[1,4],[2,5],[2,6]]
 *      Output: 7
 *      Explanation:
 *          All of the nodes of the given tree are good.
 *
 * Example 2:
 *      Input: edges = [[0,1],[1,2],[2,3],[3,4],[0,5],[1,6],[2,7],[3,8]]
 *      Output: 6
 *      Explanation:
 *          There are 6 good nodes in the given tree. They are colored in the image above.
 *
 * Example 3:
 *      Input: edges = [[0,1],[1,2],[1,3],[1,4],[0,5],[5,6],[6,7],[7,8],[0,9],[9,10],[9,12],[10,11]]
 *      Output: 12
 *      Explanation:
 *      All nodes except node 9 are good.
 *
 * Constraints:
 *      2 <= n <= 10^5
 *      edges.length == n - 1
 *      edges[i].length == 2
 *      0 <= ai, bi < n
 *  The input is generated such that edges represents a valid tree.
 *
 */
abstract class CountTheNumberOfGoodNodes {

    abstract fun countGoodNodes(edges: Array<IntArray>): Int

    @Test
    fun test1() {
        // Given
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(0, 2),
            intArrayOf(1, 3),
            intArrayOf(1, 4),
            intArrayOf(2, 5),
            intArrayOf(2, 6),
        )

        // When
        val result = countGoodNodes(edges)

        // Then
        assertEquals(7, result)
    }

    @Test
    fun test2() {
        // Given
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 2),
            intArrayOf(2, 3),
            intArrayOf(3, 4),
            intArrayOf(0, 5),
            intArrayOf(1, 6),
            intArrayOf(2, 7),
            intArrayOf(3, 8),
        )

        // When
        val result = countGoodNodes(edges)

        // Then
        assertEquals(6, result)
    }

    @Test
    fun test3() {
        // Given
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 2),
            intArrayOf(1, 3),
            intArrayOf(1, 4),
            intArrayOf(0, 5),
            intArrayOf(5, 6),
            intArrayOf(6, 7),
            intArrayOf(7, 8),
            intArrayOf(0, 9),
            intArrayOf(9, 10),
            intArrayOf(9, 12),
            intArrayOf(10,11),
        )

        // When
        val result = countGoodNodes(edges)

        // Then
        assertEquals(12, result)
    }

    @Test
    fun test4() {
        // Given
        val edges = arrayOf(
            intArrayOf(6, 0),
            intArrayOf(1, 0),
            intArrayOf(5, 1),
            intArrayOf(2, 5),
            intArrayOf(3, 1),
            intArrayOf(4, 3)
        )

        // When
        val result = countGoodNodes(edges)

        // Then
        assertEquals(6, result)
    }

}

class CountTheNumberOfGoodNodesImp: CountTheNumberOfGoodNodes() {

    /**
     * Initial thoughts
     *
     * A good node is if all its subtree have the same number of nodes.
     * A node could have 0, 1, 2 or more subtrees
     * - If a node has 0 subtree (leave), it is automatically good node
     * - If a node has 1 subtree, it is automatically a good node
     * - If a node has 2 or more subtree, it is a good node if
     * -    if all the nodes have the same number of nodes
     *
     * Do we have to build the tree first?
     * - The order of nodes in the list is random
     * - To check the child of each node, I need to check on every node O(n)
     * - To reduce the complexity, it is better build the tree first
     * Build the tree as map where
     * - key: Integer. The id of the node
     * - value: list of nodes (int) which are its child
     *
     * Helper: build another map where
     * - key: Integer. The id of the node
     * - value: Integer. The size of the node
     *
     * Correction: the input parameter is a list of edges. It does not
     * have any direction. So it could be [0, 1] or [1, 0]
     */
    private val tree = mutableMapOf<Int, MutableList<Int>>()

    override fun countGoodNodes(edges: Array<IntArray>): Int {
        // 1. Build the tree
        edges.forEach { edge ->
            val children0 = tree.getOrDefault(edge[0], mutableListOf())
            children0.add(edge[1])
            tree[edge[0]] = children0
            val children1 = tree.getOrDefault(edge[1], mutableListOf())
            children1.add(edge[0])
            tree[edge[1]] = children1
        }

        // 2. Build the size of its node
        val treeSizes = Array(edges.size + 1) { 0 }
        generateTreeSizes(tree, 0, -1, treeSizes)

        // 3. Check the good nodes
        var goodNodesNum = 0
        // Per each one of the nodes
        tree.forEach { entry ->
            var isGoodNode = true
            var nodeSize = -1
            entry.value.forEach {child ->
                if (treeSizes[child] < treeSizes[entry.key]) {
                    if (nodeSize == -1) {
                        nodeSize = treeSizes[child]
                    } else if (nodeSize != treeSizes[child]) {
                        isGoodNode = false
                        return@forEach
                    }
                }
            }

            if (isGoodNode) goodNodesNum +=1
        }

        return goodNodesNum
   }

    /**
     * Generates the tree size and fill the form
     *
     */
    private fun generateTreeSizes(tree: Map<Int, List<Int>>, currentNode: Int,
                                  parent: Int,
        treeSizes: Array<Int>): Int {
        var size = 1 // The size needs to be starting as 1 because of itself
        val children = tree[currentNode]
        children?.forEach { child ->
            if (child != parent) {
                size += generateTreeSizes(tree, child, currentNode, treeSizes)
            }
        }
        treeSizes[currentNode] = size
        return size
    }

}
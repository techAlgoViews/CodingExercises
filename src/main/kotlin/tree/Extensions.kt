package tree

import main.kotlin.tree.TreeNode
import org.junit.Assert.*
import org.junit.Test

/**
 * [5,1,4,null,null,3,6]
 */
fun List<Int?>.toBinaryTree(): TreeNode? {
    // The tree cannot start with null
    if (isEmpty() || get(0) == null) {
        return null
    }

    val queue = ArrayDeque<TreeNode>()
    val root = TreeNode(get(0)!!)
    queue.addLast(root)

    var i = 1

    while(queue.isNotEmpty() && i < size) {
        val head = queue.removeFirst()
        // Left node
        if (i < size && get(i) != null) {
            val leftNode = TreeNode(get(i)!!)
            head.left = leftNode
            queue.addLast(leftNode)
        }
        i++

        // Right node
        if (i < size && get(i) != null) {
            val rightNode = TreeNode(get(i)!!)
            head.right = rightNode
            queue.addLast(rightNode)
        }
        i++
    }

    return root
}

class TestToBinaryTree {

    @Test
    fun testSimple1() {
        // Given
        val list = listOf(1, 2, 3, 4, 5, 6, 7)

        // When
        val result = list.toBinaryTree()

        // Then
        assertNotNull(result)
        assertEquals(1, result?.`val`)
        assertEquals(2, result?.left?.`val`)
        assertEquals(3, result?.right?.`val`)
        assertEquals(4, result?.left?.left?.`val`)
        assertNull(result?.left?.left?.left)
        assertNull(result?.left?.left?.right)
        assertEquals(5, result?.left?.right?.`val`)
        assertNull(result?.left?.right?.left)
        assertNull(result?.left?.right?.right)
        assertEquals(6, result?.right?.left?.`val`)
        assertNull(result?.right?.left?.left)
        assertNull(result?.right?.left?.right)
        assertEquals(7, result?.right?.right?.`val`)
        assertNull(result?.right?.right?.left)
        assertNull(result?.right?.right?.right)
    }

    @Test
    fun testWithNull() {
        // Given
        val list = listOf(5,1,4,null,null,3,6)

        // When
        val result = list.toBinaryTree()

        // Then
        assertNotNull(result)
        assertEquals(5, result?.`val`)
        assertEquals(1, result?.left?.`val`)
        assertEquals(4, result?.right?.`val`)
        assertNull(result?.left?.left)
        assertNull(result?.left?.right)
        assertEquals(3, result?.right?.left?.`val`)
        assertNull(result?.right?.left?.left)
        assertNull(result?.right?.left?.right)
        assertEquals(6, result?.right?.right?.`val`)
        assertNull(result?.right?.right?.left)
        assertNull(result?.right?.right?.right)
    }
}

fun TreeNode?.toList(): List<Int?> {
    // Corner case
    if (this == null) return emptyList()

    // Init the variables
    val queue = ArrayDeque<TreeNode?>()
    val result = mutableListOf<Int?>()
    queue.addLast(this)

    // Loop
    while(queue.isNotEmpty()) {
        val node = queue.removeFirst()
        result.add(node?.`val`)

        node?.let {
            queue.addLast(it.left)
            queue.addLast(it.right)
        }
    }

    // Remove all the nulls from the end
    var lastNotNull = result.size - 1
    while (result[lastNotNull] == null) {
        lastNotNull--
    }

    return result.subList(0, lastNotNull + 1)
}

class ToListTest {
    @Test
    fun testCornerCaseNullList() {
        // Given
        val root: TreeNode? = null

        // When
        val result = root.toList()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun testSimple1() {
        // Given
        val list = listOf(1, 2, 3, 4, 5, 6, 7)
        val root = list.toBinaryTree()

        // When
        val result = root.toList()

        // Then
        assertEquals(list, result)
    }

    @Test
    fun testSimple2() {
        // Given
        val list = listOf(3,9,20,null,null,15,7)
        val root = list.toBinaryTree()

        // When
        val result = root.toList()

        // Then
        assertEquals(list, result)
    }

}
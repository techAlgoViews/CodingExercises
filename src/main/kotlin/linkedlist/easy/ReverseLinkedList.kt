package main.kotlin.linkedlist.easy

import main.kotlin.linkedlist.ListNode
import main.kotlin.linkedlist.checkValues
import main.kotlin.linkedlist.toLinkedList
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Reverse linked list
 *
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 *
 * Example 1:
 *      Input: head = [1,2,3,4,5]
 *      Output: [5,4,3,2,1]
 *
 * Example 2:
 *      Input: head = [1,2]
 *      Output: [2,1]
 *
 * Example 3:
 *      Input: head = []
 *      Output: []
 *
 * Constraints:
 *      The number of nodes in the list is the range [0, 5000].
 *      -5000 <= Node.val <= 5000
 */
abstract class ReverseLinkedList {

    abstract fun reverseList(head: ListNode?): ListNode?

    @Test
    fun testCornerCase_EmptyHead() {
        // Given
        val head = null

        // When
        val result = reverseList(head)

        // Then
        assertNull(result)
    }

    @Test
    fun testNormal1() {
        // Given
        val list = listOf(1, 2, 3, 4, 5)
        val head = list.toLinkedList()

        // When
        val result = reverseList(head)

        // Then
        assertTrue(result.checkValues(listOf(5, 4, 3, 2, 1)))
    }

    @Test
    fun testNormal2() {
        // Given
        val list = listOf(1, 2)
        val head = list.toLinkedList()

        // When
        val result = reverseList(head)

        // Then
        assertTrue(result.checkValues(listOf(2, 1)))
    }
}

class ReverseLinkedListImpl: ReverseLinkedList() {

    /**
     * Initial thoughts
     *
     * Because there are maximum of 5000 nodes, we cannot go to the end of the node and
     * then reverse. The operation needs to be done in place
     *
     * Now, we are going to need a couple of pointers
     * head
     *  |
     * [1] -> [2] -> [3] -> [4] -> [5]
     *  previous = null
     *  current = head
     *
     * Loop
     *  next = current.next
     *
     * head
     *  |
     * [1] -> [2] -> [3] -> [4] -> [5]
     *  ^      ^      ^
     *  p      c      n
     *
     * current.next = previous
     *
     * [1] <- [2]    [3] -> [4] -> [5]
     *  ^      ^      ^
     *  p      c      n
     *
     * previous = current
     *
     * [1] <- [2]    [3] -> [4] -> [5]
     *         ^      ^
     *        c,p     n
     *
     * current = next
     *
     * [1] <- [2]    [3] -> [4] -> [5]
     *         ^      ^
     *         p      c,n
     *
     * Stop when current = null
     *
     * 3.
     * The return previous
     */
    override fun reverseList(head: ListNode?): ListNode? {
        // 1. Init the value
        var previous: ListNode? = null
        var current = head

        // 2. Loop
        while (current != null) {
            val next = current.next
            current.next = previous
            previous = current
            current = next
        }

        // 3. Return the result
        return previous
     }
}
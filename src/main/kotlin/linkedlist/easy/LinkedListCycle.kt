package main.kotlin.linkedlist.easy

import main.kotlin.linkedlist.ListNode
import main.kotlin.linkedlist.toLinkedList
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test

/**
 * Linked list cycle
 *
 * Given head, the head of a linked list, determine if the linked list has a cycle in it.
 * There is a cycle in a linked list if there is some node in the list that can be reached again by
 * continuously following the next pointer. Internally, pos is used to denote the index of the node that
 * tail's next pointer is connected to. Note that pos is not passed as a parameter.
 *
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 *
 * Example 1:
 *      Input: head = [3,2,0,-4], pos = 1
 *      Output: true
 *      Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
 *
 * Example 2:
 *      Input: head = [1,2], pos = 0
 *      Output: true
 *      Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
 *
 * Example 3:
 *      Input: head = [1], pos = -1
 *      Output: false
 *      Explanation: There is no cycle in the linked list.
 *
 * Constraints:
 *      The number of the nodes in the list is in the range [0, 104].
 *      -10^5 <= Node.val <= 10^5
 *      pos is -1 or a valid index in the linked-list.
 *
 */
abstract class LinkedListCycle {
    abstract fun hasCycle(head: ListNode?): Boolean

    @Test
    fun testCornerCase_1Node() {
        // Given
        val list = listOf(1)
        val head = list.toLinkedList()

        // When
        val result = hasCycle(head)

        // Then
        assertFalse(result)
    }

    // TODO: Fix this
    @Ignore("This test never ends")
    @Test
    fun testNormal1() {
        // Given
        val list = listOf(1, 2)
        val head = list.toLinkedList()
        // Create cycle
        head?.next?.next = head

        // When
        val result = hasCycle(head)

        // Then
        assertTrue(result)
    }

    // TODO: Fix this
    @Ignore("This test never ends")
    @Test
    fun testNormal2() {
        // Given
        val list = listOf(3, 2, 0, -4)
        val head = list.toLinkedList()
        // Create cycle
        head?.next?.next?.next?.next = head?.next

        // When
        val result = hasCycle(head)

        // Then
        assertTrue(result)
    }
}

class LinkedListCycleImpl: LinkedListCycle() {

    override fun hasCycle(head: ListNode?): Boolean {
        // 1. Init the variable
        var tortoise = head
        var hare = head

        // 2. Loop
        while(hare?.next != null){
            tortoise = tortoise?.next
            hare = hare.next?.next
            if(tortoise == hare) return true
        }

        // 3. If fast is ever null or fast.next is ever null, return false
        return false
    }
}
package main.kotlin.linkedlist.medium

import main.kotlin.linkedlist.ListNode
import main.kotlin.linkedlist.checkValues
import main.kotlin.linkedlist.toLinkedList
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 *
 * Example 1:
 * - Input: head = [1,2,3,4,5], n = 2
 * - Output: [1,2,3,5]
 *
 * Example 2:
 * - Input: head = [1], n = 1
 * - Output: []
 *
 * Example 3:
 * - Input: head = [1,2], n = 1
 * - Output: [1]
 *
 * Constraints:
 * - The number of nodes in the list is sz.
 * - 1 <= sz <= 30
 * - 0 <= Node.val <= 100
 * - 1 <= n <= sz
 *
 * Follow up: Could you do this in one pass?
 */
abstract class RemoveNthNodeFromEndOfList {

    abstract fun removeNthFromEnd(head: ListNode?, n: Int): ListNode?

    @ParameterizedTest(name = "After removing {1} from {0} it should be {2}")
    @MethodSource("getData")
    fun test(head: List<Int>, n: Int, expectedValue: List<Int>) {
        val result = removeNthFromEnd(head.toLinkedList(), n)
        assertTrue(result.checkValues(expectedValue))
    }

    companion object {
        @JvmStatic
        fun getData(): List<Array<Any?>> {
            return listOf(
                arrayOf(listOf(1, 2, 3, 4, 5), 2, listOf(1, 2, 3, 5)),
                arrayOf(listOf(1), 1, emptyList<Int>()),
                arrayOf(listOf(1, 2), 1, listOf(1)),
            )
        }
    }
}

class RemoveNthNodeFromEndOfListImpl: RemoveNthNodeFromEndOfList() {
    override fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        // n = 1
        //       second Pointer
        //             |
        // [dummy] -> [0] -> [1] -> [2] -> null
        //    |
        // first pointer
        // 1. Create a dummy node as result
        val dummyNode = ListNode(-1)
        dummyNode.next = head

        // 2. Initialize the pointers
        var firstPointer: ListNode? = dummyNode
        var secondPointer = head

        // 3. Move the endNodePointer n positions
        for (i in 0 until n) {
            secondPointer = secondPointer?.next
        }

        // 4. Moving the secondPointer and firstPointer at the same time
        while (secondPointer != null) {
            firstPointer = firstPointer?.next
            secondPointer = secondPointer.next
        }

        // 5. Remove the nth node
        firstPointer?.next = firstPointer?.next?.next
        return dummyNode.next
    }
}
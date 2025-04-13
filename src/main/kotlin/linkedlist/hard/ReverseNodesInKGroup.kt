package main.kotlin.linkedlist.hard

import main.kotlin.linkedlist.ListNode
import main.kotlin.linkedlist.toLinkedList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

/**
 * Reverse Nodes in k-Group
 * Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified
 * list.
 * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a
 * multiple of k then left-out nodes, in the end, should remain as it is.
 * You may not alter the values in the list's nodes, only nodes themselves may be changed.
 *
 * Example 1:
 * - Input: head = [1,2,3,4,5], k = 2
 * - Output: [2,1,4,3,5]
 *
 * Example 2:
 * - Input: head = [1,2,3,4,5], k = 3
 * - Output: [3,2,1,4,5]
 *
 * Constraints:
 * - The number of nodes in the list is n.
 * - 1 <= k <= n <= 5000
 * - 0 <= Node.val <= 1000
 */
abstract class ReverseNodesInKGroup {

    abstract fun reverseKGroup(head: ListNode?, k: Int): ListNode?

    @ParameterizedTest(name = "The revers k group of {0} and k of {1} should be {2}")
    @ArgumentsSource(TestDataArgumentProvider::class)
    fun test(head: ListNode?, k: Int, expectedValue: ListNode?) {
        val result = reverseKGroup(head, k)
        Assertions.assertEquals(expectedValue, result)
    }

    class TestDataArgumentProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(listOf(1, 2, 3, 4, 5).toLinkedList(), 2, listOf(2, 1, 4, 3, 5).toLinkedList()),
                Arguments.of(listOf(1, 2, 3, 4, 5).toLinkedList(), 3, listOf(3, 2, 1, 4, 5).toLinkedList()),
                Arguments.of(listOf(1, 2, 3, 4, 5).toLinkedList(), 1, listOf(1, 2, 3, 4, 5).toLinkedList()),
                Arguments.of(listOf(1, 2).toLinkedList(), 2, listOf(2, 1).toLinkedList()),
                Arguments.of(listOf(1, 2, 3, 4, 5, 6).toLinkedList(), 2, listOf(2, 1, 4, 3, 6, 5).toLinkedList()),
                Arguments.of(emptyList<Int>().toLinkedList(), 2, emptyList<Int>().toLinkedList())
            )
        }
    }
}

class ReverseNodesInKGroupImpl : ReverseNodesInKGroup() {
    override fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        // 1. Create a dummy node
        val dummy = ListNode(0)
        dummy.next = head
        var groupPrev: ListNode? = dummy
        var currentNode = head
        var groupFinalNode = head

        // 2. Loop through
        while (groupFinalNode != null) {
            groupFinalNode = getGroupFinalNode(currentNode, k)
            // There is not more nodes
            if (groupFinalNode == null) {
                return dummy.next
            }

            val nextGroupStartingNode = groupFinalNode.next

            // Revert the list
            val reversedGroupNodes = reverseList(currentNode, nextGroupStartingNode)
            groupPrev?.next = reversedGroupNodes

            // Reset values
            groupPrev = currentNode
            groupFinalNode = nextGroupStartingNode
            currentNode = nextGroupStartingNode
        }

        // 3. Returning the result
        return dummy.next
    }

    private fun getGroupFinalNode(firstNode: ListNode?, k: Int): ListNode? {
        var increase = 0
        var groupFinalNode = firstNode
        while (increase < k - 1 && groupFinalNode != null) {
            groupFinalNode = groupFinalNode.next
            increase++
        }

        return groupFinalNode
    }

    private fun reverseList(
        head: ListNode?,
        finalNode: ListNode?
    ): ListNode? {
        // 1. Init the value
        var previous: ListNode? = finalNode
        var current = head

        // 2. Loop
        while (current != finalNode) {
            val tmp = current?.next
            current?.next = previous
            previous = current
            current = tmp
        }

        // 3. Return the result
        return previous
    }
}
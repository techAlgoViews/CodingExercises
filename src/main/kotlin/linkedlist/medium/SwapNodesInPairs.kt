package main.kotlin.linkedlist.medium

import main.kotlin.linkedlist.ListNode
import main.kotlin.linkedlist.toLinkedList
import main.kotlin.linkedlist.toList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

/**
 * Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without modifying
 * the values in the list's nodes (i.e., only nodes themselves may be changed.)
 *
 * Example 1:
 * - Input: head = [1,2,3,4]
 * - Output: [2,1,4,3]
 *
 * Example 2:
 * - Input: head = []
 * - Output: []
 *
 * Example 3:
 * - Input: head = [1]
 * - Output: [1]
 *
 * Example 4:
 * - Input: head = [1,2,3]
 * - Output: [2,1,3]
 *
 * Constraints:
 * - The number of nodes in the list is in the range [0, 100].
 * - 0 <= Node.val <= 100
 */
abstract class SwapNodesInPairs {

    abstract fun swapPairs(head: ListNode?): ListNode?

    @ParameterizedTest(name = "The swap pair of {0} should be {1}")
    @ArgumentsSource(TestDataArgumentProvider::class)
    fun test(head: ListNode?, expectedValue: ListNode?) {
        val result = swapPairs(head)
        Assertions.assertEquals(expectedValue.toList(), result.toList())
    }

    class TestDataArgumentProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(listOf(1, 2, 3, 4).toLinkedList(), listOf(2, 1, 4, 3).toLinkedList()),
                Arguments.of(emptyList<Int>().toLinkedList(), listOf<Int>().toLinkedList()),
                Arguments.of(listOf(1, 2, 3).toLinkedList(), listOf(2, 1, 3).toLinkedList()),
            )
        }
    }
}

class SwapNodesInPairsImpl: SwapNodesInPairs() {
    /**
     * Initial thoughts: This would be like the reverse nodes in K-group, but with k = 2
     * dummy -> [1] -> [2] -> [3] -> [4] -> [5] -> [6]
     *
     *                        curhead cur, tmp
     *                         |       |
     * dummy -> [2] -> [1] -> [3]    [4] -> [5] -> [6]
     *                         |             |
     *                         --------------
     *
     *                        curhead cur, tmp
     *                         |       |
     * dummy -> [2] -> [1] -> [3] <-  [4]   [5] -> [6]
     *                         |             |
     *                         --------------
     * dummy -> [2] -> [1] -> [4] -> [3] -> [6] -> [5]
     */
    override fun swapPairs(head: ListNode?): ListNode? {
        // Initial the value
        val dummyHead = ListNode(0)
        dummyHead.next = head
        var prevGroupTail:ListNode? = dummyHead
        var curGroupHead = head

        // Advance
        while (true) {
            if (curGroupHead?.next == null) {
                return dummyHead.next
            }
            val curGroupTail: ListNode = curGroupHead.next!!
            val nextGroupHead: ListNode? = curGroupTail.next

            // Reverse the pair
            // First node
            var curNode = curGroupHead
            val tmpNode = curNode.next
            curNode.next = nextGroupHead
            curNode = tmpNode

            // Second node
            curNode?.next = curGroupHead
            prevGroupTail?.next = curNode

            // Reset the values
            prevGroupTail = curGroupTail.next
            curGroupHead = nextGroupHead
        }
    }
}
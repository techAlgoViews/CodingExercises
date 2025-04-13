package main.kotlin.linkedlist.easy

import main.kotlin.linkedlist.ListNode
import main.kotlin.linkedlist.checkValues
import main.kotlin.linkedlist.toLinkedList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * Merge two sorted list
 *
 * You are given the heads of two sorted linked lists list1 and list2.
 * Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the
 * first two lists.
 * Return the head of the merged linked list.
 * Example 1:
 *      Input: list1 = [1,2,4], list2 = [1,3,4]
 *      Output: [1,1,2,3,4,4]
 *
 * Example 2:
 *      Input: list1 = [], list2 = []
 *      Output: []
 *
 * Example 3:
 *      Input: list1 = [], list2 = [0]
 *      Output: [0]
 *
 * Constraints:
 *      The number of nodes in both lists is in the range [0, 50].
 *      -100 <= Node.val <= 100
 *      Both list1 and list2 are sorted in non-decreasing order.
 */
abstract class MergeTwoSortedList {
    abstract fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode?

    @ParameterizedTest(name = "After merging {0} and {1} it should be {2}")
    @MethodSource("getData")
    fun test(list1: List<Int>, list2: List<Int>, expectedValue: List<Int>) {
        val result = mergeTwoLists(list1.toLinkedList(), list2.toLinkedList())
        Assertions.assertTrue(result.checkValues(expectedValue))
    }

    companion object {
        @JvmStatic
        fun getData(): List<Array<List<Int>>> {
            return listOf(
                arrayOf(listOf(1, 2, 4), listOf(1, 3, 4), listOf(1,1,2,3,4,4)),
                arrayOf(listOf(1, 2, 4, 5), listOf(1, 3, 4), listOf(1,1,2,3,4,4,5)),
                arrayOf(listOf(1, 2, 4), listOf(1, 3, 4, 5), listOf(1,1,2,3,4,4,5)),
                arrayOf(listOf(), listOf(), listOf()),
                arrayOf(listOf(), listOf(0, 1, 2, 3), listOf(0, 1, 2, 3)),
                arrayOf(listOf(0, 1, 2, 3), listOf(), listOf(0, 1, 2, 3)),
            )
        }
    }
}

class MergeTwoSortedListImpl : MergeTwoSortedList() {
    /*
     * currentList1
     *      |
     * L1: [1] -> [3]
     * L2: [2] -> [4] -> [5]
     *      |
     * currentList2
     *
     *          tail
     *            |
     * sentinel: [0] -> [1] -> [2] -> [3] -> [4] -> [5]
     */
    override fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        // 1. Init the values
        val sentinel = ListNode(0)
        var tail:ListNode? = sentinel

        // Since the parameter of the function cannot be updated, the algo uses
        // two extra parameters
        var currentList1 = list1
        var currentList2 = list2

        while (currentList1 != null && currentList2 != null) {
            if (currentList1.`val` < currentList2.`val`) {
                tail?.next = currentList1
                currentList1 = currentList1.next
            } else { // current list 1 val >= currentList2.val
                tail?.next = currentList2
                currentList2 = currentList2.next
            }

            // Update current
            tail = tail?.next
        }

        if (currentList1 == null) {
            tail?.next = currentList2
        }

        if (currentList2 == null) {
            tail?.next = currentList1
        }

        return sentinel.next
    }
}
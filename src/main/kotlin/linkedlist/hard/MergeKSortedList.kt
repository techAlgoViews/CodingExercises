package main.kotlin.linkedlist.hard

import main.kotlin.linkedlist.ListNode
import main.kotlin.linkedlist.checkValues
import main.kotlin.linkedlist.toLinkedList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * Merge k sorted list
 *
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 * Example 1:
 *  Input: lists = [[1,4,5],[1,3,4],[2,6]]
 *  Output: [1,1,2,3,4,4,5,6]
 *  Explanation: The linked-lists are:
 * [
 *      1->4->5,
 *      1->3->4,
 *      2->6
 * ]
 * merging them into one sorted list:
 * 1->1->2->3->4->4->5->6
 *
 * Example 2:
 *  Input: lists = []
 *  Output: []
 *
 * Example 3:
 *  Input: lists = [[]]
 *  Output: []
 *
 * Constraints:
 *  k == lists.length
 *  0 <= k <= 10^4
 *  0 <= lists[i].length <= 500
 *  -10^4 <= lists[i][j] <= 10^4
 *  lists[i] is sorted in ascending order.
 *  The sum of lists[i].length will not exceed 10^4.
 */
abstract class MergeKSortedList {
    abstract fun mergeKLists(lists: Array<ListNode?>): ListNode?

    @ParameterizedTest(name = "After merging {0} it should be {1}")
    @MethodSource("getData")
    fun test(kLists: Array<List<Int>>, expectedValue: List<Int>) {
        val result = mergeKLists(kLists.map { it.toLinkedList() }.toTypedArray())
        Assertions.assertTrue(result.checkValues(expectedValue))
    }

    companion object {
        @JvmStatic
        fun getData(): List<Array<*>> {
            return listOf(
                arrayOf(arrayOf(listOf(1, 4, 5), listOf(1, 3, 4), listOf(2, 6)), listOf(1,1,2,3,4,4,5,6)),
                arrayOf(arrayOf<List<Int>>(), listOf<Int>()),
                arrayOf(arrayOf(emptyList<Int>()), listOf<Int>()),
            )
        }
    }
}

class MergeKSortedListImpl : MergeKSortedList() {

    override fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) return null
        return divideAndConquer(lists, 0, lists.size - 1)
    }

    private fun divideAndConquer(lists: Array<ListNode?>, left: Int, right: Int): ListNode? {
        // 1. End condition
        if (left == right) return lists[left]

        // 2. Loop
        val mid = left + (right - left)/2
        val l1 = divideAndConquer(lists, left, mid)
        val l2 = divideAndConquer(lists, mid + 1, right)
        return mergeTwoLists(l1, l2)
    }

    private fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
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
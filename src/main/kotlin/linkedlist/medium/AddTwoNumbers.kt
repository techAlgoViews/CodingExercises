package main.kotlin.linkedlist.medium

import main.kotlin.linkedlist.ListNode
import main.kotlin.linkedlist.checkValues
import main.kotlin.linkedlist.toLinkedList
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Add two numbers
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in
 * reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as
 * a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example 1:
 *      Input: l1 = [2,4,3], l2 = [5,6,4]
 *      Output: [7,0,8]
 *      Explanation: 342 + 465 = 807.
 *
 * Example 2:
 *      Input: l1 = [0], l2 = [0]
 *      Output: [0]
 *
 * Example 3:
 *      Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 *      Output: [8,9,9,9,0,0,0,1]
 * Constraints:
 *      The number of nodes in each linked list is in the range [1, 100].
 *      0 <= Node.val <= 9
 *      It is guaranteed that the list represents a number that does not have leading zeros.
 */
abstract class AddTwoNumbers {

    abstract fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode?

    @Test
    fun test1() {
        // Given
        val l1 = listOf(2, 4, 3).toLinkedList()
        val l2 = listOf(5, 6, 4).toLinkedList()

        //  When
        val result = addTwoNumbers(l1, l2)

        // Then
        assertNotNull(result)
        result.checkValues(listOf(7, 0, 8))
    }

    @Test
    fun test2() {
        // Given
        val l1 = listOf(0).toLinkedList()
        val l2 = listOf(0).toLinkedList()

        //  When
        val result = addTwoNumbers(l1, l2)

        // Then
        assertNotNull(result)
        result.checkValues(listOf(0))
    }

    @Test
    fun test3() {
        // Given
        val l1 = listOf(9, 9, 9, 9, 9, 9, 9).toLinkedList()
        val l2 = listOf(9, 9, 9, 9).toLinkedList()

        //  When
        val result = addTwoNumbers(l1, l2)

        // Then
        assertNotNull(result)
        result.checkValues(listOf(8, 9, 9, 9, 0, 0, 0, 1))
    }
}

class AddTwoNumbersImpl: AddTwoNumbers() {

    /**
     * Initial thoughts
     *
     * Although the problem is hard, the algorithm is very simple
     * Iterate both linked list
     * -> Add the items
     * -> Add the carry from the previous position
     * -> Set the number
     * -> Pass the carry to the next number
     *
     * Once reach to the end, if there is carry, add 1 extra node of value 1 to the linked list
     */
    override fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        return addTwoNumbers(l1, l2, 0);
    }

    // Result is an list node which is not null but has empty values
    // Where you can add the result of next value
    private fun addTwoNumbers(l1: ListNode?, l2: ListNode?, carry: Int): ListNode? {
        // If both of them are null, then check the carry
        if (l1 == null && l2 == null) {
            return if (carry > 0) {
                ListNode(carry)
            } else {
                null
            }
        }
        val l1Value = l1?.`val` ?: 0
        val l2Value = l2?.`val` ?: 0
        val finalResult = l1Value + l2Value + carry
        val currentNode = ListNode(finalResult % 10)
        val l1Next = l1?.next
        val l2Next = l2?.next
        currentNode.next = addTwoNumbers(l1Next, l2Next, finalResult / 10)
        return currentNode
    }
}

class AddTwoNumbersOptim: AddTwoNumbers() {
    override fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var carry = 0
        var currentL1 = l1
        var currentL2 = l2
        val dummyHead = ListNode(0)
        var current = dummyHead

        while (currentL1 != null || currentL2 != null || carry != 0) {
            val x = currentL1?.`val` ?: 0
            val y = currentL2?.`val` ?: 0
            val sum = x + y + carry
            carry = sum / 10
            current.next = ListNode(sum % 10)
            current = current.next!!
            currentL1 = currentL1?.next
            currentL2 = currentL2?.next
        }

        return dummyHead.next
    }
}
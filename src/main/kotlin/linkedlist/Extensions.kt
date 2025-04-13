package main.kotlin.linkedlist

import org.junit.Assert.*
import org.junit.Test

fun List<Int>.toLinkedList(): ListNode? {
    if (isEmpty()) {
        return null
    }

    val sentinel = ListNode(0)
    var current = sentinel

    for (item in this) {
        val next = ListNode(item)
        current.next = next
        current = next
    }

    return sentinel.next
}

fun ListNode?.checkValues(list: List<Int>): Boolean {
    if (this == null) return list.isEmpty()

    var i = 0
    var current = this
    while (i < list.size && current != null) {
        if (current.`val` != list[i]) {
            return false
        }

        i++
        current = current.next
    }

    return current == null && i == list.size
}

fun ListNode?.toList(): List<Int> {
    val list = mutableListOf<Int>()
    var current = this
    while (current != null) {
        list.add(current.`val`)
        current = current.next
    }

    return list
}

class TestCheckValues {
    @Test
    fun testSimple() {
        // Given
        val list = listOf(1, 2, 3, 4)
        val head = list.toLinkedList()

        // When
        val result = head.checkValues(list)

        // Then
        assertTrue(result)
    }

    @Test
    fun testCornerCaseEmptyList() {
        // Given
        val list = emptyList<Int>()
        val head = list.toLinkedList()

        // When
        val result = head.checkValues(list)

        // Then
        assertTrue(result)
    }

    @Test
    fun testDifferentListAndNodes() {
        // Given
        val list = listOf(1, 2, 3, 4)
        val head = list.toLinkedList()
        val list2 = listOf(2, 3, 4, 5)

        // When
        val result = head.checkValues(list2)

        // Then
        assertFalse(result)
    }
}

class TestsToLinkedList {
    @Test
    fun testSimple() {
        // Given
        val list = listOf(1, 2, 3, 4)

        // When
        val result = list.toLinkedList()

        // Then
        assertNotNull(result)
        assertEquals(1, result?.`val`)
        assertEquals(2, result?.next?.`val`)
        assertEquals(3, result?.next?.next?.`val`)
        assertEquals(4, result?.next?.next?.next?.`val`)
    }

    @Test
    fun testEmptyLinkedList() {
        // Given
        val list = listOf<Int>()

        // When
        val result = list.toLinkedList()

        // Then
        assertNull(result)
    }
}
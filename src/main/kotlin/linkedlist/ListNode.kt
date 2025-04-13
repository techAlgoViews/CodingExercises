package main.kotlin.linkedlist

class ListNode(var `val`: Int) {
    var next: ListNode? = null

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ListNode) return false
        var current:ListNode? = this
        var dummyNode:ListNode? = other
        do {
            if (current?.`val` != dummyNode?.`val`) {
                return false
            }
            current = current?.next
            dummyNode = dummyNode?.next
        } while (current != null)

        return dummyNode == null
    }

    override fun hashCode(): Int {
        return toList().hashCode()
    }

    override fun toString(): String {
        return toList().joinToString(prefix = "(", postfix = ")", separator = ", ")
    }
}
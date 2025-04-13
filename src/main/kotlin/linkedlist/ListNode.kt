package main.kotlin.linkedlist

class ListNode(var `val`: Int) {
    var next: ListNode? = null

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ListNode) return false
        val current:ListNode = this
        val dummyNode:ListNode = other
        // Two list nodes are equals if their values are equal and they point to the same address
        return current.`val` == dummyNode.`val` && current.next === dummyNode.next
    }

    override fun hashCode(): Int {
        var hashCode = 1
        repeat(`val`) {
            hashCode *= 31
        }
        return hashCode
    }

    override fun toString(): String {
        val list = toList()
        val sb = StringBuilder()
        val repeatedListNode = repeatedListNode()
        sb.append(list.joinToString(prefix = "(", separator = " -> ") {
            "[$it]"
        })
        repeatedListNode?.let {
            sb.append(" -R-> [${it.`val`}])")
        } ?: sb.append(")")

        return sb.toString()
    }
}
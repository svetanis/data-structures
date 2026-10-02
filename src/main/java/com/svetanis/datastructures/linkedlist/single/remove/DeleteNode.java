package com.svetanis.datastructures.linkedlist.single.remove;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 237. Delete Node in a Linked List
//
// Given only one node of the list, never the last one, remove that node's value from the
// list. There is no access to the head and nothing is returned.
//
// A node can only be unlinked by changing the next of the node before it, and that node is
// out of reach. So the node takes over the value of the node after it, and that next node is
// unlinked instead. Read from the head, the list then looks as if the given node had been
// removed. LC 237 promises the node is not the last one, so a next node always exists.

public final class DeleteNode {
	// Time Complexity: O(1), two assignments
	// Space Complexity: O(1)

	public void deleteNode(ListNode node) {
		node.val = node.next.val; // COPY: take over the next node's value
		node.next = node.next.next; // SKIP: unlink the next node, whose value now lives here
	}

	public static void main(String[] args) {}
}

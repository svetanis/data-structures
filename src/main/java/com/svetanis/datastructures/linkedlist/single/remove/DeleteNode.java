package com.svetanis.datastructures.linkedlist.single.remove;

import com.svetanis.datastructures.linkedlist.single.Node;

// 237. Delete Node in a Linked List

public final class DeleteNode {
	// Time Complexity: O(1)
	// Space Complexity: O(1)

	public void deleteNode(Node node) {
		node.data = node.next.data;
		node.next = node.next.next;
	}

	public static void main(String[] args) {}
}

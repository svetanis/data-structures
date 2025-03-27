package com.svetanis.datastructures.linkedlist.single.remove;

import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 203. Remove Linked List Elements

public final class RemoveElements {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static Node removeElements(Node head, int val) {
		Node dummy = new Node(-1);
		dummy.next = head;
		Node prev = dummy;
		while (prev.next != null) {
			if (prev.next.data == val) {
				prev.next = prev.next.next;
			} else {
				prev = prev.next;
			}
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, 6, 3, 4, 5, 6 };
		Node head = Nodes.fromArray(a);
		Nodes.print(removeElements(head, 6)); // 1,2,3,4,5

		int[] a1 = { 7, 7, 7, 7 };
		Node head1 = Nodes.fromArray(a1);
		Nodes.print(removeElements(head1, 7)); // []
	}
}

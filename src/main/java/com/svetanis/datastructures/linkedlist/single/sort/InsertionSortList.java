package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// 147. Insertion Sort List

public final class InsertionSortList {
	// Time Complexity: O(n^2)

	public static Node sort(Node head) {
		if (head == null || head.next == null) {
			return head;
		}
		Node curr = head;
		Node dummy = new Node(0);
		while (curr != null) {
			Node prev = dummy;
			while (prev.next != null && prev.next.data <= curr.data) {
				prev = prev.next;
			}
			Node next = curr.next;
			curr.next = prev.next;
			prev.next = curr;
			curr = next;
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		int[] a = { 4, 2, 1, 3 };
		Node head = fromArray(a);
		print(sort(head));

		int[] a1 = { -1, 5, 3, 4, 0 };
		Node head1 = fromArray(a1);
		print(sort(head1));
	}
}

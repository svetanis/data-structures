package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// 2046. Sort Linked List Already Sorted Using Absolute Values

public final class SortLL {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static Node sort(Node head) {
		if (head == null || head.next == null) {
			return head;
		}
		Node prev = head;
		Node curr = head.next;
		while (curr != null) {
			if (curr.data < 0) {
				// store next node
				Node next = curr.next;
				// remove current node
				prev.next = next;
				// move current node to front
				curr.next = head;
				head = curr;
				// move to the next node
				curr = next;
			} else {
				prev = curr;
				curr = curr.next;
			}
		}
		return head;
	}

	public static void main(String[] args) {
		int[] a = { 0, 2, -5, 5, 10, -10 };
		Node head = fromArray(a);
		print(sort(head)); // -10, -5, 0, 2, 5, 10

		int[] a1 = { 0, 1, 2 };
		Node head1 = fromArray(a1);
		print(sort(head1)); // 0, 1, 2

		int[] a2 = { 1 };
		Node head2 = fromArray(a2);
		print(sort(head2)); // 1
	}
}

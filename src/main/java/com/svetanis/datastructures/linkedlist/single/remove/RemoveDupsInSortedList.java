package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 83. Remove Duplicates from Sorted List

public final class RemoveDupsInSortedList {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static Node remove(Node head) {
		Node curr = head;
		while (curr != null && curr.next != null) {
			if (curr.data == curr.next.data) {
				curr.next = curr.next.next;
			} else {
				curr = curr.next;
			}
		}
		return head;
	}

	public static void main(String[] args) {
		Node head = fromList(asList(1, 2, 3, 3, 4, 4, 5));
		print(remove(head)); // 1 2 5

		Node head1 = fromList(asList(1, 1, 1, 2, 3));
		print(remove(head1)); // 2 3
	}
}

package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 206. Reverse Linked List

// given the head of Singly LinkedList,
// reverse the SLL. return the new head
// of the reversed SLL

public final class ReverseDummy {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static Node reverse(Node head) {
		Node curr = head;
		Node dummy = new Node();
		while (curr != null) {
			Node next = curr.next;
			curr.next = dummy.next;
			dummy.next = curr;
			curr = next;
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		Node head = fromList(asList(85, 15, 4, 20));
		print(reverse(head));

		Node head1 = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head1));

		Node head2 = fromList(asList(1, 2));
		print(reverse(head2));
	}
}

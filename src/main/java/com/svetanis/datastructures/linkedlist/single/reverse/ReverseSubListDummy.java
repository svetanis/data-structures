package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 92. Reverse Linked List II

// Given the head of a LinkedList and two positions ‘p’ and ‘q’, 
// reverse the LinkedList from position ‘p’ to ‘q’.

public final class ReverseSubListDummy {
	// Time Complexity: O(n)

	public static Node reverse(Node head, int left, int right) {
		if (head.next == null || left == right) {
			return head;
		}
		// 1. pointer to track the node before the reversal section
		Node dummy = new Node(0, head);
		Node tail = dummy;
		for (int i = 0; i < left - 1; i++) {
			tail = tail.next;
		}
		Node subListTail = tail.next;

		// 2. reverse the subList between left and right
		Node prev = null;
		Node curr = subListTail;
		for (int i = 0; i < right - left + 1; i++) {
			Node next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}

		// 3. reconnect the reversed section back to the list
		tail.next = prev;
		subListTail.next = curr;
		return dummy.next;
	}

	public static void main(String[] args) {
		Node head = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head, 2, 4)); // 1 4 3 2 5

		Node head2 = fromList(asList(5));
		print(reverse(head2, 1, 1)); // 5
	}
}

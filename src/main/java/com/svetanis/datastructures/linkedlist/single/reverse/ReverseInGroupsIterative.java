package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// Given the head of a LinkedList and a number k, 
// reverse every k sized sub-list starting from the head.
// if in the end, left with a sub-list with less than k
// nodes, reverse it too

public final class ReverseInGroupsIterative {
	// Time Complexity: O(n)

	public static Node reverse(Node head, int k) {
		if (k <= 1 || head == null) {
			return head;
		}
		Node curr = head;
		Node prev = null;
		while (curr != null) {
			Node firstTail = prev;
			Node subListTail = curr;
			Node next = null;
			// reverse k nodes
			for (int i = 0; curr != null && i < k; i++) {
				next = curr.next;
				curr.next = prev;
				prev = curr;
				curr = next;
			}
			// connect with the previous part
			if (firstTail != null) {
				firstTail.next = prev;
			} else {
				head = prev;
			}
			// connect with the next part
			subListTail.next = curr;
			// prepare for next sublist
			prev = subListTail;
		}
		return head;
	}

	public static void main(String[] args) {
		Node head = fromList(newArrayList(1, 2, 3, 4, 5, 6, 7, 8));
		print(head);
		print(reverse(head, 3));
	}
}

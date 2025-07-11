package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 25. Reverse Nodes in k-Group

// Given the head of a LinkedList and a number k, 
// reverse every k sized sub-list starting from the head.
// if the number of nodes is not a multiple of k then
// left-out nodes, in the end, should remain as is

public final class ReverseInKGroupDummy2 {
	// Time Complexity: O(n)

	public static Node reverse(Node head, int k) {
		if (k <= 1 || head == null) {
			return head;
		}

		Node dummy = new Node(0);
		dummy.next = head;
		Node prev = dummy;
		Node curr = dummy;
		Node next = dummy;
		int count = 0;
		while (curr.next != null) {
			curr = curr.next;
			count++;
		}
		while (count >= k) {
			curr = prev.next;
			next = curr.next;
			for (int i = 1; i < k; i++) {
				curr.next = next.next;
				next.next = prev.next;
				prev.next = next;
				next = curr.next;
			}
			prev = curr;
			count -= k;
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		Node head = fromList(asList(1, 2, 3, 4, 5, 6, 7, 8));
		print(reverse(head, 3));

		Node head1 = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head1, 2)); // 2 1 4 3 5

		Node head2 = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head2, 3)); // 3 2 1 4 5
	}
}

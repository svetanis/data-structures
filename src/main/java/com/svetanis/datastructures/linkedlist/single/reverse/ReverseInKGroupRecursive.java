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

public final class ReverseInKGroupRecursive {
	// Time Complexity: O(n)

	public static Node reverse(Node head, int k) {
		if (k <= 1 || head == null) {
			return head;
		}
		int count = 0;
		Node curr = head;
		while (curr != null && count != k) {
			curr = curr.next;
			count++;
		}
		if (count == k) {
			curr = reverse(curr, k);
			while (count-- > 0) {
				Node next = head.next;
				head.next = curr;
				curr = head;
				head = next;
			}
			head = curr;
		}
		return head;
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

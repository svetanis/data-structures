package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 1669. Merge In Between Linked Lists

public final class MergeInBetweenLL {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static Node mergeInBetween(Node root1, int a, int b, Node root2) {
		Node head = root1;
		for (int i = 0; i < a - 1; i++) {
			head = head.next;
		}
		Node tail = root1;
		for (int i = 0; i < b; i++) {
			tail = tail.next;
		}
		head.next = root2;
		while (head.next != null) {
			head = head.next;
		}
		head.next = tail.next;
		tail.next = null;
		return root1;
	}

	public static void main(String[] args) {
		Node head1 = fromList(asList(10, 1, 13, 6, 9, 5));
		Node head2 = fromList(asList(1000000, 1000001, 1000002));
		print(mergeInBetween(head1, 3, 4, head2)); // 10,1,13,1000000,1000001,1000002,5
		Node head3 = fromList(asList(0, 1, 2, 3, 4, 5, 6));
		Node head4 = fromList(asList(1000000, 1000001, 1000002, 1000003, 1000004));
		print(mergeInBetween(head3, 2, 5, head4)); // 0,1,1000000,1000001,1000002,1000003,1000004,6
	}
}

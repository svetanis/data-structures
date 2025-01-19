package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// 21. Merge Two Sorted Lists

public final class MergeTwoSortedRecursive {
	// Time Complexity: O(n + m)
	// Space Complexity: O(1)

	public static Node merge(Node node1, Node node2) {
		if (node1 == null) {
			return node2;
		}
		if (node2 == null) {
			return node1;
		}

		if (node1.data < node2.data) {
			node1.next = merge(node1.next, node2);
			return node1;
		} else {
			node2.next = merge(node2.next, node1);
			return node2;
		}
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3, 5, 5, 6 };
		int[] a2 = { 4, 5, 6, 7, 8, 9, 10, 11 };
		test(a1, a2);

		int[] a3 = { 1, 2, 4 };
		int[] a4 = { 1, 3, 4 };
		test(a3, a4);
	}

	private static void test(int[] a1, int[] a2) {
		Node head1 = fromArray(a1);
		Node head2 = fromArray(a2);
		Node merged = merge(head1, head2);
		print(merged);
	}
}

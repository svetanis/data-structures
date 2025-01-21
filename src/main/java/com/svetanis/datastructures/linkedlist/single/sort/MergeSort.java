package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// 148. Sort List

public final class MergeSort {
	// Time Complexity: O(n log n)
	// Space Complexity: O(log n)

	public static Node sort(Node head) {
		if (head == null || head.next == null) {
			return head;
		}

		Node middle = middleNode(head);
		Node middleNext = middle.next;
		middle.next = null;

		// recursively sort and merge the sublists
		Node left = sort(head);
		Node right = sort(middleNext);
		return mergeDummy(left, right);
	}

	private static Node middleNode(Node head) {
		if (head == null) {
			return head;
		}
		Node slow = head;
		Node fast = head.next;
		while (fast != null) {
			fast = fast.next;
			if (fast != null) {
				slow = slow.next;
				fast = fast.next;
			}
		}
		return slow;
	}

	private static Node mergeDummy(Node left, Node right) {
		Node dummy = new Node();
		Node curr = dummy;
		while (left != null && right != null) {
			if (left.data <= right.data) {
				curr.next = left;
				left = left.next;
			} else {
				curr.next = right;
				right = right.next;
			}
			curr = curr.next;
		}
		curr.next = left == null ? right : left;
		return dummy.next;
	}

	private static Node merge(Node node1, Node node2) {
		Node merged = null;
		if (node1 == null) {
			return node2;
		}
		if (node2 == null) {
			return node1;
		}
		if (node1.data <= node2.data) {
			merged = node1;
			merged.next = merge(node1.next, node2);
		} else {
			merged = node2;
			merged.next = merge(node1, node2.next);
		}
		return merged;
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

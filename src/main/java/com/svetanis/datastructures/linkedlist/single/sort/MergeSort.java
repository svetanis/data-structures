package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.isNotNull;
import static com.svetanis.datastructures.linkedlist.single.Nodes.isNull;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class MergeSort {

	public static Node sort(Node head) {
		// base case: length 0 or 1

		if (head == null || head.next == null) {
			return head;
		}

		Node middle = middleNode(head);
		Node middleNext = middle.next;
		middle.next = null;

		// recursively sort and merge the sublists
		Node left = sort(head);
		Node right = sort(middleNext);
		return merge(left, right);
	}

	private static Node middleNode(Node head) {
		if (isNull(head)) {
			return head;
		}
		Node slow = head;
		Node fast = head.next;
		while (isNotNull(fast)) {
			fast = fast.next;
			if (isNotNull(fast)) {
				slow = slow.next;
				fast = fast.next;
			}
		}
		return slow;
	}

	public static Node merge(Node node1, Node node2) {
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
		int[] a = { 8, 5, 20, 50, 10, 30 };
		Node head = fromArray(a);
		print(head);
		Node sorted = sort(head);
		print(sorted);
	}
}

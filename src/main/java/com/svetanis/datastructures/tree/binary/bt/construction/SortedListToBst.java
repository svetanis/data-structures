package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 109. Convert Sorted List to Binary Search Tree

public final class SortedListToBst {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static Node sllToBst(com.svetanis.datastructures.linkedlist.single.Node head) {
		if (head == null) {
			return null;
		}
		com.svetanis.datastructures.linkedlist.single.Node mid = middle(head);
		Node root = new Node(mid.data);
		if (head == mid) {
			return root;
		}
		root.left = sllToBst(head);
		root.right = sllToBst(mid.next);
		return root;
	}

	private static com.svetanis.datastructures.linkedlist.single.Node middle(
			com.svetanis.datastructures.linkedlist.single.Node head) {
		com.svetanis.datastructures.linkedlist.single.Node prev = null;
		com.svetanis.datastructures.linkedlist.single.Node slow = head;
		com.svetanis.datastructures.linkedlist.single.Node fast = head;
		while (fast != null && fast.next != null) {
			prev = slow;
			slow = slow.next;
			fast = fast.next.next;
		}
		if (prev != null) {
			prev.next = null;
		}
		return slow;
	}

	public static void main(String[] args) {
		int[] a = { -10, -3, 0, 5, 9 };
		com.svetanis.datastructures.linkedlist.single.Node head = fromArray(a);
		sllToBst(head);
	}
}

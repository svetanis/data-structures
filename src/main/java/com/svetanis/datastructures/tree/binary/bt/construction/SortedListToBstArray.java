package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 109. Convert Sorted List to Binary Search Tree

public final class SortedListToBstArray {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static Node sllToBst(com.svetanis.datastructures.linkedlist.single.Node head) {
		if (head == null) {
			return null;
		}
		List<Integer> list = new ArrayList<>();
		for (com.svetanis.datastructures.linkedlist.single.Node curr = head; curr != null; curr = curr.next) {
			list.add(curr.data);
		}
		return arrayToBst(list, 0, list.size() - 1);
	}

	private static Node arrayToBst(List<Integer> list, int left, int right) {
		if (left > right) {
			return null;
		}
		int mid = left + (right - left) / 2;
		Node lst = arrayToBst(list, left, mid - 1);
		Node rst = arrayToBst(list, mid + 1, right);
		return new Node(list.get(mid), lst, rst);
	}

	public static void main(String[] args) {
		int[] a = { -10, -3, 0, 5, 9 };
		com.svetanis.datastructures.linkedlist.single.Node head = fromArray(a);
		sllToBst(head);
	}
}

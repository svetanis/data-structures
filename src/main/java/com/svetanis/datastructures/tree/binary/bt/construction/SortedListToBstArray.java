package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 109. Convert Sorted List to Binary Search Tree
//
// Input: the head of a singly linked list sorted in increasing order. On LeetCode: 0 to 2 * 10^4
// nodes, -10^5 <= val <= 10^5.
// Returns: the root of a height-balanced binary search tree holding the list's values (any such
// tree is accepted; this one takes the lower middle of an even slice). The input list is left
// unchanged.
//
// The one idea: a linked list can only be reached from its head, so finding its middle costs a
// walk. An array reaches any position at once. So copy the values into an array list first, then
// build the tree exactly as for a sorted array: the middle element is the root, the elements
// before it form the left subtree, the elements after it the right subtree.
//
// Time: O(n) -- one pass to copy, then one call per element.
// Space: O(n) -- the copied values, plus the n nodes built; the recursion is O(log n) deep.
//
// Siblings: SortedListToBst finds each middle with two pointers instead of copying (O(n log n)
// time, O(log n) extra space, and it cuts the input list apart); SortedArrayToBst108 is the same
// build on an int[].

public final class SortedListToBstArray {

	public static TreeNode sllToBst(com.svetanis.datastructures.linkedlist.single.ListNode head) {
		if (head == null) { // BASE: empty list, no node
			return null;
		}
		List<Integer> list = new ArrayList<>();
		for (com.svetanis.datastructures.linkedlist.single.ListNode curr = head; curr != null; curr = curr.next) {
			list.add(curr.val); // COPY: one value per node
		}
		return arrayToBst(list, 0, list.size() - 1);
	}

	private static TreeNode arrayToBst(List<Integer> list, int left, int right) {
		if (left > right) { // BASE: empty slice, no node
			return null;
		}
		int mid = left + (right - left) / 2; // ROOT: lower middle
		TreeNode lst = arrayToBst(list, left, mid - 1); // LEFT: the smaller values
		TreeNode rst = arrayToBst(list, mid + 1, right); // RIGHT: the larger values
		return new TreeNode(list.get(mid), lst, rst);
	}

	public static void main(String[] args) {
		int[] a = { -10, -3, 0, 5, 9 };
		com.svetanis.datastructures.linkedlist.single.ListNode head = fromArray(a);
		Nodes.preOrder(sllToBst(head)); // 0 -10 -3 5 9
		System.out.println();
	}
}

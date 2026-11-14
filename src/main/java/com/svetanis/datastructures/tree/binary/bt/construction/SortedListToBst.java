package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 109. Convert Sorted List to Binary Search Tree
//
// Input: the head of a singly linked list sorted in increasing order. On LeetCode: 0 to 2 * 10^4
// nodes, -10^5 <= val <= 10^5.
// Returns: the root of a height-balanced binary search tree holding the list's values (any such
// tree is accepted; this one takes the upper middle of an even list). The list is cut apart while
// the tree is built, so the input list is not usable afterwards.
//
// The one idea: in a search tree the root has everything smaller on its left and everything larger
// on its right, so the middle node of a sorted list can be the root, with the nodes before it as
// the left subtree and the nodes after it as the right subtree. The middle is found with two
// pointers, one stepping one node and one stepping two: when the fast one reaches the end, the slow
// one is in the middle. The node before it (prev) is remembered so the left half can be cut off
// there, and the same rule is applied to each half. No array copy is needed.
//
// Time: O(n log n) -- finding the middle walks half of the current list, and the lists at one level
// of the recursion add up to n nodes; there are log n levels.
// Space: O(log n) -- only the recursion, which is as deep as the balanced tree is tall.
//
// Siblings: SortedListToBstArray copies the list into an array first and picks middles by index
// (O(n) time, O(n) extra space); SortedArrayToBst108 is the same idea on an array.

public final class SortedListToBst {

	public static TreeNode sllToBst(com.svetanis.datastructures.linkedlist.single.ListNode head) {
		if (head == null) { // BASE: empty list, no node
			return null;
		}
		com.svetanis.datastructures.linkedlist.single.ListNode mid = middle(head); // ROOT: middle node
		TreeNode root = new TreeNode(mid.val);
		if (head == mid) { // single node left: no halves
			return root;
		}
		root.left = sllToBst(head); // LEFT: the nodes before mid (cut off by middle())
		root.right = sllToBst(mid.next); // RIGHT: the nodes after mid
		return root;
	}

	private static com.svetanis.datastructures.linkedlist.single.ListNode middle(
			com.svetanis.datastructures.linkedlist.single.ListNode head) {
		com.svetanis.datastructures.linkedlist.single.ListNode prev = null;
		com.svetanis.datastructures.linkedlist.single.ListNode slow = head;
		com.svetanis.datastructures.linkedlist.single.ListNode fast = head;
		while (fast != null && fast.next != null) { // WALK: slow one step, fast two
			prev = slow;
			slow = slow.next;
			fast = fast.next.next;
		}
		if (prev != null) { // CUT: the left half ends at prev
			prev.next = null;
		}
		return slow;
	}

	public static void main(String[] args) {
		int[] a = { -10, -3, 0, 5, 9 };
		com.svetanis.datastructures.linkedlist.single.ListNode head = fromArray(a);
		Nodes.preOrder(sllToBst(head)); // 0 -3 -10 9 5
		System.out.println();
	}
}

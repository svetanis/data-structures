package com.svetanis.datastructures.tree.binary.bst.inorder;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 426. Convert Binary Search Tree to Sorted Doubly Linked List
//
// Input: the root of a binary search tree (possibly empty). Returns the smallest node, after
// rewiring the tree in place into a circular doubly linked list in ascending order: left means
// "previous", right means "next", the smallest node's previous is the largest node and the
// largest node's next is the smallest. An empty tree returns null. On LeetCode: 0 to 2000 nodes,
// -1000 <= node.val <= 1000, all values unique.
//
// The one idea: the in-order walk visits the nodes in ascending order, so while walking, keep
// the node visited just before (prev). At each node, link prev.right to it and it.left to prev
// -- by then the walk has finished with prev's old left and right children, so reusing the two
// fields loses nothing. The first node visited has no prev: it is the head. After the walk,
// prev is the largest node; close the circle between it and the head.
//
// Time: O(n) -- each node is visited once and linked in constant time.
// Space: O(h) -- the recursion stack; no new nodes are created.

public final class ConvertBstToSortedDll {

	private TreeNode prev;
	private TreeNode head;

	public TreeNode bstToDll(TreeNode root) {
		if (root == null) {
			return null;
		}
		prev = null; // RESET: a second call must not link to the first tree's last node
		head = null;
		inOrder(root);
		prev.right = head; // CLOSE: largest -> smallest
		head.left = prev; // CLOSE: smallest <- largest
		return head;
	}

	private void inOrder(TreeNode root) {
		if (root == null) { // BASE: empty subtree
			return;
		}
		inOrder(root.left); // WALK: everything smaller first
		if (prev != null) {
			prev.right = root; // LINK: previous node -> this node
			root.left = prev; // LINK: previous node <- this node
		} else {
			head = root; // HEAD: the first node visited is the smallest
		}
		prev = root; // ADVANCE: this node is the previous one for the next visit
		inOrder(root.right);
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(2);
		root.right = new TreeNode(5);
		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(3);
		ConvertBstToSortedDll bts = new ConvertBstToSortedDll();
		TreeNode head = bts.bstToDll(root);
		TreeNode curr = head;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) { // forward from the head, once for each of the 5 nodes
			sb.append(curr.val).append(' ');
			curr = curr.right;
		}
		System.out.println(sb.toString().trim()); // 1 2 3 4 5
		System.out.println(curr == head); // true -- the fifth step lands back on the head
		System.out.println(head.left.val); // 5
	}
}

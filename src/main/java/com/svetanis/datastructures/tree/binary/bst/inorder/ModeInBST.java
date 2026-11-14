package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 501. Find Mode in Binary Search Tree
//
// Input: the root of a binary search tree that may hold equal values (a node's left subtree
// holds values <= it, its right subtree values >= it). Returns every mode -- every value that
// occurs the most times -- in any order. On LeetCode: 1 to 10^4 nodes, -10^5 <= node.val <= 10^5.
//
// The one idea: the in-order walk visits the values in ascending order, so equal values come
// out in one unbroken run; no map of counts is needed. Keep the node visited just before
// (prev) and count (the length of the current run). At each node, the run grows by one if the
// value equals prev's, and starts again at 1 if not. max is the longest run seen so far and
// list holds the values whose run reached max: a run that beats max empties the list, a run
// that ties it adds to it.
//
// The four fields live across the whole walk, so the first lines of duplicates() reset them;
// without that, a second call on the same object would continue the first tree's run and
// append to its list.
//
// Known limit: the recursion is as deep as the tree, so a one-sided chain of about six
// thousand nodes or more overflows the default stack (LC allows 10^4 nodes).
// ModeInBSTIterative is the same walk with an explicit stack.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack, plus the answer list (at most n values).
//
// Siblings: ModeInBSTIterative is this walk with an explicit stack. CountDuplicates counts
// the extra copies in a tree that always puts a duplicate on the right.

public final class ModeInBST {

	private int max; // the longest run of equal values seen so far
	private int count; // the length of the current run
	private TreeNode prev; // the node visited just before this one
	private List<Integer> list; // the values whose run reached max

	public int[] duplicates(TreeNode root) {
		this.max = 0;
		this.count = 0;
		this.prev = null;
		this.list = new ArrayList<>();
		dfs(root);
		int[] a = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			a[i] = list.get(i);
		}
		return a;
	}

	private void dfs(TreeNode root) {
		if (root == null) { // BASE: empty subtree
			return;
		}
		dfs(root.left); // WALK: smaller values first
		count = (prev != null && prev.val == root.val) ? count + 1 : 1; // RUN: extend it or start a new one
		if (count > max) { // NEW BEST: forget the shorter runs
			max = count;
			list = new ArrayList<>();
		}
		if (count == max) { // TIE OR BEST: this value is a mode so far
			list.add(root.val);
		}
		prev = root; // ADVANCE
		dfs(root.right);
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.right = newNode(2);
		root.right.left = newNode(2);
		ModeInBST m1 = new ModeInBST();
		System.out.println(Arrays.toString(m1.duplicates(root))); // [2]

		ModeInBST m2 = new ModeInBST();
		TreeNode root1 = newNode(0);
		System.out.println(Arrays.toString(m2.duplicates(root1))); // [0]

		ModeInBST m3 = new ModeInBST();
		TreeNode root2 = newNode(2);
		root2.left = newNode(1);
		System.out.println(Arrays.toString(m3.duplicates(root2))); // [1, 2]

		ModeInBST m4 = new ModeInBST();
		TreeNode root3 = newNode(1);
		root3.left = newNode(1);
		System.out.println(Arrays.toString(m4.duplicates(root3))); // [1]

		// the same object twice: each call starts from empty fields
		ModeInBST reused = new ModeInBST();
		TreeNode twos = newNode(1);
		twos.right = newNode(2);
		twos.right.right = newNode(2);
		System.out.println(Arrays.toString(reused.duplicates(twos))); // [2]
		System.out.println(Arrays.toString(reused.duplicates(twos))); // [2]

		// an empty tree has no values, so no mode
		System.out.println(Arrays.toString(new ModeInBST().duplicates(null))); // []
	}
}
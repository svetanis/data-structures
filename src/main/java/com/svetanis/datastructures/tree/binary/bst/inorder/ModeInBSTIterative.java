package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
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
// The walk is done with an explicit stack of the nodes whose left subtree is being explored,
// so a very tall tree cannot overflow the call stack. The run state lives in local variables,
// so the object holds no state between calls.
//
// Time: O(n) -- each node is pushed once and popped once.
// Space: O(h) -- the stack holds one root-to-node path at most, plus the answer list (at most
// n values).
//
// Siblings: ModeInBST is the same walk, recursive; its call stack overflows on a one-sided
// chain of about six thousand nodes, this one does not.

public final class ModeInBSTIterative {

	public int[] duplicates(TreeNode root) {
		int max = 0; // the longest run of equal values seen so far
		int count = 0; // the length of the current run
		TreeNode prev = null; // the node visited just before this one
		List<Integer> list = new ArrayList<>(); // the values whose run reached max
		Deque<TreeNode> stack = new ArrayDeque<>();
		TreeNode curr = root;
		while (!stack.isEmpty() || curr != null) {
			while (curr != null) { // DESCEND: push the path down the left side
				stack.push(curr);
				curr = curr.left;
			}
			curr = stack.pop(); // VISIT: the smallest value not yet visited
			count = (prev != null && prev.val == curr.val) ? count + 1 : 1; // RUN: extend it or start a new one
			if (count > max) { // NEW BEST: forget the shorter runs
				max = count;
				list = new ArrayList<>();
			}
			if (count == max) { // TIE OR BEST: this value is a mode so far
				list.add(curr.val);
			}
			prev = curr; // ADVANCE
			curr = curr.right; // WALK: then the larger values
		}
		int[] a = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			a[i] = list.get(i);
		}
		return a;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.right = newNode(2);
		root.right.left = newNode(2);
		ModeInBSTIterative m1 = new ModeInBSTIterative();
		System.out.println(Arrays.toString(m1.duplicates(root))); // [2]

		ModeInBSTIterative m2 = new ModeInBSTIterative();
		TreeNode root1 = newNode(0);
		System.out.println(Arrays.toString(m2.duplicates(root1))); // [0]

		ModeInBSTIterative m3 = new ModeInBSTIterative();
		TreeNode root2 = newNode(2);
		root2.left = newNode(1);
		System.out.println(Arrays.toString(m3.duplicates(root2))); // [1, 2]

		ModeInBSTIterative m4 = new ModeInBSTIterative();
		TreeNode root3 = newNode(1);
		root3.left = newNode(1);
		System.out.println(Arrays.toString(m4.duplicates(root3))); // [1]

		// the same object twice: each call gives the same answer
		ModeInBSTIterative reused = new ModeInBSTIterative();
		TreeNode twos = newNode(1);
		twos.right = newNode(2);
		twos.right.right = newNode(2);
		System.out.println(Arrays.toString(reused.duplicates(twos))); // [2]
		System.out.println(Arrays.toString(reused.duplicates(twos))); // [2]

		// an empty tree has no values, so no mode
		System.out.println(Arrays.toString(new ModeInBSTIterative().duplicates(null))); // []
	}
}
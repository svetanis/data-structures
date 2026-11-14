package com.svetanis.datastructures.tree.binary.bst.bounds;

import java.util.ArrayDeque;
import java.util.Deque;

// 255. Verify Preorder Sequence in Binary Search Tree
//
// Input: preorder -- an array of distinct values. On LeetCode: 1 <= preorder.length <= 10^4,
// 1 <= preorder[i] <= 10^4. Returns true when the array is the preorder walk (root, left subtree,
// right subtree) of some BST. The tree is never built.
//
// The one idea: walking a BST in preorder, each value either goes left (smaller than the one
// before it) or turns right (larger). The stack holds the values still waiting for a right child:
// the chain of nodes the walk went left from, the smallest on top. A value larger than the top
// turns right at some node on that chain: pop every entry smaller than it; the last one popped is
// the node whose right child this value is. From then on the walk is inside that node's right
// subtree, so every later value must be larger than it. prev holds that lower limit -- the last
// value popped -- and a value below prev can never appear, so the answer is false. The upper limits
// need no check: they are enforced by the pops, since a value larger than a node on the stack has
// already left that node's left subtree.
//
// Time: O(n) -- each value is pushed once and popped at most once.
// Space: O(n) -- the stack holds a falling chain, all n values when the walk only goes left.
//
// Siblings: VerifyPreorderSequenceBSTSubmit passes a range (min, max) down by recursion instead of
// keeping a stack.

public final class VerifyPreorderSequenceBST {

	public static boolean isPreorder(int[] preorder) {
		int prev = Integer.MIN_VALUE; // the lower limit; MIN_VALUE is safe, no value is below it
		Deque<Integer> dq = new ArrayDeque<>();
		for (int num : preorder) {
			if (prev > num) { // CHECK: below the limit set by an earlier right turn
				return false;
			}

			while (!dq.isEmpty() && dq.peek() < num) {
				prev = dq.pop(); // POP: a right turn at the last popped node, it is the new lower limit
			}

			dq.push(num); // PUSH: wait for this value's own children
		}
		return true;
	}

	public static void main(String[] args) {
		int[] a = { 5, 2, 1, 3, 6 };
		System.out.println(isPreorder(a)); // true
		int[] b = { 5, 2, 6, 1, 3 };
		System.out.println(isPreorder(b)); // false
		int[] c = { Integer.MIN_VALUE, Integer.MAX_VALUE };
		System.out.println(isPreorder(c)); // true
	}
}

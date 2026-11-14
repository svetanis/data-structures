package com.svetanis.datastructures.tree.binary.bst.bounds;

// 255. Verify Preorder Sequence in Binary Search Tree
//
// Input: preorder -- an array of distinct values. On LeetCode: 1 <= preorder.length <= 10^4,
// 1 <= preorder[i] <= 10^4. Returns true when the array is the preorder walk (root, left subtree,
// right subtree) of some BST. The tree is never built.
//
// The one idea: the walk is read left to right with one shared position, index. A subtree takes
// the next value as its root only if the value lies inside the open range (min, max) that subtree
// may hold; if it does not, the value is not this subtree's -- the subtree is empty and the value is
// left for a subtree higher up. The root is followed by the left subtree, whose range is (min,
// root), then the right subtree, range (root, max). The array is a valid preorder walk exactly
// when this reading consumes every value: reaching the end of the array is the success.
//
// A null bound means "no limit". Integer.MIN_VALUE and MAX_VALUE are not used as the limits,
// because a value equal to its bound is out of range and a sequence holding either extreme would
// be refused. LC 255 keeps values to 1..10^4, but the stack sibling accepts { MIN_VALUE, MAX_VALUE }
// and so does this file.
//
// Time: O(n) -- each value is read once and tested against one range.
// Space: O(h) -- the recursion is as deep as the tree the values describe is tall, h <= n.
//
// Siblings: VerifyPreorderSequenceBST keeps a stack of the nodes waiting for a right child and one
// lower limit, with no recursion and no depth limit.

public final class VerifyPreorderSequenceBSTSubmit {

	private int index;
	private int[] preorder;

	public boolean isPreorder(int[] preorder) {
		this.index = 0; // RESET: a second call on the same object starts at the first value
		this.preorder = preorder;
		return dfs(null, null);
	}

	private boolean dfs(Integer min, Integer max) {
		if (index == preorder.length) {
			return true; // BASE: every value was placed
		}
		int root = preorder[index];
		// a value out of range means "this subtree is empty", not "the sequence is bad"; the
		// answer is whether the walk ever consumed the whole array, which reaching the base reports
		if (min != null && root <= min || max != null && root >= max) {
			return false; // SKIP: leave the value for a subtree higher up
		}
		index += 1; // TAKE: the value is this subtree's root
		boolean left = dfs(min, root); // LEFT: values below the root
		boolean right = dfs(root, max); // RIGHT: values above the root
		return left || right;
	}

	public static void main(String[] args) {
		VerifyPreorderSequenceBSTSubmit vps = new VerifyPreorderSequenceBSTSubmit();
		int[] a = { 5, 2, 1, 3, 6 };
		System.out.println(vps.isPreorder(a)); // true

		int[] b = { 5, 2, 6, 1, 3 };
		System.out.println(vps.isPreorder(b)); // false

		// both extremes of the int range
		int[] c = { Integer.MIN_VALUE, Integer.MAX_VALUE };
		System.out.println(vps.isPreorder(c)); // true
	}
}

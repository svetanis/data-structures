package com.svetanis.datastructures.tree.binary.bst.bounds;

// 255. Verify Preorder Sequence in Binary Search Tree

public final class VerifyPreorderSequenceBSTSubmit {
	// Time complexity O(n);
	// Space complexity: O(n)

	private int index;
	private int[] preorder;

	public boolean isPreorder(int[] preorder) {
		this.index = 0;
		this.preorder = preorder;
		// null for unbounded, not Integer.MIN_VALUE / MAX_VALUE: a
		// sequence containing either one has that value compared against
		// a bound equal to itself and is rejected. the monotonic-stack
		// sibling accepts { MIN_VALUE, MAX_VALUE } and so should this
		return dfs(null, null);
	}

	private boolean dfs(Integer min, Integer max) {
		if (index == preorder.length) {
			return true;
		}
		int root = preorder[index];
		// out of bounds means "this subtree is empty", not "the sequence
		// is bad", so the answer is whether the walk ever consumed the
		// whole array -- which is what reaching the base case reports
		if (min != null && root <= min || max != null && root >= max) {
			return false;
		}
		index += 1;
		boolean left = dfs(min, root);
		boolean right = dfs(root, max);
		return left || right;
	}

	public static void main(String[] args) {
		VerifyPreorderSequenceBSTSubmit vps = new VerifyPreorderSequenceBSTSubmit();
		int[] a = { 5, 2, 1, 3, 6 };
		System.out.println(vps.isPreorder(a)); // true

		int[] b = { 5, 2, 6, 1, 3 };
		System.out.println(vps.isPreorder(b)); // false

		// both extremes of the int range. against MIN_VALUE / MAX_VALUE
		// bounds this printed false
		int[] c = { Integer.MIN_VALUE, Integer.MAX_VALUE };
		System.out.println(vps.isPreorder(c)); // true
	}
}

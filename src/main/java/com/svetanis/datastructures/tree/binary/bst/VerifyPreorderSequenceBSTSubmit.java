package com.svetanis.datastructures.tree.binary.bst;

// 255. Verify Preorder Sequence in Binary Search Tree

public final class VerifyPreorderSequenceBSTSubmit {
	// Time complexity O(n);
	// Space complexity: O(n)

	private int index;
	private int[] preorder;

	public boolean isPreorder(int[] preorder) {
		this.index = 0;
		this.preorder = preorder;
		return dfs(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	private boolean dfs(int min, int max) {
		if (index == preorder.length) {
			return true;
		}
		int root = preorder[index];
		if (root <= min || root >= max) {
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
		System.out.println(vps.isPreorder(a));
	}
}

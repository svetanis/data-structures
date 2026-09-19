package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1373. Maximum Sum BST in Binary Tree

// the same post-order report as bst/bounds/LargestBstInBt.java (333),
// with a fourth field for the subtree sum. that file uses null for
// "no bound on this side" because 333 allows every int as a value;
// here the values are -4e4..4e4, so INF is 26,000x outside the range
// and can be an ordinary number.

// the sum cannot overflow either: 4e4 nodes at 4e4 each is 1.6e9,
// against Integer.MAX_VALUE of 2.1e9. that headroom is only 1.3x, so
// a long is what to reach for if the constraints ever widen.

// an empty BST scores 0, which is why maxSum starts at 0 and is only
// ever raised -- a tree of all negative values returns 0, not its
// largest single node.

public final class MaxSumBST {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private static final int INF = 1 << 30;
	
	private int maxSum;

	public int maxSum(Node root) {
		this.maxSum = 0;
		dfs(root);
		return maxSum;
	}

	private int[] dfs(Node node) {
		if (node == null) {
			return new int[] { 1, INF, -INF, 0 };
		}
		int curr = node.data;
		int[] left = dfs(node.left);
		int[] right = dfs(node.right);
		boolean valid = curr > left[2] && curr < right[1];
		if (valid && left[0] == 1 && right[0] == 1) {
			int sum = curr + left[3] + right[3];
			maxSum = Math.max(maxSum, sum);
			int minVal = Math.min(left[1], curr);
			int maxVal = Math.max(right[2], curr);
			return new int[] { 1, minVal, maxVal, sum };
		}
		return new int[] { 0, 0, 0, 0 };
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(4);
		root.right = newNode(3);
		root.left.left = newNode(2);
		root.left.right = newNode(4);
		root.right.left = newNode(2);
		root.right.right = newNode(5);
		root.right.right.left = newNode(4);
		root.right.right.right = newNode(6);
		MaxSumBST mst = new MaxSumBST();
		System.out.println(mst.maxSum(root)); // 20

		Node root2 = newNode(4);
		root2.left = newNode(3);
		root2.left.left = newNode(1);
		root2.left.right = newNode(2);
		MaxSumBST mst2 = new MaxSumBST();
		System.out.println(mst2.maxSum(root2)); // 2
	}
}

package com.svetanis.datastructures.tree.binary.bt.dfs;

import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 298. Binary Tree Longest Consecutive Sequence
// given BT, find the length of the longest 
// consecutive sequence path within it. 
// a consecutive sequence path is defined as a path
// in which adjacent nodes in the path have values
// that differ by exactly one. the path doesn't
// need to begin at the root of the tree; it can
// start at any node. 

public final class LongestConsecutiveSequenceLen {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	public static int maxLen(Node root) {
		return maxLen(root, null, 0);
	}

	private static int maxLen(Node root, Node prev, int len) {
		if (root == null) {
			return 0;
		}
		boolean consecutive = prev != null && root.data == prev.data + 1;
		len = consecutive ? len + 1 : 1;
		int left = maxLen(root.left, root, len);
		int right = maxLen(root.right, root, len);
		return max(len, max(left, right));
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.right = new Node(3);
		root.right.left = new Node(2);
		root.right.right = new Node(4);
		root.right.right.right = new Node(5);
		System.out.println(maxLen(root));
	}
}

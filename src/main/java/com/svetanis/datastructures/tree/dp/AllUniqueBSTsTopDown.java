package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.preOrder;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 95. Unique Binary Search Trees II

// given a number n find all
// structurally unique BSTs
// that can store values 1 to n

public final class AllUniqueBSTsTopDown {
	// Time Complexity: O(n * 2^n)
	// Space Complexity: O(n * 2^n)

	public static List<Node> construct(int n) {
		if (n <= 0) {
			return new ArrayList<>();
		}
		List<Node>[][] dp = new ArrayList[n + 1][n + 1];
		return construct(1, n, dp);
	}

	private static List<Node> construct(int start, int end, List<Node>[][] dp) {
		List<Node> list = new ArrayList<>();
		// if start > end then subtree is empty
		if (start > end) {
			list.add(null);
			return list;
		}
		if (dp[start][end] != null) {
			return dp[start][end];
		}
		for (int i = start; i <= end; i++) {
			List<Node> left = construct(start, i - 1, dp);
			List<Node> right = construct(i + 1, end, dp);
			// loop through all left and right subtrees
			// and connect them to i-th root
			for (int j = 0; j < left.size(); j++) {
				for (int k = 0; k < right.size(); k++) {
					Node node = new Node(i); // make value i as root
					node.left = left.get(j); // connect left subtree
					node.right = right.get(k); // connect right subtree
					list.add(node);
				}
			}
		}
		return dp[start][end] = list;
	}

	public static void main(String[] args) {
		// construct all possible BTs
		List<Node> trees = construct(3);
		for (int i = 0; i < trees.size(); i++) {
			preOrder(trees.get(i));
			System.out.println();
		}
	}
}

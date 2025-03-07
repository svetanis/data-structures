package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 250. Count Univalue Subtrees

public final class CountUnivalueSubtrees {
	// Time Complexity: O(n)
	
	private int count = 0;

	public int countSubtrees(Node root) {
		dfs(root);
		return count;
	}

	private boolean dfs(Node root) {
		if (root == null) {
			return true;
		}
		boolean isLeft = dfs(root.left);
		boolean isRight = dfs(root.right);
		if (!isLeft || !isRight) {
			return false;
		}
		int left = root.left == null ? root.data : root.left.data;
		int right = root.right == null ? root.data : root.right.data;

		if (left == right && right == root.data) {
			count++;
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(1);
		root.right = newNode(1);
		root.left.left = newNode(1);
		root.right.left = newNode(1);
		root.right.right = newNode(1);
		CountUnivalueSubtrees cus = new CountUnivalueSubtrees();
		System.out.println(cus.countSubtrees(root));
	}
}

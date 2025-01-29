package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.HashSet;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 653. Two Sum IV - Input is a BST

public final class TwoSumBst {
	// Time Complexity: O(n)

	public static boolean twoSum(Node root, int target) {
		Set<Integer> set = new HashSet<>();
		return dfs(root, target, set);
	}

	private static boolean dfs(Node root, int target, Set<Integer> set) {
		if (root == null) {
			return false;
		}
		if (set.contains(target - root.data)) {
			return true;
		}
		set.add(root.data);
		boolean left = dfs(root.left, target, set);
		boolean right = dfs(root.right, target, set);
		return left || right;
	}

	public static void main(String[] args) {
		Node root = newNode(5);
		root.left = newNode(3);
		root.right = newNode(6);
		root.left.left = newNode(2);
		root.left.right = newNode(4);
		root.right.right = newNode(7);
		System.out.println(twoSum(root, 9));
	}
}
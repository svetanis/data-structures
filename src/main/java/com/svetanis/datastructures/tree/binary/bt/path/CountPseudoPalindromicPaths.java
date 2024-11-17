package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1457. Pseudo-Palindromic Paths in a Binary Tree

public final class CountPseudoPalindromicPaths {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	public static int pathCount(Node root) {
		int[] counter = new int[10];
		return pathCount(root, counter);
	}

	private static int pathCount(Node node, int[] counter) {
		if (isNull(node)) {
			return 0;
		}
		// add current node to the path
		counter[node.data]++;
		int count = 0;
		if (isLeaf(node)) {
			if (pseudoPalindrome(counter)) {
				count++;
			}
		} else {
			count += pathCount(node.left, counter);
			count += pathCount(node.right, counter);
		}
		// backtrack: remove the current node from the path
		// while going up the recursive call stack
		counter[node.data]--;
		return count;
	}

	private static boolean pseudoPalindrome(int[] counter) {
		int odd = 0;
		for (int i = 1; i <= 9; i++) {
			if (counter[i] % 2 == 1) {
				odd++;
			}
		}
		return odd == 0 || odd == 1;
	}

	public static void main(String[] args) {
		Node root = newNode(2);
		root.left = newNode(3);
		root.right = newNode(1);
		root.left.left = newNode(3);
		root.left.right = newNode(1);
		root.right.right = newNode(1);
		System.out.println(pathCount(root)); // 2

		Node root2 = newNode(2);
		root2.left = newNode(1);
		root2.right = newNode(1);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.left.right.right = newNode(1);
		System.out.println(pathCount(root2)); // 1

	}
}

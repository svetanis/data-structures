package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 337. House Robber III

// The houses form a binary tree. 
// If the root is robbed, 
// its left and right can not be robbed. 

public final class HouseThief {
	// Time Complexity: O(n)

	public static int maxProfit(Node root) {
		if (root == null) {
			return 0;
		}
		Profit p = profit(root);
		return max(p.incl, p.excl);
	}

	private static Profit profit(Node node) {
		if (node == null) {
			return new Profit(0, 0);
		}
		Profit left = profit(node.left);
		Profit right = profit(node.right);
		int incl = node.data + left.excl + right.excl;
		int excl = max(left.excl, left.incl) + max(right.excl, right.incl);
		return new Profit(incl, excl);
	}

	public static void main(String[] args) {
		Node root1 = newNode(3);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.right = newNode(3);
		root1.right.right = newNode(1);
		System.out.println(maxProfit(root1)); // 7

		Node root2 = newNode(3);
		root2.left = newNode(4);
		root2.right = newNode(5);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.right = newNode(5);
		root2.right.right = newNode(1);
		System.out.println(maxProfit(root2)); // 9

	}

	private static class Profit {
		int incl;
		int excl;

		public Profit(int incl, int excl) {
			this.incl = incl;
			this.excl = excl;
		}
	}
}

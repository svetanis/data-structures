package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.IdentityHashMap;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 337. House Robber III

// The houses form a binary tree. 
// If the root is robbed, 
// its left and right can not be robbed. 

public final class HouseThiefTopDown {
	// Time Complexity: O(n)

	// The memo must be keyed on node IDENTITY, not on Node.equals.
	// Node overrides equals and hashCode by value -- it compares
	// data, left and right, and caches its hash in the constructor,
	// before any child has been attached. In a HashMap every node
	// built by newNode(v) with the same v therefore lands in one
	// bucket, and each lookup compares whole subtrees: a 4000-node
	// chain took 140 seconds. IdentityHashMap compares with ==.

	public static int maxProfit(Node root) {
		if (root == null) {
			return 0;
		}
		Map<Node, Integer> map = new IdentityHashMap<>();
		return dfs(root, map);
	}

	private static int dfs(Node node, Map<Node, Integer> map) {
		if (node == null) {
			return 0;
		}
		if (map.containsKey(node)) {
			return map.get(node);
		}
		// 1. rot this house + grandchildren
		int profit = node.data;
		if (node.left != null) {
			profit += dfs(node.left.left, map) + dfs(node.left.right, map);
		}
		if (node.right != null) {
			profit += dfs(node.right.left, map) + dfs(node.right.right, map);
		}
		// 2. don't rob this house, go to his children
		int noRobbing = dfs(node.left, map) + dfs(node.right, map);
		int max = Math.max(profit, noRobbing);
		map.put(node, max);
		return max;
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

		// a chain of equal values: every node hashes to the same bucket
		// under Node.hashCode, so a HashMap memo takes about 140 seconds
		// here and an IdentityHashMap takes a millisecond
		System.out.println(maxProfit(chain(4000))); // 2000
	}

	private static Node chain(int length) {
		Node head = newNode(1);
		Node last = head;
		for (int i = 1; i < length; i++) {
			last.left = newNode(1);
			last = last.left;
		}
		return head;
	}
}

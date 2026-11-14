package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.IdentityHashMap;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 337. House Robber III
//
// Input: the root of a binary tree; each node is a house holding TreeNode.val money. Robbing two
// houses joined by an edge -- a parent and its child -- trips the alarm. Returns the most money
// that can be robbed. On LeetCode: 1 <= nodes <= 10^4, 0 <= Node.val <= 10^4.
//
// The one idea: dfs(node) is the most money from the subtree under node. Either rob the node,
// which rules out its two children and so continues at its four grandchildren, or skip the node
// and take the best of each child's subtree. The answer is the better of the two. A node is asked
// for from its parent and from its grandparent, so the answer for each node is kept in a map and
// read back the second time.
//
// Time: O(n) -- each node is worked out once, with constant work; every later ask is a lookup.
// Space: O(n) -- the map holds one entry per node; the recursion stack adds O(h).
//
// Siblings: HouseThiefRecursive is the same recursion with no map, exponential in the worst case.
// HouseThief carries a pair per subtree (maxProfit as an object, maxProfitArray as an int[2])
// instead of a map.

public final class HouseThiefTopDown {

	// The memo is keyed on node IDENTITY: two houses holding the same
	// money are still two houses. TreeNode keeps Object's equals and
	// hashCode, so a HashMap would compare with == as well;
	// IdentityHashMap puts that choice in the type.

	public static int maxProfit(TreeNode root) {
		if (root == null) {
			return 0;
		}
		Map<TreeNode, Integer> map = new IdentityHashMap<>(); // MEMO: node -> best for its subtree
		return dfs(root, map);
	}

	private static int dfs(TreeNode node, Map<TreeNode, Integer> map) {
		if (node == null) {
			return 0; // BASE: no house
		}
		if (map.containsKey(node)) {
			return map.get(node); // CACHED: worked out from the parent or the grandparent
		}
		// 1. rob this house + grandchildren
		int profit = node.val;
		if (node.left != null) {
			profit += dfs(node.left.left, map) + dfs(node.left.right, map); // ROB: skip the left child
		}
		if (node.right != null) {
			profit += dfs(node.right.left, map) + dfs(node.right.right, map); // ROB: skip the right child
		}
		// 2. don't rob this house, go to his children
		int noRobbing = dfs(node.left, map) + dfs(node.right, map); // SKIP: the children are free
		int max = Math.max(profit, noRobbing);
		map.put(node, max); // STORE: for the next ask
		return max;
	}

	public static void main(String[] args) {
		TreeNode root1 = newNode(3);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.right = newNode(3);
		root1.right.right = newNode(1);
		System.out.println(maxProfit(root1)); // 7

		TreeNode root2 = newNode(3);
		root2.left = newNode(4);
		root2.right = newNode(5);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.right = newNode(5);
		root2.right.right = newNode(1);
		System.out.println(maxProfit(root2)); // 9

		// a chain of equal values: every node is its own memo entry, and
		// the memo does constant work per node
		System.out.println(maxProfit(chain(4000))); // 2000
	}

	private static TreeNode chain(int length) {
		TreeNode head = newNode(1);
		TreeNode last = head;
		for (int i = 1; i < length; i++) {
			last.left = newNode(1);
			last = last.left;
		}
		return head;
	}
}

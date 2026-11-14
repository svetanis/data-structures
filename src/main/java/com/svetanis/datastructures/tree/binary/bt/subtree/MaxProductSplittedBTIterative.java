package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1339. Maximum Product of Splitted Binary Tree
//
// Input: the root of a binary tree. Removing one edge splits the tree into two trees. Returns the
// largest possible product of the two trees' value sums, modulo 10^9 + 7 (the maximum is found
// before the modulo is taken). On LeetCode: 2 <= number of nodes <= 5 * 10^4,
// 1 <= Node.val <= 10^4.
//
// The one idea: cutting the edge above a node splits off exactly that node's subtree, so there is
// one candidate split per node: its subtree sum s against the rest, total - s. A node's sum is its
// value plus its children's sums, so the sums must be finished children first. A walk that takes
// each node before its children lists every parent before its children; going through that list
// backwards finishes every child before its parent. The sum of the root is the total, and then
// every node's product s * (total - s) is tried. Products reach about 6 * 10^16, so they are kept
// in a long and the modulo is taken only once at the end.
//
// Time: O(n) -- one walk forwards, one pass backwards, one pass over the sums.
// Space: O(n) -- the walk order and one sum per node.
//
// Siblings: MaxProductSplittedBT -- the same method by recursion; it overflows the default stack on
// a chain of about 5 * 10^4 nodes, this one does not.

public final class MaxProductSplittedBTIterative {

	private static final int MOD = (int) 1e9 + 7;

	public int maxProduct(TreeNode root) {
		List<TreeNode> order = new ArrayList<>(); // ORDER: every parent comes before its children
		Deque<TreeNode> stack = new ArrayDeque<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			order.add(node); // WALK: take the node, then its children
			if (node.left != null) {
				stack.push(node.left);
			}
			if (node.right != null) {
				stack.push(node.right);
			}
		}
		Map<TreeNode, Long> sums = new IdentityHashMap<>(); // SUM: the value sum of each node's subtree
		for (int i = order.size() - 1; i >= 0; i--) { // FINISH: children before their parent
			TreeNode node = order.get(i);
			long left = node.left == null ? 0 : sums.get(node.left);
			long right = node.right == null ? 0 : sums.get(node.right);
			sums.put(node, node.val + left + right);
		}
		long total = sums.get(root); // TOTAL: the sum of the whole tree
		long maxProduct = 0;
		for (long sum : sums.values()) {
			maxProduct = Math.max(maxProduct, sum * (total - sum)); // SPLIT: this subtree against the rest
		}
		return (int) (maxProduct % MOD); // RETURN: the modulo comes last
	}

	public static void main(String[] args) {

		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		MaxProductSplittedBTIterative mst = new MaxProductSplittedBTIterative();
		System.out.println(mst.maxProduct(root)); // 110

		TreeNode root2 = newNode(1);
		root2.right = newNode(2);
		root2.right.left = newNode(3);
		root2.right.right = newNode(4);
		root2.right.right.left = newNode(5);
		root2.right.right.right = newNode(6);
		MaxProductSplittedBTIterative mst2 = new MaxProductSplittedBTIterative();
		System.out.println(mst2.maxProduct(root2)); // 90

	}
}

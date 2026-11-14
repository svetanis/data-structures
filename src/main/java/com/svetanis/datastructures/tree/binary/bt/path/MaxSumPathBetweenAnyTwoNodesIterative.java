package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

// 124. Binary Tree Maximum Path Sum
//
// Input: the root of a binary tree. A path is a chain of nodes joined by edges, visiting each
// node at most once; it may start and end anywhere and need not pass through the root.
// Returns the largest sum of values on any path with at least one node. On LeetCode:
// 1 <= n <= 3 * 10^4, -1000 <= val <= 1000.
//
// The one idea: the best path has a highest node; the path goes down from it into at most two
// subtrees. One map entry holds, for one node, the best sum of a path that starts at that node
// and goes DOWN one way only (never below 0: a path may skip a harmful subtree). A node's entry
// can be written once both children's entries exist, so the nodes are listed parents first with
// a stack, then handled in the reverse order: children always come before their parent.
// At each node, value + left + right is the best path whose highest node is this one.
//
// Time: O(n) -- each node is listed once and handled once.
// Space: O(n) -- the list of nodes and the map.
//
// Siblings: MaxSumPathBetweenAnyTwoNodes and MaxSumPathBetweenAnyTwoNodesGlobal are the recursive
// versions; they overflow the default stack on a chain tens of thousands of nodes deep, this one
// does not.
public final class MaxSumPathBetweenAnyTwoNodesIterative {

	public static int maxSum(TreeNode root) {
		int best = Integer.MIN_VALUE; // BEST: smaller than any sum
		List<TreeNode> order = new ArrayList<>(); // ORDER: every node, a parent before its children
		Deque<TreeNode> stack = new ArrayDeque<>();
		if (root != null) {
			stack.push(root);
		}
		while (!stack.isEmpty()) { // LIST: depth-first, parents first
			TreeNode node = stack.pop();
			order.add(node);
			if (node.left != null) {
				stack.push(node.left);
			}
			if (node.right != null) {
				stack.push(node.right);
			}
		}

		// DOWN: node -> best sum of a path starting at the node and going down one way
		Map<TreeNode, Integer> down = new IdentityHashMap<>();
		for (int i = order.size() - 1; i >= 0; i--) { // WALK: children before their parent
			TreeNode node = order.get(i);
			int left = node.left == null ? 0 : max(0, down.get(node.left)); // DOWN LEFT: ignore negative
			int right = node.right == null ? 0 : max(0, down.get(node.right)); // DOWN RIGHT: same
			best = max(best, node.val + left + right); // FORK + RECORD: root is the highest node
			down.put(node, node.val + max(left, right)); // RETURN: a parent can use only one side
		}
		return best;
	}

	public static void main(String[] args) {
		// Max sum is 27 = 3 + 6 + 9 + 0 - 1 + 10
		TreeNode root = newNode(-15);
		root.left = newNode(5);
		root.right = newNode(6);
		root.left.left = newNode(-8);
		root.left.right = newNode(1);
		root.right.left = newNode(3);
		root.right.right = newNode(9);
		root.left.left.left = newNode(2);
		root.left.left.right = newNode(6);
		root.right.right.right = newNode(0);
		root.right.right.right.left = newNode(4);
		root.right.right.right.right = newNode(-1);
		root.right.right.right.right.left = newNode(10);
		System.out.println(maxSum(root)); // 27

		// Max sum is 42 = 20 + 2 + 10 + 10
		TreeNode root2 = newNode(10);
		root2.left = newNode(2);
		root2.right = newNode(10);
		root2.left.left = newNode(20);
		root2.left.right = newNode(1);
		root2.right.right = newNode(-25);
		root2.right.right.left = newNode(3);
		root2.right.right.right = newNode(4);
		System.out.println(maxSum(root2)); // 42

		// LeetCode example 1
		TreeNode root3 = newNode(1);
		root3.left = newNode(2);
		root3.right = newNode(3);
		System.out.println(maxSum(root3)); // 6

		// LeetCode example 2
		TreeNode root4 = newNode(-10);
		root4.left = newNode(9);
		root4.right = newNode(20);
		root4.right.left = newNode(15);
		root4.right.right = newNode(7);
		System.out.println(maxSum(root4)); // 42
	}
}

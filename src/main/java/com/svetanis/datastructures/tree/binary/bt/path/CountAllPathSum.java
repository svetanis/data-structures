package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

// 437. Path Sum III
//
// Input: the root of a binary tree and an integer targetSum. A path starts at any node and goes
// down to one of its descendants. Returns how many such paths have values adding up to
// targetSum. On LeetCode: 0 <= n <= 1000, -10^9 <= val <= 10^9, -10^9 <= targetSum <= 10^9
// (a sum of many large values does not fit in an int, so the running sums are longs).
//
// The one idea: walk the tree depth first and keep the values from the root down to the
// current node in a list. Every downward path that ends at the current node is a tail of that
// list, so add the values up from the end of the list one at a time: each running total is the
// sum of one such path, and it counts when it equals targetSum. Leaving a node removes its value
// from the list. Every downward path ends at exactly one node, so every path is counted once.
//
// Time: O(n * h) -- each node adds up at most h values (h = height), so O(n log n) on a balanced
//   tree and O(n^2) on a chain.
// Space: O(h) -- the list and the recursion stack.
//
// Siblings: CountAllPathSumHashing gets O(n) by keeping a map of prefix sums instead of adding
// the tail up again at every node; AllPathSum lists the paths instead of counting them.
public final class CountAllPathSum {

	public static int pathCount(TreeNode root, int k) {
		List<Integer> list = new LinkedList<>(); // PATH: values from the root to the current node
		return pathCount(root, k, list);
	}

	private static int pathCount(TreeNode node, int k, List<Integer> path) {
		if (isNull(node)) { // BASE: nothing below
			return 0;
		}
		long sum = 0; // LONG: up to 1000 values of 10^9 each
		int count = 0;
		path.add(node.val); // TAKE: the current node joins the path

		ListIterator<Integer> iter = path.listIterator(path.size());
		while (iter.hasPrevious()) { // WALK: up from the current node toward the root
			sum += iter.previous();
			if (sum == k) { // COUNT: this tail of the path sums to the target
				count++;
			}
		}

		count += pathCount(node.left, k, path); // WALK: left subtree
		count += pathCount(node.right, k, path); // WALK: right subtree
		path.remove(path.size() - 1); // UNDO: leave the node
		return count;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(12);
		root.left = newNode(7);
		root.right = newNode(1);
		root.left.left = newNode(4);
		root.right.left = newNode(10);
		root.right.right = newNode(5);
		System.out.println(pathCount(root, 11)); // 2

		// LeetCode example 1
		TreeNode root2 = newNode(10);
		root2.left = newNode(5);
		root2.right = newNode(-3);
		root2.left.left = newNode(3);
		root2.left.right = newNode(2);
		root2.left.left.left = newNode(3);
		root2.left.left.right = newNode(-2);
		root2.left.right.right = newNode(1);
		root2.right.right = newNode(11);
		System.out.println(pathCount(root2, 8)); // 3

		// LeetCode example 2
		TreeNode root3 = newNode(5);
		root3.left = newNode(4);
		root3.right = newNode(8);
		root3.left.left = newNode(11);
		root3.right.left = newNode(13);
		root3.right.right = newNode(4);
		root3.left.left.left = newNode(7);
		root3.left.left.right = newNode(2);
		root3.right.right.left = newNode(5);
		root3.right.right.right = newNode(1);
		System.out.println(pathCount(root3, 22)); // 3
	}
}

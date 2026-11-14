package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

// All Downward Paths With a Given Sum -- not a LeetCode problem; the counting version is
// 437. Path Sum III
//
// Input: the root of a binary tree and a number k. A path starts at any node and goes down to
// one of its descendants. Returns every such path whose values add up to k, each listed from its
// lowest node up to its highest. The tree has no stated size limit.
//
// The one idea: walk the tree depth first and keep the values from the root down to the
// current node in a list. Every downward path that ends at the current node is a tail of that
// list, so add the values up from the end of the list one at a time: each running total is the
// sum of one such path, and it is an answer when it equals k. Leaving a node removes its value
// from the list again. Every downward path ends at exactly one node, so every path is seen once.
//
// Time: O(n * h) plus the size of the answer -- each node adds up at most h values (h = height,
//   so O(n^2) on a chain); each answer is copied once.
// Space: O(h) -- the list; the answer is extra.
//
// Siblings: CountAllPathSum counts the same paths without listing them; CountAllPathSumHashing
// counts them in O(n) with a map of prefix sums.
public final class AllPathSum {

	private static final String MISMATCH = "count and list disagree";

	public static List<List<Integer>> pathCount(TreeNode root, int k) {
		List<List<Integer>> paths = new ArrayList<>();
		List<Integer> path = new LinkedList<>(); // PATH: values from the root to the current node
		int count = pathCount(root, k, path, paths);
		if (count != paths.size()) { // CHECK: the count and the list are two views of one answer
			throw new IllegalStateException(MISMATCH);
		}
		return paths;
	}

	private static int pathCount(
			TreeNode node, int k, List<Integer> path, List<List<Integer>> paths) {
		if (isNull(node)) { // BASE: nothing below
			return 0;
		}
		int sum = 0;
		int count = 0;
		path.add(node.val); // TAKE: the current node joins the path

		List<Integer> list = new ArrayList<>(); // LIST: the stretch added up so far, lowest node first
		ListIterator<Integer> iter = path.listIterator(path.size());
		while (iter.hasPrevious()) { // WALK: up from the current node toward the root
			int prev = iter.previous();
			list.add(prev);
			sum += prev;
			if (sum == k) { // RECORD: this stretch is a path with sum k; keep going, longer ones may hit
				count++;
				paths.add(new ArrayList<>(list));
			}
		}

		count += pathCount(node.left, k, path, paths); // WALK: left subtree
		count += pathCount(node.right, k, path, paths); // WALK: right subtree
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
		System.out.println(pathCount(root, 11)); // [[4, 7], [10, 1]]
	}
}

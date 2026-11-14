package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// 113. Path Sum II
//
// Input: the root of a binary tree and an integer targetSum. Returns every root-to-leaf path
// whose values add up to targetSum, each as the list of its values, root first, in left-to-right
// order. On LeetCode: 0 <= n <= 5000, -1000 <= val <= 1000, -1000 <= targetSum <= 1000.
//
// The one idea: subtract each value from the target on the way down, so what is carried is the
// sum the rest of the path must still reach. Keep the values from the root to the current node
// in a deque. A leaf whose value equals what is carried completes a path: copy the deque into the
// answer. Leaving a node removes its value from the deque again, so the deque always holds
// exactly the path to the current node.
//
// Time: O(n * h) -- each node is visited once, and each answer path is copied (h = height, so
//   at most h values per copy); O(n^2) in the worst case.
// Space: O(h) -- the deque and the recursion stack; the answer is extra.
//
// Siblings: RootToLeafPathsGivenSumSubmit is the same method on plain lists for the judge.
public final class RootToLeafPathsGivenSum {

	public static List<List<Integer>> paths(TreeNode node, int sum) {
		Deque<Integer> path = new ArrayDeque<>(); // PATH: values from the root to the current node
		List<List<Integer>> paths = new ArrayList<>();
		paths(node, sum, path, paths);
		return paths;
	}

	private static void paths(
			TreeNode node, int sum, Deque<Integer> path, List<List<Integer>> paths) {
		if (isNull(node)) { // BASE: nothing below
			return;
		}
		path.addLast(node.val); // TAKE: the current node joins the path
		if (isLeaf(node) && sum == node.val) { // RECORD: a leaf that uses up what is left
			paths.add(new ArrayList<>(path));
		} else {
			paths(node.left, sum - node.val, path, paths); // WALK: left subtree, smaller target
			paths(node.right, sum - node.val, path, paths); // WALK: right subtree, smaller target
		}
		path.removeLast(); // UNDO: leave the node
	}

	public static void main(String[] args) {
		// sum = 21 : 10->8->3
		// sum = 23 : 10->8->5
		// sum = 21 : 10->9->2
		TreeNode root = newNode(10);
		root.left = newNode(8);
		root.right = newNode(9);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.left = newNode(2);
		inOrder(root); // prints 3 8 5 10 2 9
		System.out.println();

		// given sum root-to-leaf path
		System.out.println(paths(root, 21)); // [[10, 8, 3], [10, 9, 2]]

		// LeetCode example 1
		TreeNode root2 = newNode(5);
		root2.left = newNode(4);
		root2.right = newNode(8);
		root2.left.left = newNode(11);
		root2.right.left = newNode(13);
		root2.right.right = newNode(4);
		root2.left.left.left = newNode(7);
		root2.left.left.right = newNode(2);
		root2.right.right.left = newNode(5);
		root2.right.right.right = newNode(1);
		System.out.println(paths(root2, 22)); // [[5, 4, 11, 2], [5, 8, 4, 5]]

		// LeetCode example 2
		TreeNode root3 = newNode(1);
		root3.left = newNode(2);
		root3.right = newNode(3);
		System.out.println(paths(root3, 5)); // []
	}
}

package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1373. Maximum Sum BST in Binary Tree
//
// Input: the root of a binary tree. A subtree (a node with everything below it) that is a binary
// search tree has every value in its left part smaller than the node and every value in its right
// part larger, with no equal values. Returns the largest sum of values over all subtrees that are
// binary search trees; an empty one counts, so the answer is never negative. On LeetCode:
// 1 <= number of nodes <= 4 * 10^4, -4 * 10^4 <= Node.val <= 4 * 10^4.
//
// The one idea: keep four facts for one node's subtree: is it a search tree, its smallest value, its
// largest value, and its sum. A node's subtree is a search tree when both children's subtrees are,
// the node is larger than the left part's largest value, and smaller than the right part's
// smallest. Then its smallest is the left part's smallest (or the node itself when the left is
// empty), its largest likewise on the right, and its sum is the node's value plus both sums. An
// empty subtree is "a search tree, smallest = INF, largest = -INF, sum 0", which lets every
// comparison pass without special cases. The facts of a node need its children's facts first. A walk
// that takes each node before its children lists every parent before its children; going through
// that list backwards finishes every child before its parent.
//
// The sum cannot overflow: 4e4 nodes at 4e4 each is 1.6e9, against Integer.MAX_VALUE of 2.1e9. An
// empty search tree scores 0, which is why the answer starts at 0 and is only ever raised.
//
// Time: O(n) -- one walk forwards, one pass backwards, constant work per node.
// Space: O(n) -- the walk order and four facts per node.
//
// Siblings: MaxSumBST -- the same four facts by recursion; it overflows the default stack on a chain
// of about 4 * 10^4 nodes, this one does not.

public final class MaxSumBSTIterative {

	private static final int INF = 1 << 30;

	public int maxSum(TreeNode root) {
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
		Map<TreeNode, int[]> facts = new IdentityHashMap<>(); // FACTS: { is a search tree, smallest, largest, sum }
		int maxSum = 0; // BEST: the empty search tree scores 0
		for (int i = order.size() - 1; i >= 0; i--) { // FINISH: children before their parent
			TreeNode node = order.get(i);
			int[] left = node.left == null ? new int[] { 1, INF, -INF, 0 } : facts.get(node.left);
			int[] right = node.right == null ? new int[] { 1, INF, -INF, 0 } : facts.get(node.right);
			int curr = node.val;
			boolean valid = curr > left[2] && curr < right[1]; // CHECK: strictly between the two parts
			if (valid && left[0] == 1 && right[0] == 1) {
				int sum = curr + left[3] + right[3];
				maxSum = Math.max(maxSum, sum); // RECORD: keep the largest
				facts.put(node, new int[] { 1, Math.min(left[1], curr), Math.max(right[2], curr), sum });
			} else {
				facts.put(node, new int[] { 0, 0, 0, 0 }); // SKIP: not a search tree, so neither is any ancestor
			}
		}
		return maxSum; // RETURN: the largest sum
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(4);
		root.right = newNode(3);
		root.left.left = newNode(2);
		root.left.right = newNode(4);
		root.right.left = newNode(2);
		root.right.right = newNode(5);
		root.right.right.left = newNode(4);
		root.right.right.right = newNode(6);
		MaxSumBSTIterative mst = new MaxSumBSTIterative();
		System.out.println(mst.maxSum(root)); // 20

		TreeNode root2 = newNode(4);
		root2.left = newNode(3);
		root2.left.left = newNode(1);
		root2.left.right = newNode(2);
		MaxSumBSTIterative mst2 = new MaxSumBSTIterative();
		System.out.println(mst2.maxSum(root2)); // 2
	}
}

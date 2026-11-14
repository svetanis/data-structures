package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 563. Binary Tree Tilt
//
// Input: the root of a binary tree. The tilt of one node is the absolute difference between the sum
// of the values in its left subtree and the sum of the values in its right subtree; an empty side
// counts as 0. Returns the sum of the tilts of all nodes. On LeetCode: 0 <= number of nodes <= 10^4,
// -1000 <= Node.val <= 1000. The answer is returned as an int, as on LeetCode, and is assumed to
// fit; a chain of 10^4 nodes all holding 1000 would overflow it.
//
// The one idea: a node's tilt needs the sum of everything under its left child and under its right
// child, so the children's sums must be finished first. A walk that takes each node before its
// children lists every parent before its children; going through that list backwards finishes every
// child before its parent. Each node reads its two children's sums, adds |left sum - right sum| to
// a running total, and stores its own value plus both sums for its parent.
//
// Time: O(n) -- one walk forwards, one pass backwards, constant work per node.
// Space: O(n) -- the walk order and one sum per node.
//
// Siblings: BinaryTreeTiltGlobal and BinaryTreeTilt -- the same total by recursion; the Global one
// overflows the default stack on a chain of about 10^4 nodes, this one does not.

public final class BinaryTreeTiltGlobalIterative {

	public int tilt(TreeNode root) {
		List<TreeNode> order = new ArrayList<>(); // ORDER: every parent comes before its children
		Deque<TreeNode> stack = new ArrayDeque<>();
		if (root != null) {
			stack.push(root);
		}
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
		Map<TreeNode, Integer> sums = new IdentityHashMap<>(); // SUM: the value sum of each node's subtree
		int total = 0; // TOTAL: the tilts added so far
		for (int i = order.size() - 1; i >= 0; i--) { // FINISH: children before their parent
			TreeNode node = order.get(i);
			int left = node.left == null ? 0 : sums.get(node.left);
			int right = node.right == null ? 0 : sums.get(node.right);
			total += Math.abs(left - right); // RECORD: this node's tilt joins the total
			sums.put(node, node.val + left + right);
		}
		return total; // RETURN: the total of all tilts
	}

	public static void main(String[] args) {
		BinaryTreeTiltGlobalIterative btt = new BinaryTreeTiltGlobalIterative();
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		System.out.println(btt.tilt(root)); // 1

		TreeNode root1 = newNode(4);
		root1.left = newNode(2);
		root1.right = newNode(9);
		root1.left.left = newNode(3);
		root1.left.right = newNode(5);
		root1.right.right = newNode(7);
		System.out.println(btt.tilt(root1)); // 15
	}
}

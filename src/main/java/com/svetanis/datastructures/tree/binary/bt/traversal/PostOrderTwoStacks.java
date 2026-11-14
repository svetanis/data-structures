package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 145. Binary Tree Postorder Traversal
//
// Input: root -- the root of a binary tree, possibly null. Returns the values in postorder: the
// whole left subtree, the whole right subtree, then the node. On LeetCode: 0 <= nodes <= 100,
// -100 <= Node.val <= 100.
//
// The one idea: postorder read backwards is node, right, left -- and that is a preorder with the
// two children swapped. So walk preorder with the right child visited first (push left, then right,
// so right is popped first), and put every visited node on a second stack. The second stack hands
// the nodes back in the opposite order, which is left, right, node.
//
// Time: O(n) -- every node goes through both stacks once.
// Space: O(n) -- the second stack holds every node; the first holds at most one chain of waiting
// siblings.
//
// Siblings: PostOrderRecursive prints the same order with the call stack; PostOrderDfs gets it with
// a set of reported nodes and a restart from the root.

public final class PostOrderTwoStacks {

	public static List<Integer> postOrder(TreeNode root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Deque<TreeNode> stack = new ArrayDeque<>(); // WAIT: nodes still to visit
		Deque<TreeNode> out = new ArrayDeque<>(); // OUT: visit order, read back reversed
		stack.push(root);
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop(); // VISIT: node, then right side, then left side
			out.push(node); // OUT: last visited ends up on top

			if (node.left != null) { // PUSH LEFT first, so it is popped last
				stack.push(node.left);
			}
			if (node.right != null) { // PUSH RIGHT second, so it is popped first
				stack.push(node.right);
			}
		}
		return toList(out); // RETURN: popped from the top, so left, right, node
	}

	public static List<Integer> toList(Deque<TreeNode> queue) {
		List<Integer> list = new ArrayList<>();
		while (!queue.isEmpty()) {
			list.add(queue.poll().val); // TAKE: from the top, last visited first
		}
		return list;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(6);
		System.out.println(postOrder(root)); // [4, 5, 2, 6, 3, 1]
	}
}

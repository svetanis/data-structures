package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayDeque;
import java.util.Deque;

// 988. Smallest String Starting From Leaf
//
// Input: the root of a binary tree whose values are 0 to 25, standing for the letters 'a' to 'z'.
// Returns the lexicographically smallest string that starts at a leaf and ends at the root.
// A shorter string that is a prefix of a longer one is the smaller of the two. On LeetCode:
// 1 <= n <= 8500, 0 <= val <= 25.
//
// The one idea: keep the letters from the root down to the current node in a StringBuilder; at a
// leaf, reverse a copy to get the leaf-to-root string and keep it if it is smaller than the best
// so far. The walk is the one in LexSmallestRootToLeafPathBacktrack with the recursion replaced
// by a stack. One stack entry is a node plus a flag: "false" means the node is about to be
// entered (append its letter, look at the leaf, push the children); "true" means its children are
// done (delete its letter again).
//
// Time: O(n + L * h) -- each node is pushed twice; each of the L leaves copies and reverses a
//   string of up to h letters (h = height).
// Space: O(h) -- the builder and the stack.
//
// Siblings: LexSmallestRootToLeafPathBacktrack and LexSmallestRootToLeafPathBacktrack2 are the
// recursive versions; they overflow the default stack on a chain about 8500 nodes deep.
public final class LexSmallestRootToLeafPathBacktrackIterative {

	public String minPath(TreeNode root) {
		String min = "~"; // BEST: larger than any string of letters 'a' to 'z'
		StringBuilder sb = new StringBuilder();
		Deque<TreeNode> nodes = new ArrayDeque<>();
		Deque<Boolean> leaving = new ArrayDeque<>(); // FLAG: true = the node's children are done
		if (root != null) {
			nodes.push(root);
			leaving.push(false);
		}
		while (!nodes.isEmpty()) {
			TreeNode node = nodes.pop();
			boolean done = leaving.pop();
			if (done) { // UNDO: leave the node
				sb.deleteCharAt(sb.length() - 1);
				continue;
			}
			sb.append((char) (node.val + 'a')); // TAKE: the current letter joins the path
			if (node.left == null && node.right == null) { // LEAF: the path is complete
				String s = new StringBuilder(sb).reverse().toString(); // FLIP: leaf first
				if (s.compareTo(min) < 0) { // RECORD: a smaller string
					min = s;
				}
			}
			nodes.push(node); // LEAVE: come back here after both children
			leaving.push(true);
			if (node.right != null) { // WALK: right subtree (pushed first, so it is visited second)
				nodes.push(node.right);
				leaving.push(false);
			}
			if (node.left != null) { // WALK: left subtree
				nodes.push(node.left);
				leaving.push(false);
			}
		}
		return min;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(0);
		root.left = newNode(1);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(4);
		root.right.left = newNode(3);
		root.right.right = newNode(4);
		inOrder(root); // prints 3 1 4 0 3 2 4
		System.out.println();
		LexSmallestRootToLeafPathBacktrackIterative lsp =
				new LexSmallestRootToLeafPathBacktrackIterative();
		System.out.println(lsp.minPath(root)); // dba

		// LeetCode example 2
		TreeNode root2 = newNode(25);
		root2.left = newNode(1);
		root2.right = newNode(3);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.right.left = newNode(0);
		root2.right.right = newNode(2);
		System.out.println(lsp.minPath(root2)); // adz

		// LeetCode example 3
		TreeNode root3 = newNode(2);
		root3.left = newNode(2);
		root3.right = newNode(1);
		root3.left.right = newNode(1);
		root3.right.left = newNode(0);
		root3.left.right.left = newNode(0);
		System.out.println(lsp.minPath(root3)); // abc
	}
}

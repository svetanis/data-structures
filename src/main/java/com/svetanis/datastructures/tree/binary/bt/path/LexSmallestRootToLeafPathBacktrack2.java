package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 988. Smallest String Starting From Leaf

// Find the lexicographically smallest str 
// that starts at root and ends at leaf in BT

public final class LexSmallestRootToLeafPathBacktrack2 {
	// Time complexity: O(n)

	private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

	private String min;

	public String minPath(Node root) {
		this.min = null;
		dfs(root, new StringBuilder());
		return min;
	}

	private void dfs(Node node, StringBuilder sb) {
		if (node == null) {
			return;
		}
		sb.insert(0, LETTERS.charAt(node.data));
		if (node.left == null && node.right == null) {
			String s = sb.toString();
			if (min == null || s.compareTo(min) < 0) {
				min = s;
			}
		}
		dfs(node.left, sb);
		dfs(node.right, sb);
		sb.deleteCharAt(0);
	}

	public static void main(String[] args) {
		Node root = newNode(0);
		root.left = newNode(1);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(4);
		root.right.left = newNode(3);
		root.right.right = newNode(4);
		inOrder(root);
		System.out.println();
		LexSmallestRootToLeafPathBacktrack2 lsp = new LexSmallestRootToLeafPathBacktrack2();
		System.out.println(lsp.minPath(root));
	}
}

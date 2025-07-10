package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 988. Smallest String Starting From Leaf

// Find the lexicographically smallest str 
// that starts at root and ends at leaf in BT

public final class LexSmallestRootToLeafPathBacktrack {
	// Time complexity: O(n)

	private String min;

	public String minPath(Node root) {
		this.min = "~";
		dfs(root, new StringBuilder());
		return min;
	}

	private void dfs(Node root, StringBuilder sb) {
		if (root == null) {
			return;
		}
		char c = (char) (root.data + 'a');
		sb.append(c);
		if (root.left == null && root.right == null) {
			sb.reverse();
			String s = sb.toString();
			sb.reverse();
			if (s.compareTo(min) < 0) {
				min = s;
			}
		}
		dfs(root.left, sb);
		dfs(root.right, sb);
		sb.deleteCharAt(sb.length() - 1);
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
		LexSmallestRootToLeafPathBacktrack lsp = new LexSmallestRootToLeafPathBacktrack();
		System.out.println(lsp.minPath(root));
	}
}

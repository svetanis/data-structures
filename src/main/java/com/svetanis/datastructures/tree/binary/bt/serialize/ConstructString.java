package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 606. Construct String from Binary Tree

public final class ConstructString {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	public static String treeToStr(Node root) {
		StringBuilder sb = new StringBuilder();
		treeToStr(root, sb);
		return sb.toString();
	}

	private static void treeToStr(Node root, StringBuilder sb) {
		if (root == null) {
			return;
		}
		sb.append(root.data + "");
		if (root.left != null) {
			sb.append("(");
			treeToStr(root.left, sb);
			sb.append(")");
		} else if (root.left == null && root.right != null) {
			sb.append("()");
		}
		if (root.right != null) {
			sb.append("(");
			treeToStr(root.right, sb);
			sb.append(")");
		}
	}

	public static String tree2str(Node root) {
		if (root == null) {
			return "";
		}
		if (root.left == null && root.right == null) {
			return root.data + "";
		}
		if (root.right == null) {
			return root.data + "(" + tree2str(root.left) + ")";
		}
		return root.data + "(" + tree2str(root.left) + ")(" + tree2str(root.right) + ")";
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		System.out.println(tree2str(root)); // 1(2(4))(3)

		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.right = newNode(4);
		System.out.println(tree2str(root1)); // 1(2()(4))(3)
	}
}

package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.preOrder;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;

import java.util.LinkedList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 297. Serialize and Deserialize Binary Tree
//
// Input: serialize takes the root of a binary tree; deserialize takes a string made by serialize.
// On LeetCode: 0 to 10^4 nodes, -1000 <= val <= 1000.
// Returns: serialize gives the pre-order text, with "#" standing for every missing child and a
// comma after every token (the text ends with a comma); deserialize gives back an equal tree.
//
// The one idea: pre-order (node, left subtree, right subtree) alone does not fix a tree's shape,
// but pre-order with a marker written for every missing child does: reading the tokens left to
// right, a number is a node whose left subtree is read next and then its right subtree, and a
// marker ends the subtree being read. The tokens sit in one list shared by all the calls; each call
// removes the front token and takes as many following tokens as its own subtree needs.
//
// Time: O(n) -- one token written and one token read per node and per missing child (about 2n);
// removing the front of a LinkedList is O(1).
// Space: O(n) for the text and the token list, plus the recursion (as deep as the tree is tall,
// up to n).
//
// Known limit: the recursion is as deep as the tree is tall. A chain of about 10,000 nodes (the
// LeetCode limit) overflows the default stack in serialize; this only happens on very large input.
//
// Siblings: SerializeDeserializeBTPreOrderIterative is the same method with an explicit stack;
// SerializeBTPreOrderSimple is the same idea with "x" and a space and an iterator over tokens;
// SerializeBTPreOrder is the same idea with a StringTokenizer.

public final class SerializeDeserializeBTPreOrder {

	private static final String DELIMITER = "#";
	private static final String SEPARATOR = ",";

	public static String serialize(TreeNode root) {
		StringBuilder sb = new StringBuilder();
		serialize(root, sb);
		return sb.toString();
	}

	private static void serialize(TreeNode root, StringBuilder sb) {
		if (root == null) { // MARKER: a missing child
			sb.append(DELIMITER).append(SEPARATOR);
			return;
		}
		sb.append(root.val).append(SEPARATOR); // VALUE: the node, before its subtrees
		serialize(root.left, sb); // LEFT: whole left subtree
		serialize(root.right, sb); // RIGHT: whole right subtree
	}

	public static TreeNode deserialize(String s) {
		if (s == null || s.isBlank() || s.equals(DELIMITER)) { // an empty tree is the single marker
			return null;
		}
		List<String> list = new LinkedList<>(asList(s.split(SEPARATOR)));
		return deserialize(list);
	}

	private static TreeNode deserialize(List<String> list) {
		String curr = list.remove(0); // TAKE: the front token
		if (DELIMITER.equals(curr)) { // BASE: marker, a missing child
			return null;
		}
		TreeNode node = new TreeNode(parseInt(curr)); // VALUE: the token is this node
		node.left = deserialize(list); // LEFT: read next
		node.right = deserialize(list); // RIGHT: read after the left subtree is complete
		return node;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(6);
		root.left = newNode(4);
		root.right = newNode(8);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		preOrder(root); // 6 4 3 5 8
		System.out.println();
		String serialized = serialize(root);
		System.out.println(serialized); // 6,4,3,#,#,5,#,#,8,#,#,
		TreeNode deserialized = deserialize(serialized);
		preOrder(deserialized); // 6 4 3 5 8
		System.out.println();
	}
}

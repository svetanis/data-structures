package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.preOrder;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;

import java.util.Iterator;
import java.util.StringJoiner;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 297. Serialize and Deserialize Binary Tree
//
// Input: serialize takes the root of a binary tree; deserialize takes a string made by serialize.
// On LeetCode: 0 to 10^4 nodes, -1000 <= val <= 1000.
// Returns: serialize gives the pre-order text, with "x" standing for every missing child, tokens
// separated by one space; deserialize gives back an equal tree.
//
// The one idea: pre-order (node, left subtree, right subtree) alone does not fix a tree's shape,
// but pre-order with a marker written for every missing child does: reading the tokens left to
// right, a number is a node whose left subtree is read next and then its right subtree, and a
// marker ends the subtree being read. One iterator over the tokens is shared by all the calls, so
// each call takes exactly the tokens of its own subtree and leaves the rest.
//
// Time: O(n) -- one token written and one token read per node and per missing child (about 2n).
// Space: O(n) for the text, plus the recursion (as deep as the tree is tall, up to n).
//
// Known limit: the recursion is as deep as the tree is tall. A chain of about 7,000 nodes, inside
// LeetCode's limit of 10^4, overflows the default stack in serialize; this only happens on very
// large input.
//
// Siblings: SerializeBTPreOrderSimpleIterative is the same method with an explicit stack;
// SerializeDeserializeBTPreOrder is the same idea with "," and "#" and a token list;
// SerializeBTPreOrder is the same idea with a StringTokenizer.

public final class SerializeBTPreOrderSimple {

	private static final String DELIMITER = "x";

	public static String serialize(TreeNode root) {
		StringJoiner joiner = new StringJoiner(" ");
		serialize(root, joiner);
		return joiner.toString();
	}

	private static void serialize(TreeNode root, StringJoiner joiner) {
		if (root == null) { // MARKER: a missing child
			joiner.add(DELIMITER);
			return;
		}
		joiner.add(root.val + ""); // VALUE: the node, before its subtrees
		serialize(root.left, joiner); // LEFT: whole left subtree
		serialize(root.right, joiner); // RIGHT: whole right subtree
	}

	public static TreeNode deserialize(String root) {
		if (root.equals(DELIMITER)) { // an empty tree is the single marker
			return null;
		}
		Iterator<String> iter = stream(root.split(" ")).iterator();
		return deserialize(iter);
	}

	private static TreeNode deserialize(Iterator<String> iter) {
		if (!iter.hasNext()) { // BASE: text used up
			return null;
		}
		String curr = iter.next();
		if (curr.equals(DELIMITER)) { // BASE: marker, a missing child
			return null;
		}
		TreeNode node = new TreeNode(parseInt(curr)); // VALUE: the next token is this node
		node.left = deserialize(iter); // LEFT: read next
		node.right = deserialize(iter); // RIGHT: read after the left subtree is complete
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
		System.out.println(serialized); // 6 4 3 x x 5 x x 8 x x
		TreeNode deserialized = deserialize(serialized);
		preOrder(deserialized); // 6 4 3 5 8
		System.out.println();
	}
}

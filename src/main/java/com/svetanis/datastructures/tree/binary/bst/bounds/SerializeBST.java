package com.svetanis.datastructures.tree.binary.bst.bounds;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 449. Serialize and Deserialize BST
//
// Input: serialize takes the root of a BST; deserialize takes the string serialize returned.
// On LeetCode: 0 <= number of nodes <= 10^4, 0 <= node.val <= 10^4, the tree is a BST (values
// distinct). Returns: serialize -- a string; deserialize -- the same tree back. The string should
// be as short as possible.
//
// The one idea: write only the preorder walk (root, left subtree, right subtree), values separated
// by one space -- no null markers. A BST needs nothing more, because its shape is fixed by the
// order of the values. Reading it back: the next number in the string is the root of the subtree
// being built, and the subtree may only hold values inside an open range (min, max). The left child
// must lie below the root, so its range is (min, root); the right child lies above, so its range is
// (root, max). When the next number falls outside the range, it belongs to a subtree higher up --
// stop and return nothing for this side, and leave that number unread.
//
// Time: O(n) -- each value is written once and read once.
// Space: O(n) -- the list of words; the recursion is as deep as the tree is tall.
//
// Known limit: deserialize recurses once per tree level, so a tree of 10^4 nodes that is one long
// chain (the LeetCode maximum) overflows the default thread stack with a StackOverflowError. See
// SerializeBSTIterative.java, the same method with an explicit stack.
//
// Siblings: SerializeBSTIterative does the same with explicit stacks, so it has no depth limit.

public final class SerializeBST {

	private static final String SEPARATOR = " ";
	private static final int INF = 1 << 30; // above and below every value LeetCode allows

	private int index;
	private List<String> list;

	public String serialize(TreeNode root) {
		this.list = new ArrayList<>();
		serialize(root, list);
		return String.join(SEPARATOR, list);
	}

	private void serialize(TreeNode root, List<String> list) {
		if (root == null) {
			return;
		}
		list.add(String.valueOf(root.val)); // WRITE: the root first, then the left, then the right
		serialize(root.left, list);
		serialize(root.right, list);
	}

	public TreeNode deserialize(String s) {
		if (s.isBlank()) {
			return null;
		}
		this.index = 0; // RESET: a second call on the same object starts at the first word
		this.list = Arrays.asList(s.split(SEPARATOR));
		return deserialize(-INF, INF);
	}

	private TreeNode deserialize(int min, int max) {
		if (index == list.size()) { // BASE: every word is used
			return null;
		}
		int val = Integer.parseInt(list.get(index));
		if (val < min || val > max) { // SKIP: it belongs to a subtree higher up, leave it unread
			return null;
		}
		TreeNode node = new TreeNode(val); // TAKE: the next word is the root of this subtree
		index += 1;
		node.left = deserialize(min, val); // LEFT: values below the root
		node.right = deserialize(val, max); // RIGHT: values above the root
		return node;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		SerializeBST bst = new SerializeBST();
		inOrder(root); // prints 1 2 4 5
		System.out.println();
		String serialized = bst.serialize(root);
		System.out.println("serialized=" + serialized); // serialized=4 2 1 5
		TreeNode deserialized = bst.deserialize(serialized);
		inOrder(deserialized); // prints 1 2 4 5
		System.out.println();
		System.out.println(bst.serialize(null).isEmpty()); // true
		System.out.println(bst.deserialize("") == null); // true
	}
}

package com.svetanis.datastructures.tree.binary.bst.bounds;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 449. Serialize and Deserialize BST
//
// Input: serialize takes the root of a BST; deserialize takes the string serialize returned.
// On LeetCode: 0 <= number of nodes <= 10^4, 0 <= node.val <= 10^4, the tree is a BST (values
// distinct). Returns: serialize -- a string; deserialize -- the same tree back.
//
// The one idea: the same as SerializeBST -- write only the preorder walk, values separated by one
// space, no null markers, because the order of the values fixes the shape of a BST. Reading it
// back uses one stack that holds the nodes whose right child is still open, the deepest on top.
// The next number is placed by comparing it with the top of the stack: if it is smaller, it is
// the left child of the top and is pushed. If it is larger, it is the right child of the deepest
// node it exceeds: pop while the number is larger than the top; the last node popped gets it as its
// right child, and it is pushed too.
//
// Time: O(n) -- each value is written once, pushed once and popped at most once.
// Space: O(n) -- the stack and the list of words; no recursion, so no depth limit.
//
// Siblings: SerializeBST does the same with recursion and an open range (min, max) per subtree; it
// overflows the default stack on a chain of 10^4 nodes.

public final class SerializeBSTIterative {

	private static final String SEPARATOR = " ";

	public String serialize(TreeNode root) {
		List<String> list = new ArrayList<>();
		Deque<TreeNode> stack = new ArrayDeque<>();
		if (root != null) {
			stack.push(root);
		}
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			list.add(String.valueOf(node.val)); // WRITE: the node, before anything under it
			if (node.right != null) {
				stack.push(node.right); // PUSH: right first, so the left comes out first
			}
			if (node.left != null) {
				stack.push(node.left);
			}
		}
		return String.join(SEPARATOR, list);
	}

	public TreeNode deserialize(String s) {
		if (s.isBlank()) {
			return null;
		}
		String[] words = s.split(SEPARATOR);
		TreeNode root = new TreeNode(Integer.parseInt(words[0])); // ROOT: the first word
		Deque<TreeNode> stack = new ArrayDeque<>(); // STACK: nodes whose right child is still open
		stack.push(root);
		for (int i = 1; i < words.length; i++) {
			int val = Integer.parseInt(words[i]);
			TreeNode node = new TreeNode(val);
			if (val < stack.peek().val) {
				stack.peek().left = node; // LEFT: smaller than the top, so its left child
			} else {
				TreeNode parent = stack.peek();
				while (!stack.isEmpty() && val > stack.peek().val) {
					parent = stack.pop(); // POP: the deepest node that the value exceeds
				}
				parent.right = node; // RIGHT: it becomes that node's right child
			}
			stack.push(node);
		}
		return root;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		SerializeBSTIterative bst = new SerializeBSTIterative();
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

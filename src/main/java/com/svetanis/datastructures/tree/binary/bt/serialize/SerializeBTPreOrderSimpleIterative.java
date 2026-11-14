package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.preOrder;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.StringJoiner;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 297. Serialize and Deserialize Binary Tree, without recursion
//
// Input: serialize takes the root of a binary tree; deserialize takes a string made by serialize.
// On LeetCode: 0 to 10^4 nodes, -1000 <= val <= 1000.
// Returns: serialize gives the pre-order text, with "x" standing for every missing child, tokens
// separated by one space; deserialize gives back an equal tree.
//
// The one idea: pre-order (node, left subtree, right subtree) alone does not fix a tree's shape,
// but pre-order with a marker written for every missing child does: reading the tokens left to
// right, a number is a node whose left subtree is read next and then its right subtree, and a
// marker ends the subtree being read.
// serialize keeps the nodes still to be written on a stack, with a stand-in MISSING node for each
// missing child, so a node pops, writes its value, and pushes its right then its left child.
// deserialize keeps two things: the node whose slot is filled next (parent) and whether that slot
// is its left or its right, plus a stack of nodes whose left slot is filled and whose right slot is
// still waiting. A number goes into the slot and becomes the new parent with its left slot next; a
// marker fills the slot with nothing, and the next slot to fill is the right slot of the most
// recent node still waiting.
//
// Time: O(n) -- one token written and one token read per node and per missing child (about 2n).
// Space: O(n) for the text, plus a stack of at most the height of the tree.
//
// Siblings: SerializeBTPreOrderSimple is the same method with recursion; it overflows the default
// stack on a chain of about 7,000 nodes, this file does not.

public final class SerializeBTPreOrderSimpleIterative {

	private static final String DELIMITER = "x";
	private static final TreeNode MISSING = new TreeNode(0); // stand-in for a missing child, compared by identity

	public static String serialize(TreeNode root) {
		StringJoiner joiner = new StringJoiner(" ");
		Deque<TreeNode> stack = new ArrayDeque<>();
		stack.push(orMissing(root));
		while (!stack.isEmpty()) { // WALK: pre-order, one entry per pop
			TreeNode node = stack.pop();
			if (node == MISSING) { // MARKER: a missing child
				joiner.add(DELIMITER);
			} else {
				joiner.add(String.valueOf(node.val)); // VALUE: the node, before its subtrees
				stack.push(orMissing(node.right)); // RIGHT: pushed first, written last
				stack.push(orMissing(node.left)); // LEFT: on top, written next
			}
		}
		return joiner.toString();
	}

	private static TreeNode orMissing(TreeNode node) {
		return node == null ? MISSING : node;
	}

	public static TreeNode deserialize(String text) {
		Iterator<String> iter = stream(text.split(" ")).iterator();
		Deque<TreeNode> waiting = new ArrayDeque<>(); // nodes whose right slot is still empty
		TreeNode root = null;
		TreeNode parent = null; // node whose slot is filled next
		boolean fillLeft = true;
		while (iter.hasNext()) { // WALK: one token per round
			String token = iter.next();
			TreeNode child = token.equals(DELIMITER) ? null : new TreeNode(parseInt(token));
			if (parent == null) { // ROOT: the very first token
				root = child;
			} else if (fillLeft) { // LEFT SLOT
				parent.left = child;
				waiting.push(parent); // its right slot comes after the left subtree
			} else { // RIGHT SLOT
				parent.right = child;
			}
			if (child != null) { // DOWN: the new node's left slot is next
				parent = child;
				fillLeft = true;
			} else if (waiting.isEmpty()) { // DONE: every slot is filled
				break;
			} else { // BACK: right slot of the latest node still waiting
				parent = waiting.pop();
				fillLeft = false;
			}
		}
		return root;
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

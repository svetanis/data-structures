package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.preOrder;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;

import java.util.Iterator;
import java.util.StringJoiner;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 297. Serialize and Deserialize Binary Tree

public final class SerializeBTPreOrderSimple {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	private static final String DELIMITER = "x";

	public static String serialize(Node root) {
		StringJoiner joiner = new StringJoiner(" ");
		serialize(root, joiner);
		return joiner.toString();
	}

	private static void serialize(Node root, StringJoiner joiner) {
		if (root == null) {
			joiner.add(DELIMITER);
			return;
		}
		joiner.add(root.data + "");
		serialize(root.left, joiner);
		serialize(root.right, joiner);
	}

	public static Node deserialize(String root) {
		if (root.equals(DELIMITER)) {
			return null;
		}
		Iterator<String> iter = stream(root.split(" ")).iterator();
		return deserialize(iter);
	}

	private static Node deserialize(Iterator<String> iter) {
		if (!iter.hasNext()) {
			return null;
		}
		String curr = iter.next();
		if (curr.equals(DELIMITER)) {
			return null;
		}
		Node node = new Node(parseInt(curr));
		node.left = deserialize(iter);
		node.right = deserialize(iter);
		return node;
	}

	public static void main(String[] args) {
		Node root = newNode(6);
		root.left = newNode(4);
		root.right = newNode(8);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		preOrder(root);
		System.out.println();
		String serialized = serialize(root);
		System.out.println(serialized);
		Node deserialized = deserialize(serialized);
		preOrder(deserialized);
		System.out.println();
	}
}

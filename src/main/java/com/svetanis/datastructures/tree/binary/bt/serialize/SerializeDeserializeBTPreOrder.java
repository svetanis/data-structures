package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.preOrder;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.LinkedList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 297. Serialize and Deserialize Binary Tree

public final class SerializeDeserializeBTPreOrder {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	private static final String DELIMITER = "#";
	private static final String SEPARATOR = ",";

	public static String serialize(Node root) {
		StringBuilder sb = new StringBuilder();
		serialize(root, sb);
		return sb.toString();
	}

	private static void serialize(Node root, StringBuilder sb) {
		if (root == null) {
			sb.append(DELIMITER).append(SEPARATOR);
			return;
		}
		sb.append(root.data).append(SEPARATOR);
		serialize(root.left, sb);
		serialize(root.right, sb);
	}

	public static Node deserialize(String s) {
		if (isBlank(s) || s.equals(DELIMITER)) {
			return null;
		}
		List<String> list = new LinkedList<>(asList(s.split(SEPARATOR)));
		return deserialize(list);
	}

	private static Node deserialize(List<String> list) {
		String curr = list.remove(0);
		if (DELIMITER.equals(curr)) {
			return null;
		}
		Node node = new Node(parseInt(curr));
		node.left = deserialize(list);
		node.right = deserialize(list);
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

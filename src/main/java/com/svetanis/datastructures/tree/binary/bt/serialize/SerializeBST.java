package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 449. Serialize and Deserialize BST

public final class SerializeBST {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private static final String SEPARATOR = " ";
	private static final int INF = 1 << 30;

	private int index;
	private List<String> list;

	public String serialize(Node root) {
		this.list = new ArrayList<>();
		serialize(root, list);
		return String.join(SEPARATOR, list);
	}

	private void serialize(Node root, List<String> list) {
		if (root == null) {
			return;
		}
		list.add(String.valueOf(root.data));
		serialize(root.left, list);
		serialize(root.right, list);
	}

	public Node deserialize(String s) {
		if (s.isBlank()) {
			return null;
		}
		this.index = 0;
		this.list = Arrays.asList(s.split(SEPARATOR));
		return deserialize(-INF, INF);
	}

	private Node deserialize(int min, int max) {
		if (index == list.size()) {
			return null;
		}
		int val = Integer.parseInt(list.get(index));
		if (val < min || val > max) {
			return null;
		}
		Node node = new Node(val);
		index += 1;
		node.left = deserialize(min, val);
		node.right = deserialize(val, max);
		return node;
	}

	public static void main(String[] args) {
		Node root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		SerializeBST bst = new SerializeBST();
		inOrder(root);
		System.out.println();
		String serialized = bst.serialize(root);
		System.out.println("serialized=" + serialized); // 4 2 1 5
		Node deserialized = bst.deserialize(serialized);
		inOrder(deserialized);
	}
}

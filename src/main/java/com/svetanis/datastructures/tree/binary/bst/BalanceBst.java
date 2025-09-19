package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 1382. Balance a Binary Search Tree

public final class BalanceBst {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private List<Integer> list;

	public Node balance(Node root) {
		this.list = new ArrayList<>();
		bstToList(root);
		return listToBst();
	}

	private void bstToList(Node root) {
		if (root == null) {
			return;
		}
		bstToList(root.left);
		list.add(root.data);
		bstToList(root.right);
	}

	private Node listToBst() {
		int n = list.size();
		return listToBst(0, n - 1);
	}

	private Node listToBst(int left, int right) {
		if (left > right) {
			return null;
		}
		int mid = left + (right - left) / 2;
		Node root = new Node(list.get(mid));
		root.left = listToBst(left, mid - 1);
		root.right = listToBst(mid + 1, right);
		return root;
	}

	public static void main(String[] args) {
		BalanceBst bb1 = new BalanceBst();
		Node root = newNode(1);
		root.left = newNode(2);
		root.left.left = newNode(3);
		root.left.left.left = newNode(4);
		Nodes.preOrder(bb1.balance(root));
		System.out.println();

		Node root2 = newNode(2);
		root2.left = newNode(1);
		root2.right = newNode(3);
		Nodes.preOrder(bb1.balance(root2));
		System.out.println();
	}
}

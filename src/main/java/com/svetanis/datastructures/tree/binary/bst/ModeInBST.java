package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.utils.Print;

// 501. Find Mode in Binary Search Tree

public final class ModeInBST {
	// Time Complexity: O(n)

	private int max;
	private int count;
	private Node prev;
	private List<Integer> list;

	public int[] duplicates(Node root) {
		dfs(root);
		int[] a = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			a[i] = list.get(i);
		}
		return a;
	}

	private void dfs(Node root) {
		if (root == null) {
			return;
		}
		dfs(root.left);
		count = (prev != null && prev.data == root.data) ? count + 1 : 1;
		if (count > max) {
			max = count;
			list = new ArrayList<>();
		}
		if (count == max) {
			list.add(root.data);
		}
		prev = root;
		dfs(root.right);
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.right = newNode(2);
		root.right.left = newNode(2);
		ModeInBST m1 = new ModeInBST();
		Print.print(m1.duplicates(root));

		ModeInBST m2 = new ModeInBST();
		Node root1 = newNode(0);
		Print.print(m2.duplicates(root1));

		ModeInBST m3 = new ModeInBST();
		Node root2 = newNode(2);
		root2.left = newNode(1);
		Print.print(m3.duplicates(root2));

		ModeInBST m4 = new ModeInBST();
		Node root3 = newNode(1);
		root3.left = newNode(1);
		Print.print(m4.duplicates(root3));

	}
}
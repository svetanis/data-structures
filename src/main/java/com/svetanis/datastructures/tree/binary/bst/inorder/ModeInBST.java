package com.svetanis.datastructures.tree.binary.bst.inorder;

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
		// the four fields survive the traversal, so without this the object
		// is single-use: a second call keeps the first tree's run length
		// and its answer list, and appends to both
		this.max = 0;
		this.count = 0;
		this.prev = null;
		this.list = new ArrayList<>();
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

		// the same object twice. without the reset the second call
		// appended to the first call's list and printed 2 2
		ModeInBST reused = new ModeInBST();
		Node twos = newNode(1);
		twos.right = newNode(2);
		twos.right.right = newNode(2);
		Print.print(reused.duplicates(twos)); // 2
		Print.print(reused.duplicates(twos)); // 2

		// an empty tree. without the initialisation above, list was null
		// and duplicates threw before it could return anything
		Print.print(new ModeInBST().duplicates(null)); //
	}
}
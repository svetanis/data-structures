package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 199. Binary Tree Right Side View

// Given a binary tree, return an array 
// containing nodes in its right view. 
// The right view of a binary tree is 
// the set of nodes visible when the 
// tree is seen from the right side.

public final class RightViewDfs {
	// Time Complexity: O(n)

	private List<Integer> list = new ArrayList<>();

	public List<Integer> rightView(Node root) {
		dfs(root, 0);
		return list;
	}

	private void dfs(Node root, int depth) {
		if (root == null) {
			return;
		}
		if (list.size() == depth) {
			list.add(root.data);
		}
		dfs(root.right, depth + 1);
		dfs(root.left, depth + 1);
	}

	public static void main(String[] args) {
		RightViewDfs rv = new RightViewDfs();
		Node root = newNode(12);
		root.left = newNode(10);
		root.left.left = newNode(30);
		root.left.left = newNode(25);
		root.left.right = newNode(40);
		System.out.println(rv.rightView(root)); // 12 10 40
	}
}

package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 971. Flip Binary Tree To Match Preorder Traversal

public final class FlipMatchVoyage {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private int index;
	private boolean match;
	private int[] voyage;
	private List<Integer> flipped;

	public List<Integer> flipMatchVoyage(Node root, int[] voyage) {
		this.index = 0;
		this.match = true;
		this.voyage = voyage;
		this.flipped = new ArrayList<>();
		if (root == null) {
			return flipped;
		}
		dfs(root);
		return match ? flipped : Arrays.asList(-1);
	}

	private void dfs(Node root) {
		if (root == null || !match) {
			return;
		}
		if (root.data != voyage[index]) {
			match = false;
			return;
		}
		index += 1;
		if (root.left == null || root.left.data == voyage[index]) {
			dfs(root.left);
			dfs(root.right);
		} else {
			flipped.add(root.data);
			dfs(root.right);
			dfs(root.left);
		}
	}

	public static void main(String[] args) {
		FlipMatchVoyage fmv = new FlipMatchVoyage();
		Node root2 = newNode(1);
		root2.left = newNode(2);
		System.out.println(fmv.flipMatchVoyage(root2, new int[] { 2, 1 })); // -1

		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		System.out.println(fmv.flipMatchVoyage(root1, new int[] { 1, 3, 2 })); // 1

		Node root3 = newNode(1);
		root3.left = newNode(2);
		root3.right = newNode(3);
		System.out.println(fmv.flipMatchVoyage(root3, new int[] { 1, 2, 3 })); // []
	}
}

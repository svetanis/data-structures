package com.svetanis.datastructures.tree.binary.bt.lca;

// A binary tree node that also knows its parent -- the class LeetCode 1650 hands you, under the
// same name and fields. Shared by LcaBtParent, LcaBtParentLevel and LcaBtParentStack.

final class Node {

	public int val;
	public Node left;
	public Node right;
	public Node parent; // UP: null at the root

	public Node(int val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return Integer.toString(val);
	}
}

package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotQueue.traverse;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 536. Construct Binary Tree from String

public final class ConstructBTFromString {
	// Time Complexity: O(n^2)

	public static Node construct(String s) {
		if ("".equals(s)) {
			return null;
		}
		int lpi = s.indexOf('(');
		if (lpi == -1) {
			return new Node(Integer.parseInt(s));
		}
		Node root = new Node(Integer.parseInt(s.substring(0, lpi)));
		int count = 0;
		int start = lpi;
		for (int i = lpi; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				count++;
			} else if (s.charAt(i) == ')') {
				count--;
			}

			if (count == 0) {
				if (start == lpi) {
					root.left = construct(s.substring(start + 1, i));
					start = i + 1;
				} else {
					root.right = construct(s.substring(start + 1, i));
				}
			}
		}
		return root;
	}

	public static void main(String[] args) {
		System.out.println(traverse(construct("4(2(3)(1))(6(5))"))); // 4, 2, 6, 3, 1, 5
	}
}

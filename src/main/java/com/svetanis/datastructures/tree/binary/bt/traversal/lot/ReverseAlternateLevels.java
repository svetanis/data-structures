package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotQueue.traverse;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 2415. Reverse Odd Levels of Binary Tree
//
// Input: the root of a perfect binary tree -- every node has two children or none, and all leaves
// are on the last level. The root is level 0. Reverses, in place, the values on every odd level
// (1, 3, 5, ...) so that level 1 reads right to left, and so on, and returns the root. The nodes
// stay where they are; only their values move. On LeetCode: 1 <= nodes <= 2^14,
// 0 <= Node.val <= 10^5, the tree is perfect.
//
// The one idea: reversing a whole level is the same as swapping each node with its mirror -- the
// node at the same distance from the other edge. In a perfect tree the mirror of a node's child
// is found by crossing over: the left child of one node mirrors the right child of the other.
// So two nodes are walked together, starting with the root's two children, and each step goes
// to (left of the first, right of the second) and (right of the first, left of the second). The
// two nodes are swapped when their level is odd and left alone when it is even; the walk goes
// through the even levels only to reach the odd ones below.
//
// Time: O(n) -- each call handles two nodes, so there are about n / 2 calls.
// Space: O(h) -- the recursion stack; h is lg n because the tree is perfect.

public final class ReverseAlternateLevels {

	public static TreeNode reverseOddLevels(TreeNode root) {
		if (root == null) {
			return null;
		}
		reverseOddLevels(root.left, root.right, 0);
		return root; // RETURN: the same root; only values moved
	}

	// root1 and root2 are mirror nodes; level 0 here is the level of the root's children, which is
	// level 1 of the tree
	private static void reverseOddLevels(TreeNode root1, TreeNode root2, int level) {
		if (root1 == null || root2 == null) {
			return; // BASE: past the leaves, both are null together in a perfect tree
		}

		if (level % 2 == 0) {
			int temp = root1.val; // SWAP: the values of the two mirror nodes
			root1.val = root2.val;
			root2.val = temp;
		}
		reverseOddLevels(root1.left, root2.right, level + 1); // WALK: crossed over, the mirror pair
		reverseOddLevels(root1.right, root2.left, level + 1); // WALK: the other mirror pair
	}

	public static void main(String[] args) {
		// the LeetCode examples, printed level by level
		System.out.println(traverse(reverseOddLevels(perfect(2, 3, 5, 8, 13, 21, 34))));
		// [2, 5, 3, 8, 13, 21, 34]
		System.out.println(traverse(reverseOddLevels(perfect(7, 13, 11)))); // [7, 11, 13]
		System.out.println(traverse(reverseOddLevels(perfect(0, 1, 2, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2))));
		// [0, 2, 1, 0, 0, 0, 0, 2, 2, 2, 2, 1, 1, 1, 1]
	}

	// builds a perfect tree from its level-order values: the children of index i are 2i+1 and 2i+2
	private static TreeNode perfect(int... values) {
		TreeNode[] nodes = new TreeNode[values.length];
		for (int i = values.length - 1; i >= 0; i--) {
			nodes[i] = newNode(values[i]);
			if (2 * i + 2 < values.length) {
				nodes[i].left = nodes[2 * i + 1];
				nodes[i].right = nodes[2 * i + 2];
			}
		}
		return nodes[0];
	}
}

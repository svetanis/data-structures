package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.preOrder;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 95. Unique Binary Search Trees II
//
// Input: n. Returns every structurally unique binary search tree that stores the keys 1 to n, in
// any order. On LeetCode: 1 <= n <= 8. (For n <= 0 this returns an empty list.)
//
// The one idea: construct(start, end) returns every tree that holds exactly the keys start..end.
// Pick one key i as the root. The keys below i must all go in the left subtree and the keys above
// i in the right one, so the left subtree is any tree from construct(start, i - 1) and the right
// is any tree from construct(i + 1, end), and every pairing of the two is a different tree. An
// empty range has exactly one tree, the empty one, so it returns a list holding one null -- an
// empty list would make every product with it empty. The subtrees are shared, not copied: one
// left tree appears under several roots, which is fine while nobody changes the trees.
//
// Time: O(4^n / n^1.5) -- the number of trees is the n-th Catalan number, about 4^n / (n^1.5 *
// sqrt(pi)), and one new node is made per tree and per subtree pairing; the lists for a smaller
// range are rebuilt every time they are needed, which adds only a constant factor.
// Space: O(4^n / n^1.5) -- the same nodes, each kept in a list.
//
// Siblings: AllUniqueBSTsTopDown caches construct(start, end) so each range is built once.
// AllUniqueBSTsCount* only count the trees.

public final class AllUniqueBSTs {

	public static List<TreeNode> construct(int n) {
		if (n <= 0) {
			return new ArrayList<>();
		}
		return construct(1, n);
	}

	private static List<TreeNode> construct(int start, int end) {
		List<TreeNode> list = new ArrayList<>();
		// if start > end then subtree is empty
		if (start > end) {
			list.add(null); // BASE: one tree, the empty one
			return list;
		}

		for (int i = start; i <= end; i++) {
			List<TreeNode> left = construct(start, i - 1); // LEFT: every tree on the keys below i
			List<TreeNode> right = construct(i + 1, end); // RIGHT: every tree on the keys above i
			// loop through all left and right subtrees
			// and connect them to i-th root
			for (int j = 0; j < left.size(); j++) {
				for (int k = 0; k < right.size(); k++) {
					TreeNode node = new TreeNode(i); // make value i as root
					node.left = left.get(j); // connect left subtree
					node.right = right.get(k); // connect right subtree
					list.add(node); // RECORD: one tree per pairing
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		// construct all possible BTs
		List<TreeNode> trees = construct(3);
		for (int i = 0; i < trees.size(); i++) {
			preOrder(trees.get(i));
			System.out.println(); // 1 2 3 / 1 3 2 / 2 1 3 / 3 1 2 / 3 2 1, one tree per line
		}
	}
}

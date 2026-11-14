package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1305. All Elements in Two Binary Search Trees
//
// Input: the roots of two binary search trees (either may be empty). Returns one list holding
// every value of both trees in ascending order; a value in both trees appears twice. On
// LeetCode: each tree has 0 to 5000 nodes, -10^5 <= node.val <= 10^5.
//
// The one idea: the in-order walk of one BST is its values in ascending order, so each tree
// becomes a sorted list. Two sorted lists merge in one pass: keep one position in each list,
// copy the smaller of the two values under the positions, move that position on; when one list
// runs out, copy the rest of the other. Each value is copied once.
//
// Time: O(n + m) -- one walk of each tree (n and m nodes), then one merge pass over n + m values.
// Space: O(n + m) -- the two lists and the answer.

public final class AllElementsInTwoBst {

	public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		inOrder(root1, list1); // WALK: first tree to a sorted list
		inOrder(root2, list2); // WALK: second tree to a sorted list
		int size1 = list1.size();
		int size2 = list2.size();
		int iter1 = 0, iter2 = 0; // MERGE: the next unused position in each list
		List<Integer> list = new ArrayList<>();
		while (iter1 < size1 && iter2 < size2) {
			if (list1.get(iter1) <= list2.get(iter2)) {
				list.add(list1.get(iter1)); // TAKE: the smaller front value
				iter1++;
			} else {
				list.add(list2.get(iter2));
				iter2++;
			}
		}
		while (iter1 < size1) { // REST: second list is used up, copy what is left of the first
			list.add(list1.get(iter1));
			iter1++;
		}
		while (iter2 < size2) { // REST: first list is used up, copy what is left of the second
			list.add(list2.get(iter2));
			iter2++;
		}
		return list;
	}

	private void inOrder(TreeNode root, List<Integer> list) {
		if (root == null) { // BASE: empty subtree
			return;
		}
		inOrder(root.left, list); // WALK: everything smaller first
		list.add(root.val); // RECORD: this node
		inOrder(root.right, list); // WALK: everything larger last
	}

	public static void main(String[] args) {
		AllElementsInTwoBst bb1 = new AllElementsInTwoBst();
		TreeNode root = newNode(2);
		root.left = newNode(1);
		root.right = newNode(4);

		TreeNode root2 = newNode(1);
		root2.left = newNode(0);
		root2.right = newNode(3);

		System.out.println(bb1.getAllElements(root, root2)); // [0, 1, 1, 2, 3, 4]
		System.out.println(bb1.getAllElements(null, root2)); // [0, 1, 3]
	}
}

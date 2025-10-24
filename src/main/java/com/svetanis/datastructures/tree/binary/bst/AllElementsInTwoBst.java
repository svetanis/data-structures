package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1305. All Elements in Two Binary Search Trees

public final class AllElementsInTwoBst {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public List<Integer> getAllElements(Node root1, Node root2) {
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		inOrder(root1, list1);
		inOrder(root2, list2);
		int size1 = list1.size();
		int size2 = list2.size();
		int iter1 = 0, iter2 = 0;
		List<Integer> list = new ArrayList<>();
		while (iter1 < size1 && iter2 < size2) {
			if (list1.get(iter1) <= list2.get(iter2)) {
				list.add(list1.get(iter1));
				iter1++;
			} else {
				list.add(list2.get(iter2));
				iter2++;
			}
		}
		while (iter1 < size1) {
			list.add(list1.get(iter1));
			iter1++;
		}
		while (iter2 < size2) {
			list.add(list2.get(iter2));
			iter2++;
		}
		return list;
	}

	private void inOrder(Node root, List<Integer> list) {
		if (root == null) {
			return;
		}
		inOrder(root.left, list);
		list.add(root.data);
		inOrder(root.right, list);
	}

	public static void main(String[] args) {
		AllElementsInTwoBst bb1 = new AllElementsInTwoBst();
		Node root = newNode(2);
		root.left = newNode(1);
		root.right = newNode(4);

		Node root2 = newNode(1);
		root2.left = newNode(0);
		root2.right = newNode(3);

		System.out.println(bb1.getAllElements(root, root2));
		System.out.println();
	}
}

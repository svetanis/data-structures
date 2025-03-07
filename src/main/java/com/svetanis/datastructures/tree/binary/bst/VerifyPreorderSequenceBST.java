package com.svetanis.datastructures.tree.binary.bst;

import java.util.ArrayDeque;
import java.util.Deque;

// 255. Verify Preorder Sequence in Binary Search Tree

public final class VerifyPreorderSequenceBST {
	// Time complexity O(n);
	// Space complexity: O(n)

	public static boolean isPreorder(int[] preorder) {
		int prev = Integer.MIN_VALUE;
		Deque<Integer> dq = new ArrayDeque<>();
		for (int num : preorder) {
			if (prev > num) {
				return false;
			}

			while (!dq.isEmpty() && dq.peek() < num) {
				prev = dq.pop();
			}

			dq.push(num);
		}
		return true;
	}

	public static void main(String[] args) {
		int[] a = { 5, 2, 1, 3, 6 };
		System.out.println(isPreorder(a));
	}
}

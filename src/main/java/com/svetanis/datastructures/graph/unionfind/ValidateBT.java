package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

// 1361. Validate Binary Tree Nodes
//
// Input: n -- nodes numbered 0..n-1; left[i] and right[i] -- the left and right child of node i, or
// -1 for none. Returns true if all n nodes form exactly one valid binary tree. On LeetCode:
// n == left.length == right.length, 1 <= n <= 10^4, -1 <= left[i], right[i] <= n - 1.
//
// The one idea: no union-find here, only counting and one walk. A tree has these facts: no node has
// two parents, exactly one node (the root) has none, and walking down from the root reaches every
// node exactly once. So count how many parents each node has; reject a node with two. Then require
// exactly one node with no parent. Finally walk from that root with a queue and a visited set: a
// node met twice is a loop, and fewer than n nodes visited means some nodes hang apart.
//
// Time: O(n) -- every node is counted, then visited once.
// Space: O(n) -- the parent counts, the visited set and the queue.

public final class ValidateBT {

	public static boolean validateBT(int n, int[] left, int[] right) {
		int[] parent = new int[n];
		for (int node = 0; node < n; node++) {
			for (int child : new int[] { left[node], right[node] }) {
				if (child != -1) {
					parent[child]++; // COUNT: one more parent for child
					if (parent[child] > 1) {
						return false; // two parents
					}
				}
			}
		}

		int root = -1;
		for (int i = 0; i < n; i++) {
			if (parent[i] == 0) {
				if (root == -1) {
					root = i; // the node with no parent
				} else {
					return false; // two roots
				}
			}
		}

		if (root == -1) {
			return false; // every node has a parent: a loop and no root
		}

		Set<Integer> visited = new HashSet<>();
		Deque<Integer> dq = new ArrayDeque<>();
		dq.offer(root);
		while (!dq.isEmpty()) {
			int node = dq.poll();
			if (!visited.add(node)) {
				return false; // met a second time: a loop
			}
			if (left[node] != -1) {
				dq.offer(left[node]);
			}
			if (right[node] != -1) {
				dq.offer(right[node]);
			}
		}
		return visited.size() == n; // RETURN: the walk reached every node
	}

	public static void main(String[] args) {
		int[] left1 = { 1, -1, 3, -1 };
		int[] right1 = { 2, -1, -1, -1 };
		System.out.println(validateBT(4, left1, right1)); // true -- 0 has children 1 and 2, 2 has child 3

		int[] left2 = { 1, -1, 3, -1 };
		int[] right2 = { 2, 3, -1, -1 };
		System.out.println(validateBT(4, left2, right2)); // false -- node 3 has two parents

		int[] left3 = { 1, 0 };
		int[] right3 = { -1, -1 };
		System.out.println(validateBT(2, left3, right3)); // false -- 0 and 1 are each other's child, no root
	}
}

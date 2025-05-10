package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

// 1361. Validate Binary Tree Nodes

public final class ValidateBT {

	public static boolean validateBT(int n, int[] left, int[] right) {
		int[] parent = new int[n];
		for(int node = 0; node < n; node++) {
			for(int child : new int[] {left[node], right[node]}) {
				if(child != -1) {
					parent[child]++;
					if(parent[child] > 1) {
						return false;
					}
				}
			}
		}

		int root = -1;
		for(int i = 0; i < n; i++) {
			if(parent[i] == 0) {
				if(root == -1) {
					root = i;
				} else {
					return false;
				}
			}
		}
		
		if(root == -1) {
			return false;
		}
		
		Set<Integer> visited = new HashSet<>();
		Deque<Integer> dq = new ArrayDeque<>();
		dq.offer(root);
		while(!dq.isEmpty()) {
			int node = dq.poll();
			if(!visited.add(node)) {
				return false;
			}
			if(left[node] != -1) {
				dq.offer(left[node]);
			}
			if(right[node] != -1) {
				dq.offer(right[node]);
			}
		}
		return visited.size() == n;
	}

	public static void main(String[] args) {
		int[] left1 = { 1, -1, 3, -1 };
		int[] right1 = { 2, -1, -1, -1 };
		System.out.println(validateBT(4, left1, right1)); // true

		int[] left2 = { 1, -1, 3, -1 };
		int[] right2 = { 2, 3, -1, -1 };
		System.out.println(validateBT(4, left2, right2)); // false

		int[] left3 = { 1, 0 };
		int[] right3 = { -1, -1 };
		System.out.println(validateBT(2, left3, right3)); // false
	}
}

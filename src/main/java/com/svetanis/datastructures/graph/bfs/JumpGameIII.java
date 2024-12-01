package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.Queue;

// 1306. Jump Game III

public final class JumpGameIII {
	// Time & Space Complexity: O(n)

	public static boolean canReach(int[] a, int start) {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(start);
		while (!queue.isEmpty()) {
			int index = queue.poll();
			int next = a[index];
			if (next == 0) {
				return true;
			}
			
			// mark current index as visited
			a[index] = -1;

			int left = index - next;
			if (safe(a, left)) {
				queue.offer(left);
			}
			int right = index + next;
			if (safe(a, right)) {
				queue.offer(right);
			}
		}
		return false;
	}

	private static boolean safe(int[] a, int index) {
		return index >= 0 && index < a.length && a[index] >= 0;
	}

	public static void main(String[] args) {
		int[] a1 = { 4, 2, 3, 0, 3, 1, 2 };
		System.out.println(canReach(a1, 5)); // true
		int[] a2 = { 4, 2, 3, 0, 3, 1, 2 };
		System.out.println(canReach(a2, 0)); // true
		int[] a3 = { 3, 0, 2, 1, 2 };
		System.out.println(canReach(a3, 2)); // false
	}
}
